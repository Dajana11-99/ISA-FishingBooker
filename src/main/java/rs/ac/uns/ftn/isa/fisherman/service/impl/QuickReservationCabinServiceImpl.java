package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationCabinDto;
import rs.ac.uns.ftn.isa.fisherman.mail.CabinReservationSuccessfulInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.mail.QuickActionCabinInfo;
import rs.ac.uns.ftn.isa.fisherman.model.PaymentInformation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationCabinRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class QuickReservationCabinServiceImpl implements QuickReservationCabinService {

    @Autowired
    private AvailableCabinPeriodService availableCabinPeriodService;
    @Autowired
    private ReservationCabinService reservationCabinService;
    @Autowired
    private QuickReservationCabinRepository quickReservationCabinRepository;
    @Autowired
    private CabinSubscriptionService cabinSubscriptionService;
    @Autowired
    private ReservationPaymentService reservationPaymentService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ClientService clientService;
    private final Logger logger= LoggerFactory.getLogger(FirebaseServiceImpl.class);

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean ownerCreates(QuickReservationCabin quickReservationCabin) throws Exception {
        QuickReservationCabin successfullQuickReservation = null;
        if(!validateForReservation(quickReservationCabin)) return false;
        successfullQuickReservation=new QuickReservationCabin(quickReservationCabin.getId(),quickReservationCabin.getStartDate(),
                quickReservationCabin.getEndDate(),null,quickReservationCabin.getPaymentInformation(), quickReservationCabin.isOwnerWroteAReport(),
                quickReservationCabin.getOwnersUsername(),quickReservationCabin.getCabin(),quickReservationCabin.getDiscount(),null);
        successfullQuickReservation.setEvaluated(false);
        quickReservationCabinRepository.save(successfullQuickReservation);
        if(quickReservationCabin.getAddedAdditionalServices()!=null) {
            successfullQuickReservation.setAddedAdditionalServices(quickReservationCabin.getAddedAdditionalServices());
            quickReservationCabinRepository.save(successfullQuickReservation);
        }
        sendMailNotificationToSubscribedUsers(successfullQuickReservation.getCabin().getId(),successfullQuickReservation.getCabin().getName());
        return true;
    }
    @Override
    public Set<QuickReservationCabin> getAllReports(){
        return  quickReservationCabinRepository.getAllReports();
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime startWeek, LocalDateTime endWeek, String ownerUsername) {
        return quickReservationCabinRepository.countReservationsOfThisWeek(startWeek,endWeek,ownerUsername);
    }

    @Override
    public Integer countReservationsInPeriodByCabinId(LocalDateTime start, LocalDateTime end, Long cabinId) {
        return quickReservationCabinRepository.countReservationsInPeriodByCabinId(start,end,cabinId);
    }

    @Override
    public List<QuickReservationCabin> findReservationsToSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end) {
        return quickReservationCabinRepository.findReservationsInPeriodToSumProfit(ownerUsername,start,end);
    }
    @Override
    public double sumProfitOfPricesCalculatedByDays(List<QuickReservationCabin> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        long numOfDaysForReportReservation= 0;
        for(QuickReservationCabin quickReservationCabin: reservations){
            numOfDaysForReportReservation= calculateOverlapingDates(start,end,quickReservationCabin.getStartDate(),quickReservationCabin.getEndDate());
            profit+=(numOfDaysForReportReservation* quickReservationCabin.getPaymentInformation().getOwnersPart())/Duration.between(quickReservationCabin.getStartDate(),quickReservationCabin.getEndDate()).toDays();
        }
        return profit;
    }
    @Override
    public double sumProfitOfPricesCalculatedByDaysForAdmin(List<QuickReservationCabin> reservations, LocalDateTime start, LocalDateTime end){
        double profit=0.0;
        long numOfDaysForReportReservation= 0;
        for(QuickReservationCabin quickReservationCabin: reservations){
            numOfDaysForReportReservation= calculateOverlapingDates(start,end,quickReservationCabin.getStartDate(),quickReservationCabin.getEndDate());
            profit+=(numOfDaysForReportReservation* quickReservationCabin.getPaymentInformation().getCompanysPart())/Duration.between(quickReservationCabin.getStartDate(),quickReservationCabin.getEndDate()).toDays();
        }
        return profit;
    }

    @Override
    public boolean checkIfOwnerHasFutureReservations(String username) {
        return quickReservationCabinRepository.checkIfOwnerHasFutureReservations(username,LocalDateTime.now()) ;
    }

    @Override
    public boolean checkIfClientHasFutureReservations(Long userId) {
        return  quickReservationCabinRepository.checkIfClientHasFutureReservations(userId,LocalDateTime.now());
    }

    @Override
    public List<QuickReservationCabin> findReservationsByCabinToSumProfit(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        return quickReservationCabinRepository.findReservationsInPeriodByCabinToSumProfit(id,localDateTime,localDateTime1);
    }

    @Override
    public boolean checkIfReservationIsEvaluated(Long reservationId) {
        return quickReservationCabinRepository.getById(reservationId).isEvaluated();
    }

    @Override
    public void markThatReservationIsEvaluated(Long reservationId) {
        QuickReservationCabin quickReservationCabin = quickReservationCabinRepository.getById(reservationId);
        quickReservationCabin.setEvaluated(true);
        quickReservationCabinRepository.save(quickReservationCabin);
    }

    @Override
    public QuickReservationCabin getById(Long reservationId) {
        return quickReservationCabinRepository.getById(reservationId);
    }

    @Override
    public boolean cabinHasTakenQuickReservationInPeriod(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationCabinRepository.cabinHasTakenQuickReservationInPeriod(id, startDate, endDate);
    }

    private long calculateOverlapingDates(LocalDateTime startReport, LocalDateTime endReport, LocalDateTime startReservation, LocalDateTime endReservation){
        long numberOfOverlappingDates=0;
        LocalDate start = Collections.max(Arrays.asList(startReport.toLocalDate(), startReservation.toLocalDate()));
        LocalDate end = Collections.min(Arrays.asList(endReport.toLocalDate(), endReservation.toLocalDate()));
        numberOfOverlappingDates = ChronoUnit.DAYS.between(start, end);
        return numberOfOverlappingDates;
    }

    @Override
    public void save(QuickReservationCabin reservation) {
        quickReservationCabinRepository.save(reservation);
    }

    @Override
    public Set<QuickReservationCabin> getAvailableReservations() {
        return quickReservationCabinRepository.getAvailableReservations(LocalDateTime.now());
    }

    @Override
    public boolean makeQuickReservation(QuickReservationCabinDto quickReservationCabinDto) {
        QuickReservationCabin quickReservationCabin = quickReservationCabinRepository.getById(quickReservationCabinDto.getId());
        if(reservationCabinService.cabinNotFreeForQuickReservationInPeriod(quickReservationCabin.getCabin().getId(), quickReservationCabin.getStartDate(), quickReservationCabin.getEndDate()))
            return false;
        quickReservationCabin.setClient(clientService.findByUsername(quickReservationCabinDto.getClientUsername()));
        quickReservationCabinRepository.save(quickReservationCabin);
        PaymentInformation paymentInformation = reservationPaymentService.setTotalPaymentAmountForQuickAction(quickReservationCabin,quickReservationCabin.getCabin().getCabinOwner(),quickReservationCabin.getDiscount());
        quickReservationCabin.setPaymentInformation(paymentInformation);
        reservationPaymentService.updateUserRankAfterReservation(quickReservationCabin.getClient(),quickReservationCabin.getCabin().getCabinOwner());
        quickReservationCabinRepository.save(quickReservationCabin);
        SendReservationMailToClient(quickReservationCabinDto);
        return true;
    }

    private void SendReservationMailToClient(QuickReservationCabinDto quickReservationCabinDto) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
            String message = quickReservationCabinDto.getCabinDto().getName() + " is booked from " + quickReservationCabinDto.getStartDate().format(formatter) + " to " + quickReservationCabinDto.getEndDate().format(formatter) + " .";
            mailService.sendMail(quickReservationCabinDto.getClientUsername(), message, new CabinReservationSuccessfulInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public boolean cabinHasQuickReservationInPeriod(Long cabinId, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationCabinRepository.cabinHasQuickReservationInPeriod(cabinId, startDate, endDate);
    }

    @Override
    public Set<QuickReservationCabin> getUpcomingClientQuickReservations(String clientUsername) {
        return quickReservationCabinRepository.getUpcomingClientQuickReservations(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    @Override
    public Set<QuickReservationCabin> getClientQuickReservationsHistory(String clientUsername) {
        return quickReservationCabinRepository.getClientQuickReservationsHistory(clientService.findByUsername(clientUsername).getId(), LocalDateTime.now());
    }

    @Override
    public List<QuickReservationCabin> findAllQucikReservationsForAdminProfit(LocalDateTime start, LocalDateTime end) {
        return quickReservationCabinRepository.findAllQuickReservationsForAdminProfit(start,end);
    }

    private void sendMailNotificationToSubscribedUsers(Long cabinId,String cabinName){
        Set<String> subscriptionEmails=cabinSubscriptionService.findCabinSubscribers(cabinId);

        for(String email: subscriptionEmails) {
            try {
                String message = cabinName;
                mailService.sendMail(email, message, new QuickActionCabinInfo());
            } catch (MessagingException e) {
                logger.error(e.toString());
            }
        }
    }

    @Override
    public Set<QuickReservationCabin> getByCabinId(Long cabinId) {
        return quickReservationCabinRepository.getByCabinId(cabinId);
    }

    @Override
    public boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        if(quickReservationCabinRepository.quickReservationExists(id,startDate,endDate).size()>0)
            return true;
        return false;
    }

    @Override
    public boolean futureQuickReservationsExist(LocalDateTime currentDate, Long id) {
        if(quickReservationCabinRepository.futureQuickReservationsExist(currentDate,id).size()>0) return true;
        return false;
    }

    @Override
    public Set<QuickReservationCabin> findReservationsByOwnerId(String ownerUsername) {
        return quickReservationCabinRepository.findReservationsByOwnerUsername(ownerUsername,LocalDateTime.now());
    }

    @Override
    public QuickReservationCabin findReservationById(Long id) {
        return quickReservationCabinRepository.getById(id);
    }


    @Override
    public Set<QuickReservationCabin> getPastReservations(String ownerUsername) {
        return quickReservationCabinRepository.getPastReservations(ownerUsername,LocalDateTime.now());
    }

    @Override
    public void ownerCreatesReview(QuickReservationCabin reservation, boolean successfull) {
        QuickReservationCabin quickReservationCabin=quickReservationCabinRepository.getById(reservation.getId());
        quickReservationCabin.setSuccessfull(successfull);
       //quickReservationCabin.getOwnersReport().setComment(reservation.getOwnersReport().getComment());
       //quickReservationCabin.getOwnersReport().setBadComment(reservation.getOwnersReport().isBadComment());
        quickReservationCabinRepository.save(quickReservationCabin);
    }

    private boolean validateForReservation(QuickReservationCabin cabinQuickReservation){
        if(!availableCabinPeriodService.cabinIsAvailable(cabinQuickReservation.getCabin()
                .getId(),cabinQuickReservation.getStartDate(),cabinQuickReservation.getEndDate())) {
            System.out.println("NEMAM SLOBODAN PERIOD");
            return false;
        }


        if(reservationCabinService.reservationExists(cabinQuickReservation.getCabin()
                .getId(),cabinQuickReservation.getStartDate(),cabinQuickReservation.getEndDate())) {

            System.out.println("imam rezervacije");
            return false;
        }

        if(quickReservationCabinRepository.quickReservationExists(cabinQuickReservation.getCabin()
                .getId(),cabinQuickReservation.getStartDate(),cabinQuickReservation.getEndDate()).size()>0) {
            System.out.println("imam quick rezervacije");
            return false;
        }

        return true;
    }
}
