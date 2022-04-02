package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationBoat;

import java.time.LocalDateTime;
import java.util.Set;

public interface QuickReservationBoatRepository extends JpaRepository<QuickReservationBoat,Long> {
    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_boat c where boat_id=:boat_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean quickReservationExists(@Param("boat_id")Long boatId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM quick_reservation_boat where boat_id=:boat_id ",nativeQuery = true)
    Set<QuickReservationBoat> getByBoatId(@Param("boat_id")Long boatId);

    @Query(value="SELECT CASE WHEN  COUNT(res) > 0 THEN true ELSE false END FROM quick_reservation_boat res join boat b on res.boat_id=b.id where b.users_id=:users_id and res.needs_captain_services=true and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean ownerIsNotAvailable(@Param("users_id")Long ownerId, @Param("startDate") LocalDateTime start, @Param("endDate") LocalDateTime end);

    @Query(value="SELECT * FROM quick_reservation_boat res join boat b on res.boat_id=b.id where b.users_id=:users_id and (:currentDate <= end_date)",nativeQuery = true)
    Set<QuickReservationBoat> findReservationsByOwnerId(@Param("users_id")Long ownerId, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_boat c where boat_id=:boat_id and (:currentDate <= end_date) ",nativeQuery = true)
    boolean futureQuickReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("boat_id") Long boatId);

    @Query(value="SELECT * FROM quick_reservation_boat res join boat b on res.boat_id=b.id where b.users_id=:users_id and (:currentDate > end_date) ",nativeQuery = true)
    Set<QuickReservationBoat> getPastReservations(@Param("users_id")Long id, @Param("currentDate")LocalDateTime currentDate);

}
