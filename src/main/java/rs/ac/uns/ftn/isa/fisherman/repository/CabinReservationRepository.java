package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import java.time.LocalDateTime;

public interface CabinReservationRepository extends JpaRepository<CabinReservation, Long> {
    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_reservation c where cabin_id=:cabin_id and users_id=:users_id and ((:currentDate between start_date and end_date))",nativeQuery = true)
    boolean clientHasReservation(@Param("cabin_id")Long cabinId,@Param("users_id")Long usersId, @Param("currentDate") LocalDateTime currentDate);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_reservation c where cabin_id=:cabin_id and ((:startDate between start_date and end_date) or (:endDate  between start_date and end_date) or (start_date  between :startDate and :endDate) or (end_date  between :startDate and :endDate)) ",nativeQuery = true)
    boolean reservationExists(@Param("cabin_id")Long cabinId,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
