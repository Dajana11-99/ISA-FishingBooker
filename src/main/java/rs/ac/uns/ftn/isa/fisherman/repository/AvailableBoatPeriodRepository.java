package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableBoatOwnerPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableBoatPeriod;

import java.time.LocalDateTime;
import java.util.Set;

public interface AvailableBoatPeriodRepository extends JpaRepository<AvailableBoatPeriod,Long> {
    @Query(value="SELECT * FROM available_period where boat_id=:boat_id",nativeQuery = true)
    Set<AvailableBoatPeriod> findByBoatId(@Param("boat_id")Long boatId);

    @Query(value="SELECT CASE WHEN  COUNT(ap) > 0 THEN true ELSE false END FROM available_period ap where boat_id=:boat_id and ((:start between start_date and end_date) or (:end between start_date and end_date) or (start_date between :start and :end) or (end_date between :start and :end))",nativeQuery = true)
    boolean availablePeriodAlreadyExists(@Param("boat_id")Long id,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

}
