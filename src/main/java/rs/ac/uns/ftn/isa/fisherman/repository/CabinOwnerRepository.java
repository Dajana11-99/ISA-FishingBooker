package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;

public interface CabinOwnerRepository extends JpaRepository<CabinOwner,Integer> {

    @Query(value="SELECT * FROM users where username=:username",nativeQuery = true)
    CabinOwner findByUsername(@Param("username")String username);
}
