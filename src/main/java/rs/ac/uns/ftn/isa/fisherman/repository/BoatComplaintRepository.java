package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.BoatComplaint;

public interface BoatComplaintRepository extends JpaRepository<BoatComplaint, Long> {
    @Query(value="SELECT *  FROM complaints c where id=:id",nativeQuery = true)
    BoatComplaint getById(@Param("id") Long id);
}
