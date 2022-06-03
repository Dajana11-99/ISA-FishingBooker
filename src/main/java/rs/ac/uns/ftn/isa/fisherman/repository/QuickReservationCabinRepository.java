package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface QuickReservationCabinRepository extends JpaRepository<QuickReservationCabin, Long> {

    @Query(value="SELECT * from quick_reservation_cabin c where cabin_id=:cabin_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    List<QuickReservationCabin> quickReservationExists(@Param("cabin_id")Long cabinId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM quick_reservation_cabin where cabin_id=:cabin_id ",nativeQuery = true)
    Set<QuickReservationCabin> getByCabinId(@Param("cabin_id")Long cabinId);

    @Query(value="SELECT * FROM quick_reservation_cabin c where cabin_id=:cabin_id and (:currentDate <= end_date) ",nativeQuery = true)
    List<QuickReservationCabin> futureQuickReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("cabin_id") Long boatId);

    @Query(value="SELECT * FROM quick_reservation_cabin res where res.owners_username=:ownersUsername and (:currentDate <= end_date)",nativeQuery = true)
    Set<QuickReservationCabin> findReservationsByOwnerUsername(@Param("ownersUsername")String ownersUsername, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM quick_reservation_cabin res where res.owners_username=:ownersUsername and (:currentDate > end_date) ",nativeQuery = true)
    Set<QuickReservationCabin> getPastReservations(@Param("ownersUsername")String ownersUsername, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM quick_reservation_cabin where id=:id ",nativeQuery = true)
    QuickReservationCabin getById(@Param("id")Long id);

    @Query(value = "SELECT * FROM quick_reservation_cabin where successfull=true and bad_comment=true and comment!='' ", nativeQuery = true)
    Set<QuickReservationCabin> getAllReports();

    @Query(value="select count(cr.cabin_id) from quick_reservation_cabin cr  where cr.users_id!=null and cr.owners_username=:ownersUsername and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsOfThisWeek(@Param("start")LocalDateTime startWeek,@Param("end") LocalDateTime endWeek,@Param("ownersUsername")String ownersUsername);

    @Query(value="select * from quick_reservation_cabin cr where cr.users_id!=null and cr.owners_username=:ownersUsername and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<QuickReservationCabin> findReservationsInPeriodToSumProfit(@Param("ownersUsername")String ownersUsername, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="select * from quick_reservation_cabin r where :currentDate <= r.start_date and r.users_id IS NULL ",nativeQuery = true)
    Set<QuickReservationCabin> getAvailableReservations(@Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(r) > 0 THEN true ELSE false END FROM quick_reservation_cabin r where r.cabin_id=:cabin_id and r.start_date<=:endDate and r.end_date>=:startDate",nativeQuery = true)
    boolean cabinHasQuickReservationInPeriod(@Param("cabin_id")Long cabinId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM quick_reservation_cabin where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    Set<QuickReservationCabin> getUpcomingClientQuickReservations(@Param("user_id")Long userId, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM quick_reservation_cabin where users_id=:user_id and (:currentDate > start_date) ",nativeQuery = true)
    Set<QuickReservationCabin> getClientQuickReservationsHistory(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="select * from quick_reservation_cabin cr where cr.users_id != null and((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    List<QuickReservationCabin> findAllQuickReservationsForAdminProfit(@Param("start")LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="select count(cr.cabin_id) from quick_reservation_cabin cr where cr.users_id!=null and cr.cabin_id=:id and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriodByCabinId(@Param("start")LocalDateTime start,@Param("end") LocalDateTime end,@Param("id")Long id);

    @Query(value="select * from quick_reservation_cabin cr where cr.cabin_id=:id and cr.users_id!=null and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<QuickReservationCabin> findReservationsInPeriodByCabinToSumProfit(@Param("id")Long id,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM quick_reservation_cabin where owners_username=:username and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfOwnerHasFutureReservations(@Param("username")String username,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM  quick_reservation_cabin where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfClientHasFutureReservations(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(r) > 0 THEN true ELSE false END FROM quick_reservation_cabin r where r.cabin_id=:cabin_id and r.start_date<=:endDate and r.end_date>=:startDate and r.users_id IS NOT NULL",nativeQuery = true)
    boolean cabinHasTakenQuickReservationInPeriod(@Param("cabin_id")Long cabinId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
