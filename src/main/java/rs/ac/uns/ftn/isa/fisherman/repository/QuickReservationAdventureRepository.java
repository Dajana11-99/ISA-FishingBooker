package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface QuickReservationAdventureRepository extends JpaRepository<QuickReservationAdventure,Long> {

    @Query(value = "SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_adventure c where owners_username=:username and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ", nativeQuery = true)
    boolean quickReservationExists(@Param("username") String username, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM quick_reservation_adventure where owners_username=:username and (:currentDate <= end_date) ", nativeQuery = true)
    Set<QuickReservationAdventure> getByInstructorUsername(@Param("username") String username, @Param("currentDate") LocalDateTime currentDate);

    @Query(value = "SELECT * FROM quick_reservation_adventure where owners_username=:username and (:currentDate > end_date) ", nativeQuery = true)
    Set<QuickReservationAdventure> getPastReservations(@Param("username") String username, @Param("currentDate") LocalDateTime currentDate);

    @Query(value = "SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM quick_reservation_adventure c where adventure_id=:adventure_id and (:currentDate <= end_date) ", nativeQuery = true)
    boolean futureQuickReservationsExist(@Param("currentDate") LocalDateTime currentDate, @Param("adventure_id") Long adventureId);

    @Query(value = "SELECT * FROM quick_reservation_adventure where id=:id ", nativeQuery = true)
    QuickReservationAdventure getById(@Param("id") Long id);

    @Query(value="select * from quick_reservation_adventure cr where cr.users_id!=null and cr.owners_username=:username and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<QuickReservationAdventure> findReservationsInPeriodToSumProfit(@Param("username") String username, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="select count(cr.instructors_id) from quick_reservation_adventure cr  where cr.users_id!=null and cr.owners_username=:username and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriod(@Param("start")LocalDateTime startWeek, @Param("end") LocalDateTime endWeek, @Param("username") String username);

    @Query(value="select * from quick_reservation_adventure r where :currentDate <= r.start_date and r.users_id IS NULL ",nativeQuery = true)
    Set<QuickReservationAdventure> getAvailableReservations(@Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(r) > 0 THEN true ELSE false END FROM quick_reservation_adventure r where r.owners_username=:username and r.start_date<=:endDate and r.end_date>=:startDate",nativeQuery = true)
    boolean instructorHasReservationInPeriod(@Param("username")String username, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM quick_reservation_adventure where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    Set<QuickReservationAdventure> getUpcomingClientQuickReservations(@Param("user_id")Long userId, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM quick_reservation_adventure where users_id=:user_id and (:currentDate > start_date) ",nativeQuery = true)
    Set<QuickReservationAdventure> getClientQuickReservationsHistory(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="select * from quick_reservation_adventure cr where cr.users_id!=null and cr.users_id != null and((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    List<QuickReservationAdventure> findAllQuickReservationsForAdminProfit(@Param("start")LocalDateTime start, @Param("end") LocalDateTime end);
    @Query(value="select count(cr.adventure_id) from quick_reservation_adventure cr where cr.users_id!=null and cr.adventure_id=:id and cr.users_id!=null and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriodByAdventureId(@Param("start")LocalDateTime start,@Param("end") LocalDateTime end,@Param("id")Long id);

    @Query(value="select * from quick_reservation_adventure cr where cr.adventure_id=:id and cr.users_id!=null and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<QuickReservationAdventure> findReservationsInPeriodByAdventureToSumProfit(@Param("id")Long id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM quick_reservation_adventure where owners_username=:username and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfOwnerHasFutureReservations(@Param("username")String username,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM  quick_reservation_adventure where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfClientHasFutureReservations(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(r) > 0 THEN true ELSE false END FROM quick_reservation_adventure r where r.owners_username=:username and r.start_date<=:endDate and r.end_date>=:startDate and r.users_id IS NOT NULL",nativeQuery = true)
    boolean instructorHasTakenReservationInPeriod(@Param("username")String username, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}


