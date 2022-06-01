package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.BoatOwnerComplaint;

public interface BoatOwnerComplaintRepository extends JpaRepository<BoatOwnerComplaint, Long> {
    @Query(value="SELECT *  FROM complaints c where id=:id",nativeQuery = true)
    BoatOwnerComplaint getById(@Param("id") Long id);
}
