package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;

import java.time.LocalDateTime;
import java.util.Set;

public interface QuickReservationAdventureService {

    boolean instructorCreates(QuickReservationAdventure adventureReservation);


   Set<QuickReservationAdventure> getByInstructorId(Long id) ;

    boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate);
    public boolean futureQuickReservationsExist(LocalDateTime currentDate,Long id);
}
