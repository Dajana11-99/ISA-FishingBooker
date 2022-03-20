package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;

import java.util.Set;

public interface AvailableInstructorPeriodRepository extends JpaRepository<AvailableInstructorPeriod,Long> {

    @Query(value="SELECT * FROM available_period where users_id=:users_id",nativeQuery = true)
    Set<AvailableInstructorPeriod> findByInstructorId(@Param("users_id")Long usersId);
}
