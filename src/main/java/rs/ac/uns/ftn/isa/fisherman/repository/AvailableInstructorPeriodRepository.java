package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AvailableInstructorPeriodRepository extends JpaRepository<AvailableInstructorPeriod,Long> {

    @Query(value = "SELECT * FROM available_period where users_id=:users_id", nativeQuery = true)
    Set<AvailableInstructorPeriod> findByInstructorId(@Param("users_id") Long usersId);

    @Query(value = "SELECT CASE WHEN  COUNT(ap) > 0 THEN true ELSE false END FROM available_period ap where users_id=:users_id and ((:start between start_date and end_date) or (:end between start_date and end_date) or (start_date between :start and :end) or (end_date between :start and :end))", nativeQuery = true)
    boolean availablePeriodAlreadyExists(@Param("users_id") Long usersId, @Param("start") LocalDateTime startDate, @Param("end") LocalDateTime endDate);

    @Query(value = "SELECT  * FROM available_period ap where users_id=:users_id and ((:start between start_date and end_date)) and ((:end between start_date and end_date))", nativeQuery = true)
    List<AvailableInstructorPeriod> instructorIsAvailable(@Param("users_id") Long id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value="SELECT CASE WHEN  COUNT(ap) > 0 THEN true ELSE false END FROM available_period ap where id=:id and ((:startDate between start_date and end_date) and (:endDate between start_date and end_date))",nativeQuery = true)
    boolean availablePeriodIncludesUnavailable(@Param("id")Long id, @Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value="SELECT * FROM available_period where users_id=:users_id and start_date=:startDate and end_date=:endDate",nativeQuery = true)
    AvailableInstructorPeriod findId(@Param("users_id")Long id, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

    @Query(value="SELECT users_id FROM available_period where start_date<=:startDate and end_date>=:endDate and users_id IS NOT NULL",nativeQuery = true)
    List<Long> getAvailableFishingInstructorsForPeriod(LocalDateTime startDate, LocalDateTime endDate);
}