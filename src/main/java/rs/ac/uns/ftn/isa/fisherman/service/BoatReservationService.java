package rs.ac.uns.ftn.isa.fisherman.service;

import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import rs.ac.uns.ftn.isa.fisherman.model.Client;

import java.time.LocalDateTime;
import java.util.Set;

public interface BoatReservationService {
    boolean ownerCreates(BoatReservation boatReservation, String clientUsername);

    Set<BoatReservation> getPresentByCabinId(Long boatId);

    boolean reservationExists(Long boatId, LocalDateTime startDate,LocalDateTime endDate);

}
