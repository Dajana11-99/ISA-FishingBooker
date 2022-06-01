package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwnerComplaint;

public interface CabinOwnerComplaintRepository  extends JpaRepository<CabinOwnerComplaint, Long> {

    @Query(value="SELECT *  FROM complaints c where id=:id",nativeQuery = true)
    CabinOwnerComplaint getById(@Param("id") Long id);
}
