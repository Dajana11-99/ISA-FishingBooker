package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.dto.AdventureReservationDto;
import rs.ac.uns.ftn.isa.fisherman.dto.SearchAvailablePeriodsBoatAndAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AdventureReservationService {

    boolean instructorCreates(AdventureReservation adventureReservation, String clientUsername) throws Exception;
    Set<AdventureReservation> getPresentByInstructorId(String username);
    boolean reservationExists(String username, LocalDateTime startDate, LocalDateTime endDate);
    boolean futureReservationsExist(LocalDateTime currentDate, Long id);
    Set<AdventureReservation>  getPastReservations(String username);
    Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username);
    double sumProfitOfPricesCalculatedByHours(List<AdventureReservation> reservations, LocalDateTime start, LocalDateTime end);
    AdventureReservation findById(Long id);
    void save (AdventureReservation adventureReservation);
    Set<Adventure> getAvailableAdventures(SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsAdventureDto);
    boolean makeReservation(AdventureReservationDto adventureReservationDto) throws Exception;
    Set<AdventureReservation> getUpcomingClientReservationsByUsername(String username);
    Set<AdventureReservation> getClientReservationHistoryByUsername(String username);
    boolean reservationExists(Long id);
    boolean checkIfReservationIsEvaluated(Long reservationId);
    void markThatReservationIsEvaluated(Long reservationId);
    boolean fishingInstructorNotFree(String instructorUsername, LocalDateTime startDate, LocalDateTime endDate);
    List<AdventureReservation> findAllReservationsForAdminProfit(LocalDateTime start, LocalDateTime end);
    double sumProfitForAdminOfPricesCalculatedByHours(List<AdventureReservation> reservations, LocalDateTime start, LocalDateTime end);
    boolean checkIfOwnerHasFutureReservations(String username);
    boolean checkIfClientHasFutureReservations(Long userId);
    Integer countReservationsByAdventureInPeriod(LocalDateTime start, LocalDateTime end, Long id);
    List<AdventureReservation> findReservationsByAdventureToSumProfit(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
    List<AdventureReservation> findReservationsByOwnerToSumProfit(String username, LocalDateTime localDateTime, LocalDateTime localDateTime1);
    Set<Adventure> searchAvailableAdventures(SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsAdventureDto);
    boolean fishingInstructorNotFreeForQuickReservation(String instructorUsername, LocalDateTime startDate, LocalDateTime endDate);
}
