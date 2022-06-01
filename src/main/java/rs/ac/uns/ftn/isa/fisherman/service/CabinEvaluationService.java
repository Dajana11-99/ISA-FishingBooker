package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.dto.AddNewEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinEvaluation;

import java.util.Set;

public interface CabinEvaluationService {

    void addEvaluation(AddNewEvaluationDto addNewEvaluationDto);
    Set<CabinEvaluation> findByCabinId(Long cabinId);
    CabinEvaluation getById(Long id);
}
