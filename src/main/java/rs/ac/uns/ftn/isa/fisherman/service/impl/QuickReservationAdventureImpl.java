package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.mail.AdventureReservationSuccessfulInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.mail.QuickActionAdventureInfo;
import rs.ac.uns.ftn.isa.fisherman.model.PaymentInformation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationAdventureRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class QuickReservationAdventureImpl implements QuickReservationAdventureService {

    private final Logger logger= LoggerFactory.getLogger(FirebaseServiceImpl.class);
    @Autowired
    private QuickReservationAdventureRepository quickReservationAdventureRepository;
    @Autowired
    private AvailableInstructorPeriodService availableInstructorPeriodService;
    @Autowired
    private AdventureReservationService adventureReservationService;
    @Autowired
    private AdventureSubscriptionService adventureSubscriptionService;
    @Autowired
    private ReservationPaymentService reservationPaymentService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ClientService clientService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean instructorCreates(QuickReservationAdventure quickReservationAdventure) throws Exception {
        if(quickReservationAdventure == null) return false;
        if(!validateForReservation(quickReservationAdventure)) return false;

        QuickReservationAdventure successfullQuickReservation=new QuickReservationAdventure(quickReservationAdventure.getId(),quickReservationAdventure.getStartDate(),
                quickReservationAdventure.getEndDate(),null,quickReservationAdventure.getPaymentInformation(),quickReservationAdventure.isOwnerWroteAReport(),
                quickReservationAdventure.getOwnersUsername(),
                quickReservationAdventure.getAdventure(),quickReservationAdventure.getFishingInstructor(),quickReservationAdventure.getDiscount(),null);
        successfullQuickReservation.setEvaluated(false);

        quickReservationAdventureRepository.save(successfullQuickReservation);


        if(quickReservationAdventure.getAddedAdditionalServices()!=null){
            successfullQuickReservation.setAddedAdditionalServices(quickReservationAdventure.getAddedAdditionalServices());

            quickReservationAdventureRepository.save(successfullQuickReservation);

        }
        sendMailNotificationToSubscribedUsers(successfullQuickReservation.getAdventure().getId(),successfullQuickReservation.getAdventure().getName());
        return true;
    }

    @Override
    public Set<QuickReservationAdventure> getByInstructorUsername(String username) {
        return  quickReservationAdventureRepository.getByInstructorUsername(username,LocalDateTime.now());
    }

    @Override
    public boolean quickReservationExists(String username, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationAdventureRepository.quickReservationExists(username,startDate,endDate);
    }

    @Override
    public boolean futureQuickReservationsExist(LocalDateTime currentDate,Long id) {
        return quickReservationAdventureRepository.futureQuickReservationsExist(currentDate,id);
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username) {
        return quickReservationAdventureRepository.countReservationsInPeriod(start,end,username);
    }


    @Override
    public void save(QuickReservationAdventure quickReservationAdventure) {
        quickReservationAdventureRepository.save(quickReservationAdventure);
    }

    @Override
    public QuickReservationAdventure findById(Long id) {
        return quickReservationAdventureRepository.getById(id);
    }

    @Override
    public Set<QuickReservationAdventure> getAvailableReservations() {
        return quickReservationAdventureRepository.getAvailableReservations(LocalDateTime.now());
    }

    @Override
    public boolean makeQuickReservation(QuickReservationAdventureDto quickReservationAdventureDto) {
        if(adventureReservationService.fishingInstructorNotFreeForQuickReservation(quickReservationAdventureDto.getOwnersUsername(), quickReservationAdventureDto.getStartDate(), quickReservationAdventureDto.getEndDate()))
            return false;
        QuickReservationAdventure quickReservationAdventure = quickReservationAdventureRepository.getById(quickReservationAdventureDto.getId());
        quickReservationAdventure.setClient(clientService.findByUsername(quickReservationAdventureDto.getClientUsername()));
        quickReservationAdventureRepository.save(quickReservationAdventure);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmountForQuickAction(quickReservationAdventure,quickReservationAdventure.getFishingInstructor(),quickReservationAdventure.getDiscount());
        quickReservationAdventure.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(quickReservationAdventure.getClient(),quickReservationAdventure.getFishingInstructor());
        quickReservationAdventureRepository.save(quickReservationAdventure);
        SendReservationMailToClient(quickReservationAdventureDto);
        return true;
    }



    @Override
    public boolean fishingInstructorNotFree(String instructorUsername, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationAdventureRepository.instructorHasReservationInPeriod(instructorUsername, startDate, endDate);
    }

    @Override
    public Set<QuickReservationAdventure> getUpcomingClientQuickReservations(String clientUsername) {
        return quickReservationAdventureRepository.getUpcomingClientQuickReservations(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    @Override
    public Set<QuickReservationAdventure> getClientQuickReservationsHistory(String clientUsername) {
        return quickReservationAdventureRepository.getClientQuickReservationsHistory(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    @Override
    public List<QuickReservationAdventure> findAllQucikReservationsForAdminProfit(LocalDateTime start, LocalDateTime end) {
        return quickReservationAdventureRepository.findAllQuickReservationsForAdminProfit(start,end);
    }

    @Override
    public double sumProfitOfPricesCalculatedByHoursForAdmin(List<QuickReservationAdventure> reservations, LocalDateTime start, LocalDateTime end) {
        double profit=0.0;
        double numOfHoursForReportReservation= 0.0;
        double reservationHours=0.0;
        for(QuickReservationAdventure adventureReservation: reservations){
            numOfHoursForReportReservation= calculateOverlapingDates(start,end,adventureReservation.getStartDate(),adventureReservation.getEndDate());
            reservationHours= Duration.between(adventureReservation.getStartDate(),adventureReservation.getEndDate()).toMinutes()/60d;
            profit+=(numOfHoursForReportReservation* adventureReservation.getPaymentInformation().getCompanysPart())/reservationHours;
        }
        return profit;
    }

    @Override
    public boolean checkIfOwnerHasFutureReservations(String username) {
        return quickReservationAdventureRepository.checkIfOwnerHasFutureReservations(username,LocalDateTime.now());
    }

    @Override
    public boolean checkIfClientHasFutureReservations(Long userId) {
        return quickReservationAdventureRepository.checkIfClientHasFutureReservations(userId,LocalDateTime.now());
    }

    @Override
    public Integer countReservationsByAdventureInPeriod(LocalDateTime start, LocalDateTime end, Long id) {
        return quickReservationAdventureRepository.countReservationsInPeriodByAdventureId(start,end,id);
    }

    @Override
    public List<QuickReservationAdventure> findReservationsByAdventureToSumProfit(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        return quickReservationAdventureRepository.findReservationsInPeriodByAdventureToSumProfit(id,localDateTime,localDateTime1);
    }

    @Override
    public List<QuickReservationAdventure> findReservationsByOwnerToSumProfit(String username, LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        return quickReservationAdventureRepository.findReservationsInPeriodToSumProfit(username,localDateTime,localDateTime1);
    }

    @Override
    public boolean checkIfReservationIsEvaluated(Long reservationId) {
        return quickReservationAdventureRepository.getById(reservationId).isEvaluated();
    }

    @Override
    public void markThatReservationIsEvaluated(Long reservationId) {
        QuickReservationAdventure quickReservationAdventure = quickReservationAdventureRepository.getById(reservationId);
        quickReservationAdventure.setEvaluated(true);
        quickReservationAdventureRepository.save(quickReservationAdventure);
    }

    @Override
    public boolean instructorHasTakenReservationInPeriod(String instructorUsername, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationAdventureRepository.instructorHasTakenReservationInPeriod(instructorUsername, startDate, endDate);
    }

    private void SendReservationMailToClient(QuickReservationAdventureDto quickReservationAdventureDto) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = quickReservationAdventureDto.getAdventureDto().getName() + " is booked from " + quickReservationAdventureDto.getStartDate().format(formatter) + " to " + quickReservationAdventureDto.getEndDate().format(formatter) + " .";
            mailService.sendMail(quickReservationAdventureDto.getClientUsername(), message, new AdventureReservationSuccessfulInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public Set<QuickReservationAdventure> getPastReservations(String username) {
        return quickReservationAdventureRepository.getPastReservations(username,LocalDateTime.now());
    }


    private boolean validateForReservation(QuickReservationAdventure quickReservationAdventure){
        if(!availableInstructorPeriodService.instructorIsAvailable(quickReservationAdventure.getFishingInstructor()
                .getId(),quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;

        if(adventureReservationService.reservationExists(quickReservationAdventure.getOwnersUsername(),quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;


        if(quickReservationAdventureRepository.quickReservationExists(quickReservationAdventure.getOwnersUsername()
                ,quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;


        return true;
    }

    @Override
    public double sumProfitOfPricesCalucatedByHours(List<QuickReservationAdventure> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        double numOfHoursForReportReservation= 0.0;
        double reservationHours=0.0;
        for(QuickReservationAdventure adventureReservation: reservations){
            numOfHoursForReportReservation= calculateOverlapingDates(start,end,adventureReservation.getStartDate(),adventureReservation.getEndDate());
            reservationHours= Duration.between(adventureReservation.getStartDate(),adventureReservation.getEndDate()).toMinutes()/60d;
            profit+=(numOfHoursForReportReservation* adventureReservation.getPaymentInformation().getOwnersPart())/reservationHours;
        }
        return profit;
    }

    private double calculateOverlapingDates(LocalDateTime startReport, LocalDateTime endReport, LocalDateTime startReservation, LocalDateTime endReservation){
        double numberOfOverlappingHours=0;
        LocalDateTime start = Collections.max(Arrays.asList(startReport, startReservation));
        LocalDateTime end = Collections.min(Arrays.asList(endReport, endReservation));
        numberOfOverlappingHours = ChronoUnit.MINUTES.between(start, end);

        System.out.println("Minuta izmedju  "+numberOfOverlappingHours);
        System.out.println("Sati izmedju  "+numberOfOverlappingHours/60d);
        return numberOfOverlappingHours/60d;
    }


    private void sendMailNotificationToSubscribedUsers(Long adventureId,String adventureName){
        Set<String> subscriptionEmails=adventureSubscriptionService.findAdventureSubscribers(adventureId);
        for(String email: subscriptionEmails) {
            try {
                String message = adventureName;
                mailService.sendMail(email, message, new QuickActionAdventureInfo());
            } catch (MessagingException e) {
                logger.error(e.toString());
            }
        }
    }
}
