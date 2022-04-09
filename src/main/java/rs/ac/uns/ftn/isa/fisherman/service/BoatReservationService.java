package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BoatReservationService {
    boolean ownerCreates(BoatReservation boatReservation, String clientUsername);

    Set<BoatReservation> getPresentByCabinId(Long boatId);

    boolean reservationExists(Long boatId, LocalDateTime startDate, LocalDateTime endDate);

    boolean ownerIsNotAvailableReservation(String ownerId, LocalDateTime start, LocalDateTime end);

    Set<BoatReservation> findReservationsByOwnerUsername(String username);

    boolean futureReservationsExist(LocalDateTime currentDate, Long boatId);

    List<BoatReservation> getPastReservations(String ownersUsername);

    Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String ownerUsername);

    List<BoatReservation> findReservationsToSumProfit(String ownerUsername, LocalDateTime start, LocalDateTime end);

    double sumProfitOfPricesCalucatedByHours(List<BoatReservation> reservations, LocalDateTime start, LocalDateTime end);

    BoatReservation getById(Long reservationId);

    void save(BoatReservation reservation);
}