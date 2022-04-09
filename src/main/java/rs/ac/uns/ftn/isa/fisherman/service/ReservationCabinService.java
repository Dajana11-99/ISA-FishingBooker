package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.dto.SearchAvailablePeriodsCabinDto;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ReservationCabinService {

    Set<Cabin> getAvailableCabins(SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto);

    boolean makeReservation(CabinReservationDto cabinReservationDto);

    boolean ownerCreates(CabinReservation cabinReservation, String clientUsername);

    Set<CabinReservation> getPresentByCabinId(Long cabinId);

    boolean reservationExists(Long cabinId, LocalDateTime startDate, LocalDateTime endDate);

    Set<CabinReservation> getUpcomingClientReservationsByUsername(String clientUsername);

    Set<CabinReservation> getClientReservationHistoryByUsername(String clientUsername);

    boolean futureReservationsExist(LocalDateTime currentDate, Long id);

    Set<CabinReservation> findReservationsByOwnerUsername(String ownerUsername);

    Set<CabinReservation> getPastReservations(String ownersUsername);

    CabinReservation getById(Long id);

    Integer countReservationsInPeriod(LocalDateTime startWeek, LocalDateTime endWeek, String ownerUsername);

    List<CabinReservation> findReservationsToSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end);

    double sumProfitOfPricesCalculatedByDays(List<CabinReservation> reservations, LocalDateTime start, LocalDateTime end);

    void save(CabinReservation reservation);

    void markThatReservationIsEvaluated(Long id);

    boolean checkIfReservationIsEvaluated(Long id);
}
