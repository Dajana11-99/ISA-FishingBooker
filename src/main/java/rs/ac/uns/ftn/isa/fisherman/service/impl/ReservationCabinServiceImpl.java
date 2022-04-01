package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.SearchAvailablePeriodsCabinDto;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinReservationDto;
import rs.ac.uns.ftn.isa.fisherman.mail.CabinReservationSuccessfulInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.mapper.AdditionalServiceMapper;
import rs.ac.uns.ftn.isa.fisherman.mapper.CabinReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationCancellationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AvailableCabinPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.ClientService;
import rs.ac.uns.ftn.isa.fisherman.service.QuickReservationCabinService;
import rs.ac.uns.ftn.isa.fisherman.service.ReservationCabinService;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReservationCabinServiceImpl implements ReservationCabinService {

    @Autowired
    private ClientService clientService;
    @Autowired
    private MailService<String> mailService;
    private CabinReservationMapper cabinReservationMapper = new CabinReservationMapper();
    AdditionalServiceMapper additionalServiceMapper = new AdditionalServiceMapper();
    @Autowired
    private AvailableCabinPeriodService availableCabinPeriodService;
    @Autowired
    private CabinReservationRepository cabinReservationRepository;
    @Autowired
    private QuickReservationCabinService quickReservationCabinService;
    @Autowired
    private CabinReservationCancellationRepository cabinReservationCancellationRepository;

    @Override
    public Set<Cabin> getAvailableCabins(SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto) {
        Set<Cabin> availableCabins = new HashSet<>();
        Client client = clientService.findByUsername(searchAvailablePeriodsCabinDto.getUsername());
        List<CabinReservationCancellation> cabinReservationCancellations = cabinReservationCancellationRepository.getByUsersId(client.getId());
        for(AvailableCabinPeriod cabinPeriod:availableCabinPeriodService.findAll()){
            if(periodWasAlreadyReserved(cabinPeriod, cabinReservationCancellations))
                continue;
            if(searchAvailablePeriodsCabinDto.getStartDate().isBefore(cabinPeriod.getStartDate())
                    ||searchAvailablePeriodsCabinDto.getEndDate().isAfter(cabinPeriod.getEndDate())) {
                continue;
            }
            if(searchAvailablePeriodsCabinDto.getPrice()!=0){
                if(cabinPeriod.getCabin().getPrice()>searchAvailablePeriodsCabinDto.getPrice())
                    continue;
            }
            if(searchAvailablePeriodsCabinDto.getBedsPerRoom()>cabinPeriod.getCabin().getBedsPerRoom())
                continue;
            if(searchAvailablePeriodsCabinDto.getNumberOfRooms()>cabinPeriod.getCabin().getNumOfRooms())
                continue;
            if(cabinNotReservedInPeriod(cabinPeriod.getCabin().getId(), searchAvailablePeriodsCabinDto.getStartDate(), searchAvailablePeriodsCabinDto.getEndDate()))
                availableCabins.add(cabinPeriod.getCabin());
        }
        return availableCabins;
    }

    private boolean periodWasAlreadyReserved(AvailableCabinPeriod cabinPeriod, List<CabinReservationCancellation> cabinReservationCancellations) {
        for(CabinReservationCancellation cabinReservationCancellation:cabinReservationCancellations) {
            if (cabinReservationCancellation.getCabinReservation().getCabin().getId().equals(cabinPeriod.getCabin().getId()))
                continue;
            if (cabinReservationCancellation.getCabinReservation().getStartDate().isBefore(cabinPeriod.getStartDate())
                    || cabinReservationCancellation.getCabinReservation().getEndDate().isAfter(cabinPeriod.getEndDate()))
                return true;
        }
        return false;
    }

    @Override
    public boolean makeReservation(CabinReservationDto cabinReservationDto) {
        CabinReservation cabinReservation = cabinReservationMapper.cabinReservationDtoToCabinReservation(cabinReservationDto);
        cabinReservation.setClient(clientService.findByUsername(cabinReservationDto.getClientUsername()));
        if(periodStillAvailable(cabinReservation)){
            cabinReservationRepository.save(cabinReservation);
            if(cabinReservationDto.getAddedAdditionalServices()!=null)
            {
                cabinReservation.setAddedAdditionalServices(additionalServiceMapper.additionalServicesDtoToAdditionalServices(cabinReservationDto.getAddedAdditionalServices()));
                cabinReservationRepository.save(cabinReservation);
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
                String message = cabinReservationDto.getCabinDto().getName() + " is booked from " + cabinReservationDto.getStartDate().format(formatter) + " to " + cabinReservationDto.getEndDate().format(formatter) + " .";
                mailService.sendMail(cabinReservationDto.getClientUsername(), message, new CabinReservationSuccessfulInfo());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private boolean periodStillAvailable(CabinReservation cabinReservation) {
        for(AvailableCabinPeriod cabinPeriod:availableCabinPeriodService.findAll()){
            if(cabinPeriod.getCabin().getId().equals(cabinReservation.getCabin().getId())){
                if(cabinReservation.getStartDate().isAfter(cabinPeriod.getStartDate())
                        &&cabinReservation.getEndDate().isBefore(cabinPeriod.getEndDate())
                        &&cabinNotReservedInPeriod(cabinReservation.getCabin().getId(), cabinReservation.getStartDate(),
                        cabinReservation.getEndDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean cabinNotReservedInPeriod(Long id, LocalDateTime start, LocalDateTime end) {
        for(CabinReservation cabinReservation:cabinReservationRepository.findAll()){
            if(cabinReservation.getCabin().getId().equals(id)){
                if((cabinReservation.getStartDate().isBefore(end)||cabinReservation.getStartDate().isEqual(end))
                        &&(cabinReservation.getEndDate().isAfter(start)||cabinReservation.getEndDate().isEqual(start))) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public boolean ownerCreates(CabinReservation cabinReservation, String clientUsername) {
        Client client = clientService.findByUsername(clientUsername);
        if(!validateForReservation(cabinReservation,client)) return false;
        CabinReservation successfullReservation=new CabinReservation(cabinReservation.getId(),cabinReservation.getStartDate(),
                    cabinReservation.getEndDate(),cabinReservation.getPrice(),client,cabinReservation.getCabin(),null);
        cabinReservationRepository.save(successfullReservation);
        if(cabinReservation.getAddedAdditionalServices()!=null){
                successfullReservation.setAddedAdditionalServices(cabinReservation.getAddedAdditionalServices());
                cabinReservationRepository.save(successfullReservation);
        }
        sendMailNotification(successfullReservation,client.getUsername());
        return true;
    }

    @Override
    public Set<CabinReservation> getPresentByCabinId(Long cabinId) {
        LocalDateTime currentDate= LocalDateTime.now();
        return cabinReservationRepository.getPresentByCabinId(cabinId,currentDate);
    }

    private boolean validateForReservation(CabinReservation cabinReservation,Client client){
        LocalDateTime currentDate= LocalDateTime.now();
        if(client==null) return false;
        if(!cabinReservationRepository.clientHasReservation(cabinReservation.getCabin().
                getId(),client.getId(),currentDate)) return false;

        if(!availableCabinPeriodService.cabinIsAvailable(cabinReservation.getCabin()
                .getId(),cabinReservation.getStartDate(),cabinReservation.getEndDate())) return false;

        if(cabinReservationRepository.reservationExists(cabinReservation.getCabin()
                .getId(),cabinReservation.getStartDate(),cabinReservation.getEndDate())) return false;
        if(quickReservationCabinService.quickReservationExists(cabinReservation.getCabin().getId(),
                cabinReservation.getStartDate(),cabinReservation.getEndDate())) return false;
        return true;
    }
    public boolean reservationExists(Long cabinId, LocalDateTime startDate, LocalDateTime endDate){
        return cabinReservationRepository.reservationExists(cabinId,startDate,endDate);
    }

    private void sendMailNotification(CabinReservation cabinReservation,String email){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = cabinReservation.getCabin().getName() + " is booked from " + cabinReservation.getStartDate().format(formatter) + " to " + cabinReservation.getEndDate().format(formatter) + " .";
            mailService.sendMail(email, message, new CabinReservationSuccessfulInfo());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Set<CabinReservation> getUpcomingClientReservationsByUsername(String clientUsername){
        List<CabinReservationCancellation> cabinReservationCancellations = cabinReservationCancellationRepository.getByUsersId(clientService.findByUsername(clientUsername).getId());
        Set<CabinReservation> cabinReservations = new HashSet<>();
        for(CabinReservation cabinReservation:cabinReservationRepository.getUpcomingClientReservations(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now())){
            if(!reservationCancelled(cabinReservation.getId(),cabinReservationCancellations))
                cabinReservations.add(cabinReservation);
        }
        return cabinReservations;
    }

    private boolean reservationCancelled(Long id, List<CabinReservationCancellation> cabinReservationCancellations){
        for(CabinReservationCancellation cabinReservationCancellation:cabinReservationCancellations){
            if(cabinReservationCancellation.getCabinReservation().getId().equals(id)) return true;
        }
        return false;
    }

    public Set<CabinReservation> getClientReservationHistoryByUsername(String clientUsername){
        return cabinReservationRepository.getClientReservationsHistory(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }
    @Override
    public boolean futureReservationsExist(LocalDateTime currentDate,Long boatId) {
        return cabinReservationRepository.futureReservationsExist(currentDate,boatId);
    }
}
