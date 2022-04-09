package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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

    @Query(value="SELECT * FROM cabin_reservation res where res.owners_username=:ownersUsername and (:currentDate <= end_date) ",nativeQuery = true)
    Set<CabinReservation> findReservationsByOwnerUsername(@Param("ownersUsername")String ownersUsername, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM cabin_reservation res where res.owners_username=:ownersUsername and (:currentDate > end_date) ",nativeQuery = true)
    Set<CabinReservation> getPastReservations(@Param("ownersUsername")String ownersUsername, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_reservation c where cabin_id=:cabin_id and users_id=:users_id",nativeQuery = true)
    boolean clientHasReservationInCabin(@Param("cabin_id")Long cabinId,@Param("users_id")Long usersId);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_reservation c where cabin_id in :owners_cabins and users_id=:users_id",nativeQuery = true)
    boolean clientHasReservationInOwnersCabin(@Param("owners_cabins")Set<Integer> owners_cabins, @Param("users_id")Long usersId);

    @Query(value="SELECT id FROM cabin_reservation where cabin_id=:cabin_id and (:currentDate > start_date) ",nativeQuery = true)
    Set<Integer> getCabinReservationsHistory(@Param("cabin_id")Long cabinId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="select count(cr.id) from cabin_reservation cr where cr.owners_username=:ownersUsername and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriod(@Param("start")LocalDateTime startWeek, @Param("end") LocalDateTime endWeek, @Param("ownersUsername")String ownersUsername);

    @Query(value="select * from cabin_reservation cr where cr.owners_username=:ownersUsername and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<CabinReservation> findReservationsInPeriodToSumProfit(@Param("ownersUsername")String ownersUsername, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
