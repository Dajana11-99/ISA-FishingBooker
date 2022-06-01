package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface QuickReservationAdventureService {

    boolean instructorCreates(QuickReservationAdventure adventureReservation) throws Exception;
    Set<QuickReservationAdventure> getByInstructorUsername(String username) ;
    boolean quickReservationExists(String username, LocalDateTime startDate, LocalDateTime endDate);
    boolean futureQuickReservationsExist(LocalDateTime currentDate,Long id);
    Set<QuickReservationAdventure> getPastReservations(String username);
    Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username);
    double sumProfitOfPricesCalucatedByHours(List<QuickReservationAdventure> reservations, LocalDateTime start, LocalDateTime end);
    void  save(QuickReservationAdventure quickReservationAdventure);
    QuickReservationAdventure findById(Long id);
    Set<QuickReservationAdventure> getAvailableReservations();
    boolean makeQuickReservation(QuickReservationAdventureDto quickReservationAdventureDto);
    boolean fishingInstructorNotFree(String instructorUsername, LocalDateTime startDate, LocalDateTime endDate);
    Set<QuickReservationAdventure> getUpcomingClientQuickReservations(String clientUsername);
    Set<QuickReservationAdventure> getClientQuickReservationsHistory(String clientUsername);
    List<QuickReservationAdventure> findAllQucikReservationsForAdminProfit(LocalDateTime start, LocalDateTime end);
    double sumProfitOfPricesCalculatedByHoursForAdmin(List<QuickReservationAdventure> reservations, LocalDateTime start, LocalDateTime end);
    boolean checkIfOwnerHasFutureReservations(String username);
    boolean checkIfClientHasFutureReservations(Long userId);
    Integer countReservationsByAdventureInPeriod(LocalDateTime start, LocalDateTime end, Long id);
    List<QuickReservationAdventure> findReservationsByAdventureToSumProfit(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
    List<QuickReservationAdventure> findReservationsByOwnerToSumProfit(String username, LocalDateTime localDateTime, LocalDateTime localDateTime1);
    boolean checkIfReservationIsEvaluated(Long reservationId);
    void markThatReservationIsEvaluated(Long reservationId);
    boolean instructorHasTakenReservationInPeriod(String instructorUsername, LocalDateTime startDate, LocalDateTime endDate);
}
