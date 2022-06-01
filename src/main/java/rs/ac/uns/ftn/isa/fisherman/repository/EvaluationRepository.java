package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {

    @Query(value="SELECT * FROM evaluations where approved=false",nativeQuery = true)
    List<Evaluation> getAll();

    @Query(value="SELECT * FROM evaluations where id=:id",nativeQuery = true)
    Evaluation getById(@Param("id")Long id);

    @Query(value="SELECT * FROM evaluations where cabin_owner_id=:id",nativeQuery = true)
    List<Evaluation> findCabinOwnerEvaluations(@Param("id")Long id);

    @Query(value="SELECT * FROM evaluations where boat_owner_id=:id",nativeQuery = true)
    List<Evaluation> findBoatOwnerEvaluations(@Param("id")Long id);

    @Query(value="SELECT * FROM evaluations where fishing_instructor_id=:id",nativeQuery = true)
    List<Evaluation> findFishingInstructorEvaluations(@Param("id")Long id);

    @Query(value="SELECT * FROM evaluations where cabin_id=:id and approved=true",nativeQuery = true)
    List<Evaluation> findCabinEvaluations(@Param("id")Long id);

    @Query(value="SELECT * FROM evaluations where boat_id=:id and approved=true",nativeQuery = true)
    List<Evaluation> findBoatEvaluations(@Param("id")Long id);

    @Query(value="SELECT * FROM evaluations where fishing_instructor_id=:id and approved=true",nativeQuery = true)
    List<Evaluation> findInstructorEvaluations(Long id);

}
