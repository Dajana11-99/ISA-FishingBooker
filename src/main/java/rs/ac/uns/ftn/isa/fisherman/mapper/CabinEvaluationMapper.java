package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.CabinEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinEvaluation;

public class CabinEvaluationMapper {
    private final CabinMapper cabinMapper=new CabinMapper();
    public CabinEvaluationDto cabinEvaluationToDto(CabinEvaluation cabinEvaluation){
        return new CabinEvaluationDto(cabinEvaluation.getId(),null,cabinEvaluation.getDate(),cabinEvaluation.getComment(),
                cabinEvaluation.getGrade(),cabinEvaluation.isApproved(),cabinEvaluation.getClient().getUsername(),cabinEvaluation.getOwnersUsername(),
                cabinMapper.cabinToCabinDto(cabinEvaluation.getCabin()), null);
    }
}
