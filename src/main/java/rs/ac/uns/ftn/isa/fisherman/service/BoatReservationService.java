package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import java.time.LocalDateTime;
import java.util.Set;

public interface BoatReservationService {
    boolean ownerCreates(BoatReservation boatReservation, String clientUsername);

    Set<BoatReservation> getPresentByCabinId(Long boatId);

    boolean reservationExists(Long boatId, LocalDateTime startDate,LocalDateTime endDate);

    boolean ownerIsNotAvailableReservation(Long ownerId, LocalDateTime start, LocalDateTime end);
    Set<BoatReservation> findReservationsByOwnerId(Long id);
    boolean futureReservationsExist(LocalDateTime currentDate, Long boatId);

    Set<BoatReservation> getPastReservations(Long id);
}
