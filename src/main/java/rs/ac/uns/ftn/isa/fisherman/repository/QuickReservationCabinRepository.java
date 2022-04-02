package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;

import java.time.LocalDateTime;
import java.util.Set;

public interface QuickReservationCabinRepository extends JpaRepository<QuickReservationCabin, Long> {

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_cabin c where cabin_id=:cabin_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean quickReservationExists(@Param("cabin_id")Long cabinId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM quick_reservation_cabin where cabin_id=:cabin_id ",nativeQuery = true)
    Set<QuickReservationCabin> getByCabinId(@Param("cabin_id")Long cabinId);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_cabin c where cabin_id=:cabin_id and (:currentDate <= end_date) ",nativeQuery = true)
    boolean futureQuickReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("cabin_id") Long boatId);

    @Query(value="SELECT * FROM quick_reservation_cabin res join cabin b on res.cabin_id=b.id where b.users_id=:users_id and (:currentDate <= end_date)",nativeQuery = true)
    Set<QuickReservationCabin> findReservationsByOwnerId(@Param("users_id")Long ownerId, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM quick_reservation_cabin res join cabin b on res.cabin_id=b.id where b.users_id=:users_id and (:currentDate > end_date) ",nativeQuery = true)
    Set<QuickReservationCabin> getPastReservations(@Param("users_id")Long id, @Param("currentDate")LocalDateTime currentDate);


}
