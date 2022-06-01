package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.AddNewEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwnerEvaluation;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinOwnerEvaluationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;

@Service
public class CabinOwnerEvaluationServiceImpl implements CabinOwnerEvaluationService {

    @Autowired
    ClientService clientService;
    @Autowired
    CabinReservationRepository cabinReservationRepository;
    @Autowired
    CabinOwnerEvaluationRepository cabinOwnerEvaluationRepository;
    @Autowired
    CabinService cabinService;
    @Autowired
    CabinReservationService cabinReservationService;
    @Autowired
    QuickReservationCabinService quickReservationCabinService;

    @Override
    public void addEvaluation(AddNewEvaluationDto addNewEvaluationDto) {
        if(addNewEvaluationDto.isQuickReservation()){
            QuickReservationCabin cabinReservation = quickReservationCabinService.getById(addNewEvaluationDto.getReservationId());
            cabinOwnerEvaluationRepository.save(new CabinOwnerEvaluation(null, LocalDateTime.now(), addNewEvaluationDto.getCommentForTheEntityOwner(), addNewEvaluationDto.getGradeForTheEntityOwner(), false, clientService.findByUsername(addNewEvaluationDto.getClientUsername()), cabinReservation.getCabin().getCabinOwner().getUsername(), cabinReservation.getCabin().getCabinOwner()));
        }else{
            CabinReservation cabinReservation = cabinReservationService.getById(addNewEvaluationDto.getReservationId());
            cabinOwnerEvaluationRepository.save(new CabinOwnerEvaluation(null, LocalDateTime.now(), addNewEvaluationDto.getCommentForTheEntityOwner(), addNewEvaluationDto.getGradeForTheEntityOwner(), false, clientService.findByUsername(addNewEvaluationDto.getClientUsername()), cabinReservation.getCabin().getCabinOwner().getUsername(), cabinReservation.getCabin().getCabinOwner()));
        }
    }
}
