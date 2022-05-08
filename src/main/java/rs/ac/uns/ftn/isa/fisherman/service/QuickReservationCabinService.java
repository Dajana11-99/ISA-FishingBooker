package rs.ac.uns.ftn.isa.fisherman.service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationCabinDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface QuickReservationCabinService {
    boolean ownerCreates(QuickReservationCabin quickReservationCabin);
    Set<QuickReservationCabin> getByCabinId(Long cabinId);
    boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate);
    boolean futureQuickReservationsExist(LocalDateTime currentDate, Long id);
    Set<QuickReservationCabin> findReservationsByOwnerId(String  ownerUsername);
    QuickReservationCabin findReservationById(Long id);
    Set<QuickReservationCabin> getPastReservations(String ownerUsername);
    void ownerCreatesReview(QuickReservationCabin reservation, boolean successful);
    Set<QuickReservationCabin> getAllReports();
    Integer countReservationsInPeriod(LocalDateTime startWeek, LocalDateTime endWeek, String ownerUsername);
    Integer countReservationsInPeriodByCabinId(LocalDateTime startWeek, LocalDateTime endWeek, Long cabinId );
    List<QuickReservationCabin> findReservationsToSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end);
    double sumProfitOfPricesCalculatedByDays(List<QuickReservationCabin> reservations, LocalDateTime start, LocalDateTime end);
    void save(QuickReservationCabin reservation);
    Set<QuickReservationCabin> getAvailableReservations();
    boolean makeQuickReservation(QuickReservationCabinDto quickReservationCabinDto);
    boolean cabinHasQuickReservationInPeriod(Long cabinId, LocalDateTime startDate, LocalDateTime endDate);
    Set<QuickReservationCabin> getUpcomingClientQuickReservations(String clientUsername);
    Set<QuickReservationCabin> getClientQuickReservationsHistory(String clientUsername);
    List<QuickReservationCabin> findAllQucikReservationsForAdminProfit(LocalDateTime start, LocalDateTime end);
    double sumProfitOfPricesCalculatedByDaysForAdmin(List<QuickReservationCabin> reservations, LocalDateTime start, LocalDateTime end);
    boolean checkIfOwnerHasFutureReservations(String username);
    boolean checkIfClientHasFutureReservations(Long userId);
    List<QuickReservationCabin> findReservationsByCabinToSumProfit(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
