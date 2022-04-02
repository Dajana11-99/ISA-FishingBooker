package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinEvaluation;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinEvaluationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.CabinEvaluationService;
import rs.ac.uns.ftn.isa.fisherman.service.ClientService;

import java.time.LocalDateTime;

@Service
public class CabinEvaluationServiceImpl implements CabinEvaluationService {

    @Autowired
    ClientService clientService;
    @Autowired
    CabinReservationRepository cabinReservationRepository;
    @Autowired
    CabinEvaluationRepository cabinEvaluationRepository;

    @Override
    public boolean addEvaluation(CabinEvaluationDto cabinEvaluationDto) {
        if(cabinEvaluationRepository.reservationHasEvaluation(cabinEvaluationDto.getCabinReservationDto().getId())){
            return false;
        }
        cabinEvaluationRepository.save(new CabinEvaluation(null, LocalDateTime.now(), cabinEvaluationDto.getComment(), cabinEvaluationDto.getGrade(), false, clientService.findByUsername(cabinEvaluationDto.getClientUsername()),cabinReservationRepository.getById(cabinEvaluationDto.getCabinReservationDto().getId())));
        return true;
    }

    @Override
    public boolean reservationHasEvaluation(Long id) {
        return cabinEvaluationRepository.reservationHasEvaluation(id);
    }
}
