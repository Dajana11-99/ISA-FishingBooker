package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;

import java.util.Set;

public interface BoatRepository extends JpaRepository<Boat,Integer> {
    Boat findById(Long id);
    Boat findByName(String name);
    @Query(value = "SELECT * FROM boat WHERE users_id=:users_id",nativeQuery = true)
    Set<Boat> findByOwnersId(@Param("users_id")Long id);
    @Query(value = "SELECT * FROM boat WHERE users_id=:users_id and name=:name",nativeQuery = true)
    Boat findByNameAndOwner(@Param("name")String name, @Param("users_id")Long usersId);
}
