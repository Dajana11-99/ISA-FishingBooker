package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.fisherman.dto.SearchAvailablePeriodsCabinDto;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinReservationDto;
import rs.ac.uns.ftn.isa.fisherman.mail.CabinReservationSuccessfulInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.mapper.AdditionalServiceMapper;
import rs.ac.uns.ftn.isa.fisherman.mapper.CabinReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationCancellationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationCabinServiceImpl implements ReservationCabinService {
    private final Logger logger= LoggerFactory.getLogger(FirebaseServiceImpl.class);
    @Autowired
    private ClientService clientService;
    @Autowired
    private CabinOwnerService cabinOwnerService;
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
    @Autowired
    private ReservationPaymentService reservationPaymentService;

    @Override
    public Set<Cabin> getAvailableCabins(SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto) {
        Set<Cabin> availableCabins = new HashSet<>();
        Client client = clientService.findByUsername(searchAvailablePeriodsCabinDto.getUsername());
        List<CabinReservationCancellation> cabinReservationCancellations = cabinReservationCancellationRepository.getByUsersId(client.getId());
        for(AvailableCabinPeriod cabinPeriod:availableCabinPeriodService.findAll()){
            if(periodWasAlreadyReserved(cabinPeriod.getCabin().getId(),searchAvailablePeriodsCabinDto.getStartDate(),searchAvailablePeriodsCabinDto.getEndDate(), cabinReservationCancellations))
            {
                continue;
            }
            if(searchAvailablePeriodsCabinDto.getStartDate().isBefore(cabinPeriod.getStartDate())
                    ||searchAvailablePeriodsCabinDto.getEndDate().isAfter(cabinPeriod.getEndDate())) {
                continue;
            }
            if(searchAvailablePeriodsCabinDto.getPrice()!=0){
                if(cabinPeriod.getCabin().getPrice()>searchAvailablePeriodsCabinDto.getPrice())
                    continue;
            }
            if(searchAvailablePeriodsCabinDto.getNumberOfBeds()>(cabinPeriod.getCabin().getBedsPerRoom()*cabinPeriod.getCabin().getNumOfRooms()))
                continue;
            if(searchAvailablePeriodsCabinDto.getNumberOfRooms()>cabinPeriod.getCabin().getNumOfRooms())
                continue;
            if(!cabinNotFreeInPeriod(cabinPeriod.getCabin().getId(), searchAvailablePeriodsCabinDto.getStartDate(), searchAvailablePeriodsCabinDto.getEndDate()))
                availableCabins.add(cabinPeriod.getCabin());
        }
        return availableCabins;
    }

    private boolean periodWasAlreadyReserved(Long cabinId, LocalDateTime startDate, LocalDateTime endDate, List<CabinReservationCancellation> cabinReservationCancellations) {
        for(CabinReservationCancellation cabinReservationCancellation:cabinReservationCancellations) {
            if (!cabinReservationCancellation.getCabin().getId().equals(cabinId))
            {
                continue;
            }
            if (cabinReservationCancellation.getStartDate().isBefore(endDate)
                    && cabinReservationCancellation.getEndDate().isAfter(startDate))
            {
                return true;
            }
        }
        return false;
    }


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean makeReservation(CabinReservationDto cabinReservationDto) throws Exception{
        if(cabinNotFreeInPeriod(cabinReservationDto.getCabinDto().getId(), cabinReservationDto.getStartDate(), cabinReservationDto.getEndDate()))
            return false;
        CabinReservation cabinReservation = setUpCabinReservationFromDto(cabinReservationDto);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmount(cabinReservation,cabinReservation.getCabin().getCabinOwner());
        cabinReservation.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(cabinReservation.getClient(),cabinReservation.getCabin().getCabinOwner());
        cabinReservationRepository.save(cabinReservation);
        if(cabinReservationDto.getAddedAdditionalServices()!=null)
        {
            cabinReservation.setAddedAdditionalServices(additionalServiceMapper.additionalServicesDtoToAdditionalServices(cabinReservationDto.getAddedAdditionalServices()));
            cabinReservationRepository.save(cabinReservation);

        }
        SendReservationMailToClient(cabinReservationDto);
        return true;
    }

    @Override
    public boolean cabinNotFreeInPeriod(Long cabinId, LocalDateTime startDate, LocalDateTime endDate) {
        return cabinReservationRepository.cabinReservedInPeriod(cabinId, startDate, endDate) ||
                quickReservationCabinService.cabinHasQuickReservationInPeriod(cabinId, startDate, endDate);
    }

    @Override
    public List<CabinReservation> findAllReservationsForAdminProfit(LocalDateTime start, LocalDateTime end) {
        return cabinReservationRepository.findAllReservationsForAdminProfit(start,end);
    }

    @NotNull
    private CabinReservation setUpCabinReservationFromDto(CabinReservationDto cabinReservationDto) {
        CabinOwner cabinOwner = cabinOwnerService.findByUsername(cabinReservationDto.getCabinDto().getOwnerUsername());
        CabinReservation cabinReservation = cabinReservationMapper.cabinReservationDtoToCabinReservation(cabinReservationDto);
        cabinReservation.getCabin().setCabinOwner(cabinOwner);
        cabinReservation.setClient(clientService.findByUsername(cabinReservationDto.getClientUsername()));
        return cabinReservation;
    }

    private boolean clientHasCancellationForCabinInPeriod(CabinReservation cabinReservation) {
        return cabinReservationCancellationRepository.clientHasCancellationForCabinInPeriod(cabinReservation.getCabin().getId(), cabinReservation.getClient().getId(), cabinReservation.getStartDate(), cabinReservation.getEndDate());
    }

    private void SendReservationMailToClient(CabinReservationDto cabinReservationDto) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = cabinReservationDto.getCabinDto().getName() + " is booked from " + cabinReservationDto.getStartDate().format(formatter) + " to " + cabinReservationDto.getEndDate().format(formatter) + " .";
            mailService.sendMail(cabinReservationDto.getClientUsername(), message, new CabinReservationSuccessfulInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime startWeek, LocalDateTime endWeek, String ownerUsername) {
        return cabinReservationRepository.countReservationsInPeriod(startWeek,endWeek,ownerUsername);
    }

    @Override
    public List<CabinReservation> findReservationsToSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end) {
        return cabinReservationRepository.findReservationsInPeriodToSumProfit(ownerUsername,start,end);
    }
    @Override
    public double sumProfitOfPricesCalculatedByDays(List<CabinReservation> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        long numOfDaysForReportReservation= 0;
        for(CabinReservation cabinReservation: reservations){
            numOfDaysForReportReservation= calculateOverlapingDates(start,end,cabinReservation.getStartDate(),cabinReservation.getEndDate());
            profit+=(numOfDaysForReportReservation*cabinReservation.getPaymentInformation().getOwnersPart())/Duration.between(cabinReservation.getStartDate(),cabinReservation.getEndDate()).toDays();
        }
        return profit;
    }

    @Override
    public double sumProfitForAdminOfPricesCalculatedByDays(List<CabinReservation> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        long numOfDaysForReportReservation= 0;
        for(CabinReservation cabinReservation: reservations){
            numOfDaysForReportReservation= calculateOverlapingDates(start,end,cabinReservation.getStartDate(),cabinReservation.getEndDate());
            profit+=(numOfDaysForReportReservation*cabinReservation.getPaymentInformation().getCompanysPart())/Duration.between(cabinReservation.getStartDate(),cabinReservation.getEndDate()).toDays();
        }
        return profit;
    }

    @Override
    public Integer countReservationsInPeriodByCabinId(LocalDateTime start, LocalDateTime end, Long id) {
        return cabinReservationRepository.countReservationsInPeriodByCabinId(start,end,id);
    }

    @Override
    public List<CabinReservation> findReservationsByCabinToSumProfit(Long id, LocalDateTime start, LocalDateTime end) {
        return cabinReservationRepository.findReservationsInPeriodByCabinToSumProfit(id,start,end);
    }

    @Override
    public Set<Cabin> searchAvailableCabins(SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto) {
        Set<Cabin> cabins = new HashSet<>();
        for(Cabin availableCabin:getAvailableCabins(searchAvailablePeriodsCabinDto)){
            if(!availableCabin.getAddress().getStreetAndNum().toLowerCase().contains(searchAvailablePeriodsCabinDto.getStreetAndNum().toLowerCase()))
                continue;
            if(!availableCabin.getAddress().getCity().toLowerCase().contains(searchAvailablePeriodsCabinDto.getCity().toLowerCase()))
                continue;
            if(!availableCabin.getAddress().getCountry().toLowerCase().contains(searchAvailablePeriodsCabinDto.getCountry().toLowerCase()))
                continue;
            if(availableCabin.getRating()!=0.0 && availableCabin.getRating()<searchAvailablePeriodsCabinDto.getRating())
                continue;
            cabins.add(availableCabin);
        }
        return cabins;
    }

    @Override
    public boolean cabinNotFreeForQuickReservationInPeriod(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        return cabinReservationRepository.cabinReservedInPeriod(id, startDate, endDate) ||
                quickReservationCabinService.cabinHasTakenQuickReservationInPeriod(id, startDate, endDate);
    }

    public long calculateOverlapingDates(LocalDateTime startReport, LocalDateTime endReport, LocalDateTime startReservation, LocalDateTime endReservation){
        long numberOfOverlappingDates=0;
        LocalDate start = Collections.max(Arrays.asList(startReport.toLocalDate(), startReservation.toLocalDate()));
        LocalDate end = Collections.min(Arrays.asList(endReport.toLocalDate(), endReservation.toLocalDate()));
        numberOfOverlappingDates = DAYS.between(start, end);
        return numberOfOverlappingDates;
    }

    @Override
    public void save(CabinReservation reservation) {
        cabinReservationRepository.save(reservation);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean ownerCreates(CabinReservation cabinReservation, String clientUsername) throws Exception {
        CabinReservation successfullReservation = null;
        Client client = clientService.findByUsername(clientUsername);

        if(cabinReservation == null) return false;
        if(client == null) return  false;
        if(!validateForReservation(cabinReservation,client)) return false;
        successfullReservation=new CabinReservation(cabinReservation.getId(),cabinReservation.getStartDate(),
                cabinReservation.getEndDate(),client,cabinReservation.getPaymentInformation(),cabinReservation.isOwnerWroteAReport(),
                cabinReservation.getOwnersUsername(),cabinReservation.getCabin(),null, false);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmount(successfullReservation,successfullReservation.getCabin().getCabinOwner());
        successfullReservation.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(client,successfullReservation.getCabin().getCabinOwner());
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
        return cabinReservationRepository.getPresentByCabinId(cabinId,LocalDateTime.now());
    }

    private boolean validateForReservation(CabinReservation cabinReservation,Client client){
        if(client==null) return false;
       /* if(!cabinReservationRepository.clientHasReservation(cabinReservation.getCabin().
                getId(),client.getId(),LocalDateTime.now())) return false;*/

        if(!availableCabinPeriodService.cabinIsAvailable(cabinReservation.getCabin()
                .getId(),cabinReservation.getStartDate(),cabinReservation.getEndDate())) return false;

        if(cabinReservationRepository.reservationExists(cabinReservation.getCabin()
                .getId(),cabinReservation.getStartDate(),cabinReservation.getEndDate()).size()>0) return false;
        if(quickReservationCabinService.quickReservationExists(cabinReservation.getCabin().getId(),
                cabinReservation.getStartDate(),cabinReservation.getEndDate())) return false;
        return true;
    }
    public boolean reservationExists(Long cabinId, LocalDateTime startDate, LocalDateTime endDate){
        return cabinReservationRepository.reservationExists(cabinId, startDate, endDate).size() > 0;
    }

    public void sendMailNotification(CabinReservation cabinReservation,String email){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = cabinReservation.getCabin().getName() + " is booked from " + cabinReservation.getStartDate().format(formatter) + " to " + cabinReservation.getEndDate().format(formatter) + " .";
            mailService.sendMail(email, message, new CabinReservationSuccessfulInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    public Set<CabinReservation> getUpcomingClientReservationsByUsername(String clientUsername){
        return cabinReservationRepository.getUpcomingClientReservations(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    public Set<CabinReservation> getClientReservationHistoryByUsername(String clientUsername){
        return cabinReservationRepository.getClientReservationsHistory(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }
    @Override
    public boolean futureReservationsExist(LocalDateTime currentDate,Long boatId) {
        return cabinReservationRepository.futureReservationsExist(currentDate, boatId).size() > 0;
    }

    @Override
    public Set<CabinReservation> findReservationsByOwnerUsername(String ownerUsername) {
        return cabinReservationRepository.findReservationsByOwnerUsername(ownerUsername,LocalDateTime.now());
    }

    @Override
    public Set<CabinReservation> getPastReservations(String ownerUsername) {
        return cabinReservationRepository.getPastReservations(ownerUsername,LocalDateTime.now());
    }

    public CabinReservation getById(Long id) {
        return cabinReservationRepository.getById(id);
    }

    @Override
    public boolean checkIfOwnerHasFutureReservations(String username) {
        return cabinReservationRepository.checkIfOwnerHasFutureReservations(username,LocalDateTime.now());
    }

    @Override
    public boolean checkIfClientHasFutureReservations(Long userId) {
        return  cabinReservationRepository.checkIfClientHasFutureReservations(userId,LocalDateTime.now());
    }

    @Override
    public void markThatReservationIsEvaluated(Long id){
        CabinReservation cabinReservation = cabinReservationRepository.getById(id);
        cabinReservation.setEvaluated(true);
        cabinReservationRepository.save(cabinReservation);
    }

    @Override
    public boolean checkIfReservationIsEvaluated(Long id){
        return cabinReservationRepository.getById(id).isEvaluated();
    }

    @Override
    public boolean reservationExists(Long id) {
        return cabinReservationRepository.reservationExists(id);
    }

}
