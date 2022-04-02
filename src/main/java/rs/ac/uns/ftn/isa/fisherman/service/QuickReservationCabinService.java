package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;

import java.time.LocalDateTime;
import java.util.Set;

public interface QuickReservationCabinService {
    boolean ownerCreates(QuickReservationCabin quickReservationCabin);


    Set<QuickReservationCabin> getByCabinId(Long cabinId);

    boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate);

    boolean futureQuickReservationsExist(LocalDateTime currentDate, Long id);

    Set<QuickReservationCabin> findReservationsByOwnerId(Long id);

    Set<QuickReservationCabin> getPastReservations(Long id);
}
