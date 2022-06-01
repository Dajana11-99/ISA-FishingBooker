package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BoatReservationRepository extends JpaRepository<BoatReservation,Long> {
    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM boat_reservation c where boat_id=:boat_id and users_id=:users_id and ((:currentDate between start_date and end_date))",nativeQuery = true)
    boolean clientHasReservation(@Param("boat_id")Long boatId, @Param("users_id")Long usersId, @Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT * FROM boat_reservation c where boat_id=:boat_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    List<BoatReservation> reservationExists(@Param("boat_id")Long boatId,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM boat_reservation where boat_id=:boat_id and (:currentDate <= end_date) ",nativeQuery = true)
    Set<BoatReservation> getPresentByBoatId(@Param("boat_id")Long boatId, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(res) > 0 THEN true ELSE false END FROM boat_reservation res where res.owners_username=:ownersUsername and needs_captain_service=true and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean ownerIsNotAvailable(@Param("ownersUsername")String ownersUsername, @Param("startDate") LocalDateTime start, @Param("endDate") LocalDateTime end);

    @Query(value="SELECT * FROM boat_reservation res where res.owners_username=:ownersUsername and (:currentDate <= end_date) ",nativeQuery = true)
    Set<BoatReservation> findReservationsByOwnerUsername(@Param("ownersUsername")String ownersUsername, @Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM boat_reservation c where boat_id=:boat_id and (:currentDate <= end_date) ",nativeQuery = true)
    boolean futureReservationsExist(@Param("currentDate")LocalDateTime currentDate,@Param("boat_id") Long boatId);

    @Query(value="SELECT * FROM boat_reservation res where res.owners_username=:ownersUsername",nativeQuery = true)
    List<BoatReservation> getReservationsByOwnerUsername(@Param("ownersUsername")String ownersUsername);

    @Query(value="SELECT * FROM boat_reservation where id=:id",nativeQuery = true)
    BoatReservation getById(@Param("id")Long id);


    @Query(value="select count(cr.boat_id) from boat_reservation cr where cr.owners_username=:ownersUsername and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriod(@Param("start")LocalDateTime startWeek, @Param("end") LocalDateTime endWeek, @Param("ownersUsername") String ownersUsername);

    @Query(value="select * from boat_reservation cr where cr.owners_username=:ownersUsername and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<BoatReservation> findReservationsInPeriodToSumProfit(@Param("ownersUsername")  String ownersUsername, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="SELECT CASE WHEN  COUNT(res) > 0 THEN true ELSE false END FROM boat_reservation res where res.boat_id=:boat_id and res.start_date<=:endDate and res.end_date>=:startDate",nativeQuery = true)
    boolean boatReservedInPeriod(@Param("boat_id")Long boatId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM boat_reservation where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    Set<BoatReservation> getUpcomingClientReservations(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT * FROM boat_reservation where users_id=:user_id and (:currentDate > start_date) ",nativeQuery = true)
    Set<BoatReservation> getClientReservationsHistory(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM boat_reservation c where c.id=:id",nativeQuery = true)
    void deleteByReservationId(@Param("id")Long id);

    @Query(value="SELECT CASE WHEN  COUNT(b) > 0 THEN true ELSE false END FROM boat_reservation b where b.id=:id ",nativeQuery = true)
    boolean reservationExists(@Param("id") Long id);

    @Query(value="SELECT CASE WHEN  COUNT(b) > 0 THEN true ELSE false END FROM boat_reservation b where boat_id=:boat_id and users_id=:users_id",nativeQuery = true)
    boolean clientHasReservationInBoat(@Param("boat_id")Long boatId,@Param("users_id")Long usersId);

    @Query(value="SELECT CASE WHEN  COUNT(b) > 0 THEN true ELSE false END FROM boat_reservation b where boat_id in :owners_boats and users_id=:users_id",nativeQuery = true)
    boolean clientHasReservationInOwnersBoat(@Param("owners_boats")Set<Integer> ownersBoats, @Param("users_id")Long id);

    @Query(value="select * from boat_reservation cr where ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    List<BoatReservation> findAllReservationsForAdminProfit(@Param("start")LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="select count(cr.boat_id) from boat_reservation cr where cr.boat_id=:id and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end))",nativeQuery = true)
    Integer countReservationsInPeriodByBoatId(@Param("start")LocalDateTime start,@Param("end") LocalDateTime end,@Param("id")Long id);

    @Query(value="select * from boat_reservation cr where cr.boat_id=:id and ((cr.start_date between :start and :end) or (cr.end_date between :start and :end) or ((:start between cr.start_date and cr.end_date) and (:end between cr.start_date and cr.end_date)))",nativeQuery = true)
    List<BoatReservation> findReservationsInPeriodByBoatToSumProfit(@Param("id")Long id,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM boat_reservation where owners_username=:username and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfOwnerHasFutureReservations(@Param("username")String username,@Param("currentDate")LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(*) > 0 THEN true ELSE false END FROM  boat_reservation where users_id=:user_id and (:currentDate <= start_date) ",nativeQuery = true)
    boolean checkIfClientHasFutureReservations(@Param("user_id")Long userId,@Param("currentDate")LocalDateTime currentDate);


}
