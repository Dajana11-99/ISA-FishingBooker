package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationBoat;

import java.time.LocalDateTime;
import java.util.Set;

public interface QuickReservationBoatService {
    boolean ownerCreates(QuickReservationBoat quickReservationBoat);
    Set<QuickReservationBoat> getByBoatId(Long cabinId);
    boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate);
    boolean ownerIsNotAvailableQuickResrvation(Long ownerId, LocalDateTime start, LocalDateTime end);

    Set<QuickReservationBoat> findReservationsByOwnerId(Long id);
    boolean futureQuickReservationsExist(LocalDateTime currentDate,Long boatId);

    Set<QuickReservationBoat> getPastReservations(Long id);
}
