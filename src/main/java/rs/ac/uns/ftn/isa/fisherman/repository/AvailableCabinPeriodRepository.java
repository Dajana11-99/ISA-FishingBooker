package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableCabinPeriod;

import java.util.Set;

public interface AvailableCabinPeriodRepository extends JpaRepository<AvailableCabinPeriod, Long> {
    @Query(value="SELECT * FROM available_period where cabin_id=:cabin_id",nativeQuery = true)
    Set<AvailableCabinPeriod> findByCabinId(@Param("cabin_id")Long cabinId);
}
