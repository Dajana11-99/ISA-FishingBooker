package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;

import java.time.LocalDateTime;
import java.util.Set;

public interface AdventureReservationRepository extends JpaRepository<AdventureReservation,Long> {
   @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM adventure_reservation c where users_id=:users_id and instructors_id=:instructors_id ((:currentDate between start_date and end_date))",nativeQuery = true)
    boolean clientHasReservation(@Param("instructors_id")Long instructorsId,@Param("users_id")Long usersId, @Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM adventure_reservation c where instructors_id=:instructors_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean reservationExists(@Param("instructors_id")Long usersId,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM adventure_reservation where instructors_id=:instructors_id and (:currentDate <= end_date) ",nativeQuery = true)
    Set<AdventureReservation> getPresentByInstructorId(@Param("instructors_id")Long usersId, @Param("currentDate")LocalDateTime currentDate);

   @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM adventure_reservation c where adventure_id=:adventure_id and (:currentDate <= end_date) ",nativeQuery = true)
   boolean futureReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("adventure_id") Long adventure_id);

    @Query(value="SELECT * FROM adventure_reservation where instructors_id=:instructors_id and (:currentDate > end_date) ",nativeQuery = true)
    Set<AdventureReservation> getPastReservations(@Param("instructors_id")Long instructorId, @Param("currentDate")LocalDateTime currentDate);

}
