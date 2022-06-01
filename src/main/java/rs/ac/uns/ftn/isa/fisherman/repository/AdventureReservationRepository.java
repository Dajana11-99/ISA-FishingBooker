package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AdventureReservationRepository extends JpaRepository<AdventureReservation,Long> {
    @Query(value="SELECT * FROM adventure_reservation c where users_id=:usersId and owners_username=:username and ((:currentDate between start_date and end_date))",nativeQuery = true)
    List<AdventureReservation>clientHasReservation(@Param("username") String username,@Param("usersId")Long usersId, @Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM adventure_reservation c where owners_username=:username and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean reservationExists(@Param("username") String username,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM adventure_reservation where owners_username=:username and (:currentDate <= end_date) ",nativeQuery = true)
    Set<AdventureReservation> getPresentByInstructorId(@Param("username") String username, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM adventure_reservation c where adventure_id=:adventure_id and (:currentDate <= end_date) ",nativeQuery = true)
    boolean futureReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("adventure_id") Long adventureId);

    @Query(value="SELECT * FROM adventure_reservation where owners_username=:username and (:currentDate > end_date) ",nativeQuery = true)
    Set<AdventureReservation> getPastReservations(@Param("username") String username, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM adventure_reservation where id=:id ",nativeQuery = true)
    AdventureReservation getById(@Param("id")Long id);

    @Query(value="select * from adventure_reservation cr where cr.owners_username=:username and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<AdventureReservation> findReservationsInPeriodToSumProfit(@Param("username") String username, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="select count(cr.instructors_id) from adventure_reservation cr where cr.owners_username=:username and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriod(@Param("start")LocalDateTime startWeek, @Param("end") LocalDateTime endWeek, @Param("username") String username);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM adventure_reservation c where c.owners_username=:username and c.start_date<=:endDate and c.end_date>=:startDate",nativeQuery = true)
    boolean instructorHasReservationInPeriod(@Param("username")String username, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM adventure_reservation where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    Set<AdventureReservation> getUpcomingClientReservations(@Param("user_id")Long userId, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM adventure_reservation where users_id=:user_id and (:currentDate > start_date) ",nativeQuery = true)
    Set<AdventureReservation> getClientReservationsHistory(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM adventure_reservation c where c.id=:id",nativeQuery = true)
    void deleteByReservationId(@Param("id")Long id);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM adventure_reservation c where c.id=:id ",nativeQuery = true)
    boolean reservationExists(@Param("id") Long id);

    @Query(value="SELECT CASE WHEN  COUNT(a) > 0 THEN true ELSE false END FROM adventure_reservation a where instructors_id=:instructors_id and users_id=:users_id",nativeQuery = true)
    boolean clientHasReservationWithInstructor(@Param("instructors_id")Long instructorsId, @Param("users_id")Long id);

    @Query(value="select * from adventure_reservation cr where ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    List<AdventureReservation> findAllReservationsForAdminProfit(@Param("start")LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="select count(cr.adventure_id) from adventure_reservation cr where cr.adventure_id=:id and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriodByAdventureId(@Param("start")LocalDateTime start,@Param("end") LocalDateTime end,@Param("id")Long id);

    @Query(value="select * from adventure_reservation cr where cr.adventure_id=:id and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<AdventureReservation> findReservationsInPeriodByAdventureToSumProfit(@Param("id")Long id,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM adventure_reservation where owners_username=:username and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfOwnerHasFutureReservations(@Param("username")String username,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM  adventure_reservation where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfClientHasFutureReservations(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);
}
