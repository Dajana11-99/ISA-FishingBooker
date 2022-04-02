package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.dto.CabinEvaluationDto;

public interface CabinEvaluationService {

    boolean addEvaluation(CabinEvaluationDto cabinEvaluationDto);
    boolean reservationHasEvaluation(Long id);
}
