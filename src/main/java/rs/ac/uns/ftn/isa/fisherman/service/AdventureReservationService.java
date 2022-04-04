package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;

import java.time.LocalDateTime;
import java.util.Set;

public interface AdventureReservationService {


    boolean instructorCreates(AdventureReservation adventureReservation, String clientUsername);
    Set<AdventureReservation> getPresentByInstructorId(Long cabinId);
    boolean reservationExists(Long cabinId, LocalDateTime startDate, LocalDateTime endDate);

    boolean futureReservationsExist(LocalDateTime currentDate, Long id);

    Set<AdventureReservation>  getPastReservations(Long id);

    void ownerCreatesReview(AdventureReservation reservation, boolean successfull);
}
