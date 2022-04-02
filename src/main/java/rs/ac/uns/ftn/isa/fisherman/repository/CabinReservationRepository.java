package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

public interface CabinReservationRepository extends JpaRepository<CabinReservation, Long> {
    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_reservation c where cabin_id=:cabin_id and users_id=:users_id and ((:currentDate between start_date and end_date))",nativeQuery = true)
    boolean clientHasReservation(@Param("cabin_id")Long cabinId,@Param("users_id")Long usersId, @Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_reservation c where cabin_id=:cabin_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean reservationExists(@Param("cabin_id")Long cabinId,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM cabin_reservation where cabin_id=:cabin_id and (:currentDate <= end_date) ",nativeQuery = true)
    Set<CabinReservation> getPresentByCabinId(@Param("cabin_id")Long cabinId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM cabin_reservation where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    Set<CabinReservation> getUpcomingClientReservations(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM cabin_reservation where users_id=:user_id and (:currentDate > start_date) ",nativeQuery = true)
    Set<CabinReservation> getClientReservationsHistory(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM cabin_reservation where id=:id",nativeQuery = true)
    CabinReservation getById(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM cabin_reservation c where c.id=:id",nativeQuery = true)
    void deleteByReservationId(@Param("id")Long id);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_reservation c where cabin_id=:cabin_id and (:currentDate <= end_date) ",nativeQuery = true)
    boolean futureReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("cabin_id") Long cabinId);

    @Query(value="SELECT * FROM cabin_reservation res join cabin b on res.cabin_id=b.id where b.users_id=:users_id and (:currentDate <= end_date) ",nativeQuery = true)
    Set<CabinReservation> findReservationsByOwnerId(@Param("users_id")Long id, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM cabin_reservation res join cabin b on res.cabin_id=b.id  where b.users_id=:users_id and (:currentDate > end_date) ",nativeQuery = true)
    Set<CabinReservation> getPastReservations(@Param("users_id")Long boatId, @Param("currentDate")LocalDateTime currentDate);

}
