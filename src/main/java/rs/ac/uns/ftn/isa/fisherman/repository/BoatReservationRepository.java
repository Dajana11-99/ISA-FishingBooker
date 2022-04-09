package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BoatReservationRepository extends JpaRepository<BoatReservation,Long> {
    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM boat_reservation c where boat_id=:boat_id and users_id=:users_id and ((:currentDate between start_date and end_date))",nativeQuery = true)
    boolean clientHasReservation(@Param("boat_id")Long boatId, @Param("users_id")Long usersId, @Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM boat_reservation c where boat_id=:boat_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean reservationExists(@Param("boat_id")Long boatId,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

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
}
