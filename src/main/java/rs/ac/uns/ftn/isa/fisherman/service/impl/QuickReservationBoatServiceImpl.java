package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationBoatDto;
import rs.ac.uns.ftn.isa.fisherman.mail.BoatReservationSuccessfulInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.mail.QuickActionBoatInfo;
import rs.ac.uns.ftn.isa.fisherman.model.PaymentInformation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationBoat;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationBoatRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class QuickReservationBoatServiceImpl implements QuickReservationBoatService {
    private final Logger logger= LoggerFactory.getLogger(FirebaseServiceImpl.class);
    @Autowired
    private AvailableBoatPeriodService availableBoatPeriodService;
    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private QuickReservationBoatRepository quickReservationBoatRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationPaymentService reservationPaymentService;
    @Autowired
    private MailService mailService;

    @Autowired
    private BoatSubscriptionService boatSubscriptionService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean ownerCreates(QuickReservationBoat quickReservationBoat) throws Exception {
        if(!validateForReservation(quickReservationBoat)) return false;

        QuickReservationBoat successfullQuickReservation=new QuickReservationBoat(quickReservationBoat.getId(), quickReservationBoat.getStartDate(),
                quickReservationBoat.getEndDate(),null,quickReservationBoat.getPaymentInformation(),quickReservationBoat.isOwnerWroteAReport(),
                quickReservationBoat.getOwnersUsername(),
                quickReservationBoat.getBoat(),quickReservationBoat.getDiscount(),null, quickReservationBoat.getNeedsCaptainServices());
        successfullQuickReservation.setEvaluated(false);
        if(quickReservationBoat.getAddedAdditionalServices()!=null){
            if(quickReservationBoat.getNeedsCaptainServices()) {
                if (ownerIsNotAvailable(successfullQuickReservation.getBoat().getBoatOwner().getUsername(),
                        successfullQuickReservation.getStartDate(), successfullQuickReservation.getEndDate())) return false;
            }

            quickReservationBoatRepository.save(successfullQuickReservation);

            successfullQuickReservation.setAddedAdditionalServices(quickReservationBoat.getAddedAdditionalServices());

            quickReservationBoatRepository.save(successfullQuickReservation);

        }else{
            quickReservationBoatRepository.save(successfullQuickReservation);
        }
        sendMailNotificationToSubscribedUsers(successfullQuickReservation.getBoat().getId(),successfullQuickReservation.getBoat().getName());

        return true;
    }
    private boolean ownerIsNotAvailable(String username, LocalDateTime start, LocalDateTime end){
        if (quickReservationBoatRepository.ownerIsNotAvailable(username, start, end)) return true;

        if(boatReservationService.ownerIsNotAvailableReservation(username, start, end)) return true;
        return  false;
    }
    @Override
    public boolean ownerIsNotAvailableQuickResrvation(String ownerUsername, LocalDateTime start, LocalDateTime end){
        return quickReservationBoatRepository.ownerIsNotAvailable(ownerUsername, start, end);
    }

    @Override
    public Set<QuickReservationBoat> findReservationsByOwnerUsername(String username) {
        return quickReservationBoatRepository.findReservationsByOwnerId(username,LocalDateTime.now());
    }

    @Override
    public Set<QuickReservationBoat> getByBoatId(Long cabinId) {
        return quickReservationBoatRepository.getByBoatId(cabinId);
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username) {
        return quickReservationBoatRepository.countReservationsInPeriod(start,end,username);
    }


    @Override
    public QuickReservationBoat getById(Long reservationId) {
        return quickReservationBoatRepository.getById(reservationId);
    }

    @Override
    public void save(QuickReservationBoat reservation) {
        quickReservationBoatRepository.save(reservation);
    }

    @Override
    public Set<QuickReservationBoat> getAvailableReservations() {
        Set<QuickReservationBoat> quickReservationBoats = new HashSet<>();
        for(QuickReservationBoat quickReservationBoat:quickReservationBoatRepository.findAll()){
            if(quickReservationBoat.getClient()==null && quickReservationBoat.getStartDate().isAfter(LocalDateTime.now()))
                quickReservationBoats.add(quickReservationBoat);
        }
        return quickReservationBoats;
    }

    @Override
    public boolean makeQuickReservation(QuickReservationBoatDto quickReservationBoatDto) {
        QuickReservationBoat quickReservationBoat = quickReservationBoatRepository.getById(quickReservationBoatDto.getId());
        if(boatReservationService.boatNotFreeForQuickReservation(quickReservationBoat.getBoat().getId(), quickReservationBoat.getStartDate(), quickReservationBoat.getEndDate()))
            return false;
        quickReservationBoat.setClient(clientService.findByUsername(quickReservationBoatDto.getClientUsername()));
        quickReservationBoatRepository.save(quickReservationBoat);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmountForQuickAction(quickReservationBoat,quickReservationBoat.getBoat().getBoatOwner(),quickReservationBoat.getDiscount());
        quickReservationBoat.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(quickReservationBoat.getClient(),quickReservationBoat.getBoat().getBoatOwner());
        quickReservationBoatRepository.save(quickReservationBoat);
        SendReservationMailToClient(quickReservationBoatDto);
        return true;
    }

    private void SendReservationMailToClient(QuickReservationBoatDto quickReservationBoatDto) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = quickReservationBoatDto.getBoatDto().getName() + " is booked from " + quickReservationBoatDto.getStartDate().format(formatter) + " to " + quickReservationBoatDto.getEndDate().format(formatter) + " .";
            mailService.sendMail(quickReservationBoatDto.getClientUsername(), message, new BoatReservationSuccessfulInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public boolean boatHasQuickReservationInPeriod(Long boatId, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationBoatRepository.boatHasQuickReservationInPeriod(boatId, startDate, endDate);
    }

    @Override
    public Set<QuickReservationBoat> getUpcomingClientQuickReservations(String clientUsername) {
        return quickReservationBoatRepository.getUpcomingClientQuickReservations(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    @Override
    public Set<QuickReservationBoat> getClientQuickReservationsHistory(String clientUsername) {
        Long clientId = clientService.findByUsername(clientUsername).getId();
        Set<QuickReservationBoat> quickReservationBoats = new HashSet<>();
        for(QuickReservationBoat quickReservationBoat:quickReservationBoatRepository.findAll()){
            if(quickReservationBoat.getClient()!=null && quickReservationBoat.getClient().getId().equals(clientId)
                    && quickReservationBoat.getStartDate().isBefore(LocalDateTime.now()))
                quickReservationBoats.add(quickReservationBoat);
        }
        return quickReservationBoats;
    }

    @Override
    public List<QuickReservationBoat> findAllQucikReservationsForAdminProfit(LocalDateTime start, LocalDateTime end) {
        return quickReservationBoatRepository.findAllQuickReservationsForAdminProfit(start,end);
    }

    @Override
    public double sumProfitOfPricesCalculatedByHoursForAdmin(List<QuickReservationBoat> reservations, LocalDateTime start, LocalDateTime end) {
        double profit=0.0;
        double numOfHoursForReportReservation= 0.0;
        double reservationHours=0.0;
        for(QuickReservationBoat boatReservation: reservations){
            numOfHoursForReportReservation= calculateOverlapingDates(start,end,boatReservation.getStartDate(),boatReservation.getEndDate());
            reservationHours= Duration.between(boatReservation.getStartDate(),boatReservation.getEndDate()).toMinutes()/60d;

            profit+=(numOfHoursForReportReservation* boatReservation.getPaymentInformation().getCompanysPart())/reservationHours;

        }
        return profit;
    }

    @Override
    public boolean checkIfOwnerHasFutureReservations(String username) {
        return quickReservationBoatRepository.checkIfOwnerHasFutureReservations(username,LocalDateTime.now());
    }

    @Override
    public boolean checkIfClientHasFutureReservations(Long userId) {
        return quickReservationBoatRepository.checkIfClientHasFutureReservations(userId,LocalDateTime.now());
    }

    @Override
    public Integer countReservationsByBoatInPeriod(LocalDateTime start, LocalDateTime end, Long id) {
        return quickReservationBoatRepository.countReservationsByBoatInPeriod(start,end,id);
    }

    @Override
    public List<QuickReservationBoat> findReservationsToSumProfit(String username, LocalDateTime start, LocalDateTime end) {
        return quickReservationBoatRepository.findReservationsInPeriodToSumProfit(username,start,end);
    }

    @Override
    public List<QuickReservationBoat> findReservationsToSumProfitByBoat(Long id, LocalDateTime start, LocalDateTime end) {
        return quickReservationBoatRepository.findReservationsInPeriodByBoatToSumProfit(id,start,end);
    }

    @Override
    public boolean checkIfReservationIsEvaluated(Long reservationId) {
        return quickReservationBoatRepository.getById(reservationId).isEvaluated();
    }

    @Override
    public void markThatReservationIsEvaluated(Long reservationId) {
        QuickReservationBoat quickReservationBoat = quickReservationBoatRepository.getById(reservationId);
        quickReservationBoat.setEvaluated(true);
        quickReservationBoatRepository.save(quickReservationBoat);
    }

    @Override
    public boolean boatHasTakenQuickReservationInPeriod(Long boatId, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationBoatRepository.boatHasTakenQuickReservationInPeriod(boatId, startDate, endDate);
    }

    @Override
    public boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        if(quickReservationBoatRepository.quickReservationExists(id,startDate,endDate).size()>0) return false;
        return true;
    }

    private boolean validateForReservation(QuickReservationBoat quickReservationBoat){
        if(!availableBoatPeriodService.boatIsAvailable(quickReservationBoat.getBoat()
                .getId(),quickReservationBoat.getStartDate(),quickReservationBoat.getEndDate())) return false;

        if(boatReservationService.reservationExists(quickReservationBoat.getBoat()
                .getId(),quickReservationBoat.getStartDate(),quickReservationBoat.getEndDate())) return false;

        if(quickReservationBoatRepository.quickReservationExists(quickReservationBoat.getBoat()
                .getId(),quickReservationBoat.getStartDate(),quickReservationBoat.getEndDate()).size()>0) return false;

        return true;
    }
    @Override
    public boolean futureQuickReservationsExist(LocalDateTime currentDate,Long boatId){
        return quickReservationBoatRepository.futureQuickReservationsExist(currentDate,boatId);
    }
    @Override
    public double findReservationsAndSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end) {

        return sumProfitOfPricesCalculatedByHours(quickReservationBoatRepository.findReservationsInPeriodToSumProfit(ownerUsername,start,end),start,end);
    }
    @Override
    public double sumProfitOfPricesCalculatedByHours(List<QuickReservationBoat> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        double numOfHoursForReportReservation= 0.0;
        double reservationHours=0.0;
        for(QuickReservationBoat boatReservation: reservations){
            numOfHoursForReportReservation= calculateOverlapingDates(start,end,boatReservation.getStartDate(),boatReservation.getEndDate());
            reservationHours= Duration.between(boatReservation.getStartDate(),boatReservation.getEndDate()).toMinutes()/60d;

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
    public Set<QuickReservationBoat> getPastReservations(String username) {
        LocalDateTime currentDate=LocalDateTime.now();
        return quickReservationBoatRepository.getPastReservations(username, currentDate);
    }

    private void sendMailNotificationToSubscribedUsers(Long boatId,String boatName){
        Set<String> subscriptionEmails=boatSubscriptionService.findBoatSubscribers(boatId);
        subscriptionEmails.add("dajanazlokapa1@gmail.com");
        for(String email: subscriptionEmails) {
            try {
                String message = boatName;
                mailService.sendMail(email, message, new QuickActionBoatInfo());
            } catch (MessagingException e) {
                logger.error(e.toString());
            }
        }
    }


}
