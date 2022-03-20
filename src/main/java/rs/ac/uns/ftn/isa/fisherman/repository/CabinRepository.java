package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;

import java.util.Set;

public interface CabinRepository extends JpaRepository<Cabin,Integer> {
    Cabin findById(Long id);
    Cabin findByName(String cabin);

    @Query(value = "SELECT * FROM cabin WHERE users_id=:users_id",nativeQuery = true)
    Set<Cabin> findByOwnersId(@Param("users_id")Long id);
}
