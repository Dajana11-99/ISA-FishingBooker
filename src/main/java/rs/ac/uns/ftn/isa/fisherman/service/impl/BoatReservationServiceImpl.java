package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.mail.BoatReservationSuccessfullInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class BoatReservationServiceImpl implements BoatReservationService {
    private final Logger logger= LoggerFactory.getLogger(FirebaseServiceImpl.class);
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

    @Override
    public boolean ownerCreates(BoatReservation boatReservation, String clientUsername) {
        Client client = clientService.findByUsername(clientUsername);
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
    private boolean ownerIsNotAvailable(String ownersUsermane, LocalDateTime start, LocalDateTime end){
        if(boatReservationRepository.ownerIsNotAvailable(ownersUsermane, start, end)) return true;
        if(quickReservationBoatService.ownerIsNotAvailableQuickResrvation(ownersUsermane, start, end)) return true;
        return false;
    }
    private boolean validateForReservation(BoatReservation boatReservation,Client client){
        LocalDateTime currentDate= LocalDateTime.now();
        if(client==null) return false;
        /*if(!boatReservationRepository.clientHasReservation(boatReservation.getBoat().
                getId(),client.getId(),currentDate)) return false;
*/
        if(!availableBoatPeriodService.boatIsAvailable(boatReservation.getBoat()
                .getId(),boatReservation.getStartDate(),boatReservation.getEndDate())) return false;

        if(boatReservationRepository.reservationExists(boatReservation.getBoat()
                .getId(),boatReservation.getStartDate(),boatReservation.getEndDate())) return false;
        if(quickReservationBoatService.quickReservationExists(boatReservation.getBoat().getId(),
                boatReservation.getStartDate(),boatReservation.getEndDate())) return false;
        return true;
    }

    @Override
    public boolean reservationExists(Long boatId, LocalDateTime startDate, LocalDateTime endDate){
        return boatReservationRepository.reservationExists(boatId,startDate,endDate);
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
    public Set<BoatReservation> getPastReservations(String ownersUsername) {
        return boatReservationRepository.getPastReservations(ownersUsername,LocalDateTime.now());
    }

    @Override
    public void ownerCreatesReview(BoatReservation boatReservation, boolean successfull) {
        BoatReservation oldReservation = boatReservationRepository.getById(boatReservation.getId());
        oldReservation.setSuccessfull(successfull);
       // oldReservation.getOwnersReport().setComment(boatReservation.getOwnersReport().getComment());
       // oldReservation.getOwnersReport().setBadComment(boatReservation.getOwnersReport().isBadComment());
        boatReservationRepository.save(oldReservation);
    }


    @Override
    public Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username) {
        return boatReservationRepository.countReservationsInPeriod(start,end,username);
    }

    @Override
    public Double sumProfit(String username, LocalDateTime start, LocalDateTime end) {
        return boatReservationRepository.sumProfit(username,start,end);
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

}
