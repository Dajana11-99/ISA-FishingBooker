package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.fisherman.dto.BoatReservationDto;
import rs.ac.uns.ftn.isa.fisherman.dto.SearchAvailablePeriodsBoatAndAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.mail.BoatReservationSuccessfulInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.BoatReservationSuccessfullInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.mapper.AdditionalServiceMapper;
import rs.ac.uns.ftn.isa.fisherman.mapper.BoatReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationCancellationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BoatReservationServiceImpl implements BoatReservationService {
    private final Logger logger= LoggerFactory.getLogger(BoatReservationServiceImpl.class);
    @Autowired
    private BoatReservationRepository boatReservationRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AvailableBoatPeriodService availableBoatPeriodService;
    @Autowired
    private MailService<String> mailService;
    @Autowired
    private ReservationPaymentService reservationPaymentService;
    @Autowired
    private QuickReservationBoatService quickReservationBoatService;
    @Autowired
    private BoatOwnerService boatOwnerService;
    @Autowired
    private BoatReservationCancellationRepository boatReservationCancellationRepository;
    private final BoatReservationMapper boatReservationMapper = new BoatReservationMapper();
    private final AdditionalServiceMapper additionalServiceMapper = new AdditionalServiceMapper();


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean ownerCreates(BoatReservation boatReservation, String clientUsername) throws Exception{
        Client client = clientService.findByUsername(clientUsername);
        if(boatReservation == null) return  false;
        if(client == null) return  false;
        if(!validateForReservation(boatReservation,client)) return false;
        BoatReservation successfullReservation=new BoatReservation(boatReservation.getId(),boatReservation.getStartDate(),
                boatReservation.getEndDate(),client,boatReservation.getPaymentInformation(),boatReservation.isOwnerWroteAReport(),
                boatReservation.getOwnersUsername(),boatReservation.getBoat(),
                null,boatReservation.getNeedsCaptainService());
        successfullReservation.setEvaluated(false);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmount(successfullReservation,successfullReservation.getBoat().getBoatOwner());
        successfullReservation.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(client,successfullReservation.getBoat().getBoatOwner());
        if(boatReservation.getAddedAdditionalServices()!=null){
            if(boatReservation.getNeedsCaptainService()) {
                if (ownerIsNotAvailable(successfullReservation.getBoat().getBoatOwner().getUsername(),
                        successfullReservation.getStartDate(), successfullReservation.getEndDate())) return false;
            }

            boatReservationRepository.save(successfullReservation);

            successfullReservation.setAddedAdditionalServices(boatReservation.getAddedAdditionalServices());

            boatReservationRepository.save(successfullReservation);

        }else{

            boatReservationRepository.save(successfullReservation);

        }
        sendMailNotification(successfullReservation,client.getUsername());
        return true;
    }


    @Override
    public Set<BoatReservation> getPresentByCabinId(Long boatId) {
        LocalDateTime currentDate= LocalDateTime.now();
        return boatReservationRepository.getPresentByBoatId(boatId,currentDate);
    }

    @Override
    public boolean ownerIsNotAvailable(String ownersUsername, LocalDateTime start, LocalDateTime end){
        if(boatReservationRepository.ownerIsNotAvailable(ownersUsername, start, end)) return true;
        if(quickReservationBoatService.ownerIsNotAvailableQuickReservation(ownersUsername, start, end)) return true;
        return false;
    }

    @Override
    public boolean boatNotFreeForQuickReservation(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        return boatReservationRepository.boatReservedInPeriod(id, startDate, endDate) ||
                quickReservationBoatService.boatHasTakenQuickReservationInPeriod(id, startDate, endDate);
    }

    private boolean validateForReservation(BoatReservation boatReservation,Client client){
        LocalDateTime currentDate= LocalDateTime.now();
        if(client==null) return false;
        /*if(!boatReservationRepository.clientHasReservation(boatReservation.getBoat().
                getId(),client.getId(),currentDate)) return false;
*/
        if(!availableBoatPeriodService.boatIsAvailable(boatReservation.getBoat()
                .getId(),boatReservation.getStartDate(),boatReservation.getEndDate())) {
            return false;
        };

        if(!boatReservationRepository.reservationExists(boatReservation.getBoat()
                .getId(),boatReservation.getStartDate(),boatReservation.getEndDate()).isEmpty()) {
            return false;
        }
        if(quickReservationBoatService.quickReservationExists(boatReservation.getBoat().getId(),
                boatReservation.getStartDate(),boatReservation.getEndDate())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean reservationExists(Long boatId, LocalDateTime startDate, LocalDateTime endDate){
        if(!boatReservationRepository.reservationExists(boatId,startDate,endDate).isEmpty()) return true;
        return  false;
    }

    @Override
    public boolean ownerIsNotAvailableReservation(String username, LocalDateTime start, LocalDateTime end) {
        return boatReservationRepository.ownerIsNotAvailable(username, start, end);
    }

    @Override
    public Set<BoatReservation> findReservationsByOwnerUsername(String username) {
        return boatReservationRepository.findReservationsByOwnerUsername(username,LocalDateTime.now());
    }

    @Override
    public boolean futureReservationsExist(LocalDateTime currentDate,Long boatId) {
        return boatReservationRepository.futureReservationsExist(currentDate,boatId);
    }

    @Override
    public List<BoatReservation> getPastReservations(String ownersUsername) {
        LocalDateTime currentDate=LocalDateTime.now();
        List<BoatReservation> pastReservations=new ArrayList<>();
        for(BoatReservation boatReservation: boatReservationRepository.getReservationsByOwnerUsername(ownersUsername)){
            if(currentDate.isAfter(boatReservation.getEndDate()))
                pastReservations.add(boatReservation);
        }
        return pastReservations;
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username) {
        return boatReservationRepository.countReservationsInPeriod(start,end,username);
    }

    @Override
    public List<BoatReservation> findReservationsToSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end) {
        return boatReservationRepository.findReservationsInPeriodToSumProfit(ownerUsername,start,end);
    }
    @Override
    public double sumProfitOfPricesCalculatedByHours(List<BoatReservation> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        double numOfHoursForReportReservation= 0.0;
        double reservationHours=0.0;
        for(BoatReservation boatReservation: reservations){
            numOfHoursForReportReservation= calculateOverlapingDates(start,end,boatReservation.getStartDate(),boatReservation.getEndDate());
            reservationHours=Duration.between(boatReservation.getStartDate(),boatReservation.getEndDate()).toMinutes()/60d;
            profit+=(numOfHoursForReportReservation* boatReservation.getPaymentInformation().getOwnersPart())/reservationHours;
        }
        return profit;
    }

    private double calculateOverlapingDates(LocalDateTime startReport, LocalDateTime endReport, LocalDateTime startReservation, LocalDateTime endReservation){
        double numberOfOverlappingHours=0;
        LocalDateTime start = Collections.max(Arrays.asList(startReport, startReservation));
        LocalDateTime end = Collections.min(Arrays.asList(endReport, endReservation));
        numberOfOverlappingHours = ChronoUnit.MINUTES.between(start, end);
        return numberOfOverlappingHours/60d;
    }

    @Override
    public BoatReservation getById(Long reservationId) {
        return boatReservationRepository.getById(reservationId);
    }

    @Override
    public void save(BoatReservation reservation) {
        boatReservationRepository.save(reservation);
    }

    private void sendMailNotification(BoatReservation boatReservation,String email){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = boatReservation.getBoat().getName() + " is booked from " + boatReservation.getStartDate().format(formatter) + " to " + boatReservation.getEndDate().format(formatter) + " .";
            mailService.sendMail(email, message, new BoatReservationSuccessfullInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public Set<Boat> getAvailableBoats(SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsBoatDto) {
        Set<Boat> availableBoats = new HashSet<>();
        for(AvailableBoatPeriod boatPeriod:availableBoatPeriodService.findPeriodsBetweenDates(searchAvailablePeriodsBoatDto.getStartDate(), searchAvailablePeriodsBoatDto.getEndDate())){
            if(searchAvailablePeriodsBoatDto.getPrice()!=0){
                if(boatPeriod.getBoat().getPrice()>searchAvailablePeriodsBoatDto.getPrice())
                    continue;
            }
            if(searchAvailablePeriodsBoatDto.getMaxPeople()>boatPeriod.getBoat().getMaxPeople())
                continue;
            if(boatWasAlreadyReservedInPeriod(boatPeriod.getBoat().getId(),searchAvailablePeriodsBoatDto))
                continue;
            if(!boatNotFreeInPeriod(boatPeriod.getBoat().getId(), searchAvailablePeriodsBoatDto.getStartDate(), searchAvailablePeriodsBoatDto.getEndDate()))
                availableBoats.add(boatPeriod.getBoat());
        }
        return availableBoats;
    }

    private boolean boatWasAlreadyReservedInPeriod(Long boatId, SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsBoatDto) {
        return boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(boatId,clientService.findByUsername(searchAvailablePeriodsBoatDto.getUsername()).getId(), searchAvailablePeriodsBoatDto.getStartDate(), searchAvailablePeriodsBoatDto.getEndDate());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean makeReservation(BoatReservationDto boatReservationDto) throws Exception {
        if(boatReservationDto == null) return  false;
        if(boatNotFreeInPeriod(boatReservationDto.getBoatDto().getId(), boatReservationDto.getStartDate(), boatReservationDto.getEndDate()))
            return false;
        BoatReservation boatReservation = setUpBoatReservationFromDto(boatReservationDto);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmount(boatReservation, boatReservation.getBoat().getBoatOwner());
        boatReservation.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(boatReservation.getClient(), boatReservation.getBoat().getBoatOwner());
        boatReservationRepository.save(boatReservation);


        if(boatReservationDto.getAddedAdditionalServices()!=null)
        {
            boatReservation.setAddedAdditionalServices(additionalServiceMapper.additionalServicesDtoToAdditionalServices(boatReservationDto.getAddedAdditionalServices()));

            boatReservationRepository.save(boatReservation);

        }
        sendReservationMailToClient(boatReservationDto);
        return true;
    }

    @Override
    public boolean boatNotFreeInPeriod(Long boatId, LocalDateTime startDate, LocalDateTime endDate) {
        return boatReservationRepository.boatReservedInPeriod(boatId, startDate, endDate) ||
                quickReservationBoatService.boatHasQuickReservationInPeriod(boatId, startDate, endDate);
    }

    @Override
    public List<BoatReservation> findAllReservationsForAdminProfit(LocalDateTime start, LocalDateTime end) {
        return boatReservationRepository.findAllReservationsForAdminProfit(start,end);
    }

    @Override
    public double sumProfitForAdminOfPricesCalculatedByHours(List<BoatReservation> reservations, LocalDateTime start, LocalDateTime end) {
        double profit=0.0;
        double numOfHoursForReportReservation= 0.0;
        double reservationHours=0.0;
        for(BoatReservation boatReservation: reservations){
            numOfHoursForReportReservation= calculateOverlapingDates(start,end,boatReservation.getStartDate(),boatReservation.getEndDate());
            reservationHours=Duration.between(boatReservation.getStartDate(),boatReservation.getEndDate()).toMinutes()/60d;
            profit+=(numOfHoursForReportReservation* boatReservation.getPaymentInformation().getCompanysPart())/reservationHours;
        }
        return profit;
    }

    @Override
    public Integer countReservationsByBoatInPeriod(LocalDateTime start, LocalDateTime end, Long id) {
        return boatReservationRepository.countReservationsInPeriodByBoatId(start,end,id);
    }

    @Override
    public List<BoatReservation> findReservationsByBoatToSumProfit(Long id, LocalDateTime start, LocalDateTime end) {
        return boatReservationRepository.findReservationsInPeriodByBoatToSumProfit(id,start,end);
    }

    @Override
    public Set<Boat> searchAvailableBoats(SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsBoatDto) {
        Set<Boat> boats = new HashSet<>();
        for(Boat availableBoat :getAvailableBoats(searchAvailablePeriodsBoatDto)){
            if(!availableBoat.getAddress().getStreetAndNum().toLowerCase().contains(searchAvailablePeriodsBoatDto.getStreetAndNum().toLowerCase()))
                continue;
            if(!availableBoat.getAddress().getCity().toLowerCase().contains(searchAvailablePeriodsBoatDto.getCity().toLowerCase()))
                continue;
            if(!availableBoat.getAddress().getCountry().toLowerCase().contains(searchAvailablePeriodsBoatDto.getCountry().toLowerCase()))
                continue;
            if(availableBoat.getRating()!=0.0 && availableBoat.getRating()<searchAvailablePeriodsBoatDto.getRating())
                continue;
            boats.add(availableBoat);
        }
        return boats;
    }

    @Override
    public Set<BoatReservation> getUpcomingClientReservationsByUsername(String clientUsername) {
        return boatReservationRepository.getUpcomingClientReservations(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    @Override
    public Set<BoatReservation> getClientReservationHistoryByUsername(String clientUsername) {
        return boatReservationRepository.getClientReservationsHistory(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    @Override
    public boolean reservationExists(Long id) {
        return boatReservationRepository.reservationExists(id);
    }

    @Override
    public boolean checkIfOwnerHasFutureReservations(String username) {
        return boatReservationRepository.checkIfOwnerHasFutureReservations(username,LocalDateTime.now());
    }

    @Override
    public boolean checkIfClientHasFutureReservations(Long userId) {
        return boatReservationRepository.checkIfClientHasFutureReservations(userId,LocalDateTime.now());
    }

    @Override
    public boolean checkIfReservationIsEvaluated(Long reservationId) {
        return boatReservationRepository.getById(reservationId).isEvaluated();
    }

    @Override
    public void markThatReservationIsEvaluated(Long reservationId) {
        BoatReservation boatReservation = boatReservationRepository.getById(reservationId);
        boatReservation.setEvaluated(true);
        boatReservationRepository.save(boatReservation);
    }

    private void sendReservationMailToClient(BoatReservationDto boatReservationDto) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = boatReservationDto.getBoatDto().getName() + " is booked from " + boatReservationDto.getStartDate().format(formatter) + " to " + boatReservationDto.getEndDate().format(formatter) + " .";
            mailService.sendMail(boatReservationDto.getClientUsername(), message, new BoatReservationSuccessfulInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    private BoatReservation setUpBoatReservationFromDto(BoatReservationDto boatReservationDto) {
        BoatOwner boatOwner = boatOwnerService.findByUsername(boatReservationDto.getBoatDto().getOwnersUsername());
        BoatReservation boatReservation = boatReservationMapper.boatReservationDtoToBoatReservation(boatReservationDto);
        boatReservation.getBoat().setBoatOwner(boatOwner);
        boatReservation.setClient(clientService.findByUsername(boatReservationDto.getClientUsername()));
        boatReservation.setNeedsCaptainService(boatReservationDto.getNeedsCaptainServices());
        return boatReservation;
    }
}
