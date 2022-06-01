package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;


public interface FishingInstructorRepository extends JpaRepository<FishingInstructor,Long> {
    @Query(value="SELECT * FROM users where username=:username",nativeQuery = true)
    FishingInstructor findByUsername(@Param("username")String username );

    @Query(value="SELECT * FROM users where id=:id",nativeQuery = true)
    FishingInstructor findByID(@Param("id")Long id);
}
