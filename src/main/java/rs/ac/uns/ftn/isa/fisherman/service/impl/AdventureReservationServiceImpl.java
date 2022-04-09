package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.mail.AdventureReservationSuccessfullInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.AdventureReservationRepository;
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
public class AdventureReservationServiceImpl implements AdventureReservationService {
    private final Logger logger= LoggerFactory.getLogger(FirebaseServiceImpl.class);
    @Autowired
    private AdventureReservationRepository adventureReservationRepository;
    @Autowired
    private QuickReservationAdventureService quickReservationAdventureService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ReservationPaymentService reservationPaymentService;

    @Autowired
    private AvailableInstructorPeriodService availableInstructorPeriodService;
    @Override
    public boolean instructorCreates(AdventureReservation adventureReservation, String clientUsername) {
        Client client = clientService.findByUsername(clientUsername);
        if(!validateForReservation(adventureReservation,client)) return false;
        AdventureReservation successfullReservation=new AdventureReservation(adventureReservation.getId(),adventureReservation.getStartDate()
        ,adventureReservation.getEndDate(),client,adventureReservation.getPaymentInformation(),adventureReservation.isOwnerWroteAReport(),adventureReservation.getOwnersUsername(),
                adventureReservation.getAdventure(),adventureReservation.getFishingInstructor(),null);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmount(successfullReservation,successfullReservation.getFishingInstructor());
        successfullReservation.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(client,successfullReservation.getFishingInstructor());
        adventureReservationRepository.save(successfullReservation);
        if(adventureReservation.getAddedAdditionalServices()!=null){
            successfullReservation.setAddedAdditionalServices(adventureReservation.getAddedAdditionalServices());
            adventureReservationRepository.save(successfullReservation);
        }

        sendMailNotification(successfullReservation,client.getUsername());
        return true;
    }

    @Override
    public Set<AdventureReservation> getPresentByInstructorId(String username) {
        return adventureReservationRepository.getPresentByInstructorId(username,LocalDateTime.now());
    }

    @Override
    public boolean reservationExists(String id, LocalDateTime startDate, LocalDateTime endDate) {
        return adventureReservationRepository.reservationExists(id,startDate,endDate);

    }

    @Override
    public boolean futureReservationsExist(LocalDateTime currentDate, Long id) {
        return  adventureReservationRepository.futureReservationsExist(currentDate,id);
    }

    @Override
    public Set<AdventureReservation> getPastReservations(String username) {
        return adventureReservationRepository.getPastReservations(username,LocalDateTime.now());
    }


    private boolean validateForReservation(AdventureReservation adventureReservation, Client client){
        LocalDateTime currentDate= LocalDateTime.now();
        if(client==null) return false;
      /*  if(!adventureReservationRepository.clientHasReservation(adventureReservation.getFishingInstructor().
                getId(),client.getId(),currentDate)) return false;*/

        if(!availableInstructorPeriodService.instructorIsAvailable(adventureReservation.getFishingInstructor()
                .getId(),adventureReservation.getStartDate(),adventureReservation.getEndDate())) return false;

        if(adventureReservationRepository.reservationExists(adventureReservation.getOwnersUsername()
                ,adventureReservation.getStartDate(),adventureReservation.getEndDate())) return false;

        if(quickReservationAdventureService.quickReservationExists(adventureReservation.getOwnersUsername(),
                adventureReservation.getStartDate(),adventureReservation.getEndDate())) return false;

        return true;
    }
    private void sendMailNotification(AdventureReservation adventureReservation,String email){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = adventureReservation.getAdventure().getName() + " is booked from " + adventureReservation.getStartDate().format(formatter) + " to " + adventureReservation.getEndDate().format(formatter) + " .";
            mailService.sendMail(email, message, new AdventureReservationSuccessfullInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username) {
        return adventureReservationRepository.countReservationsInPeriod(start,end,username);
    }
    @Override
    public double findReservationsAndSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end) {
        return sumProfitOfPricesCalucatedByHours(adventureReservationRepository.findReservationsInPeriodToSumProfit(ownerUsername,start,end),start,end);
    }
    @Override
    public double sumProfitOfPricesCalucatedByHours(List<AdventureReservation> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        double numOfHoursForReportReservation= 0.0;
        double reservationHours=0.0;
        for(AdventureReservation adventureReservation: reservations){
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
        return numberOfOverlappingHours/60d;
    }


    @Override
    public AdventureReservation findById(Long id) {
        return adventureReservationRepository.getById(id);
    }

    @Override
    public void save(AdventureReservation adventureReservation) {
        adventureReservationRepository.save(adventureReservation);
    }
}
