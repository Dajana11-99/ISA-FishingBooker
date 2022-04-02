package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.CabinEvaluation;

import java.time.LocalDateTime;

public interface CabinEvaluationRepository extends JpaRepository<CabinEvaluation, Long> {

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_evaluation c where cabin_reservation_id=:cabin_reservation_id",nativeQuery = true)
    boolean reservationHasEvaluation(@Param("cabin_reservation_id")Long cabin_reservation_id);
}
