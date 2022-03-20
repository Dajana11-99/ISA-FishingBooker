package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableBoatPeriod;
import java.util.Set;

public interface AvailableBoatPeriodRepository extends JpaRepository<AvailableBoatPeriod,Long> {
    @Query(value="SELECT * FROM available_period where boat_id=:boat_id",nativeQuery = true)
    Set<AvailableBoatPeriod> findByBoatId(@Param("boat_id")Long boatId);
}
