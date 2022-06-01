package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.dto.AddNewEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.model.BoatEvaluation;

public interface BoatEvaluationService {
    void addEvaluation(AddNewEvaluationDto addNewEvaluationDto);
    BoatEvaluation findById(Long id);

}
