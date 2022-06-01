package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.AddNewEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinEvaluation;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinEvaluationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class CabinEvaluationServiceImpl implements CabinEvaluationService {

    @Autowired
    ClientService clientService;
    @Autowired
    CabinReservationRepository cabinReservationRepository;
    @Autowired
    CabinEvaluationRepository cabinEvaluationRepository;
    @Autowired
    CabinService cabinService;
    @Autowired
    CabinReservationService cabinReservationService;
    @Autowired
    QuickReservationCabinService quickReservationCabinService;
    @Override
    public void addEvaluation(AddNewEvaluationDto addNewEvaluationDto) {
        if(addNewEvaluationDto.isQuickReservation()) {
            QuickReservationCabin cabinReservation = quickReservationCabinService.getById(addNewEvaluationDto.getReservationId());
            cabinEvaluationRepository.save(new CabinEvaluation(null, LocalDateTime.now(), addNewEvaluationDto.getCommentForTheEntity(), addNewEvaluationDto.getGradeForTheEntity(), false, clientService.findByUsername(addNewEvaluationDto.getClientUsername()), cabinReservation.getCabin().getCabinOwner().getUsername(), cabinReservation.getCabin()));
        } else {
            CabinReservation cabinReservation = cabinReservationService.getById(addNewEvaluationDto.getReservationId());
            cabinEvaluationRepository.save(new CabinEvaluation(null, LocalDateTime.now(), addNewEvaluationDto.getCommentForTheEntity(), addNewEvaluationDto.getGradeForTheEntity(), false, clientService.findByUsername(addNewEvaluationDto.getClientUsername()), cabinReservation.getCabin().getCabinOwner().getUsername(), cabinReservation.getCabin()));
        }
    }
    @Override
    public Set<CabinEvaluation> findByCabinId(Long cabinId) {
        return cabinEvaluationRepository.findByCabinId(cabinId);
    }

    @Override
    public CabinEvaluation getById(Long id) {
       return cabinEvaluationRepository.getById(id);
    }
}
