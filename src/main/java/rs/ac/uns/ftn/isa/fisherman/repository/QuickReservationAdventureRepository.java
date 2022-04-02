package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;

import java.time.LocalDateTime;
import java.util.Set;

public interface QuickReservationAdventureRepository extends JpaRepository<QuickReservationAdventure,Long> {

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_adventure c where instructors_id=:instructors_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean quickReservationExists(@Param("instructors_id")Long instructorId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM quick_reservation_adventure where instructors_id=:instructors_id and (:currentDate <= end_date) ",nativeQuery = true)
    Set<QuickReservationAdventure> getByInstructorId(@Param("instructors_id")Long instructorId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM quick_reservation_adventure where instructors_id=:instructors_id and (:currentDate > end_date) ",nativeQuery = true)
    Set<QuickReservationAdventure> getPastReservations(@Param("instructors_id")Long instructorId, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_adventure c where adventure_id=:adventure_id and (:currentDate <= end_date) ",nativeQuery = true)
    boolean futureQuickReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("adventure_id") Long adventureId);
}
