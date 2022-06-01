package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.AddNewEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.model.BoatEvaluation;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationBoat;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatEvaluationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.BoatEvaluationService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatReservationService;
import rs.ac.uns.ftn.isa.fisherman.service.QuickReservationBoatService;

import java.time.LocalDateTime;

@Service
public class BoatEvaluationServiceImpl implements BoatEvaluationService {

    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private QuickReservationBoatService quickReservationBoatService;
    @Autowired
    private BoatEvaluationRepository boatEvaluationRepository;

    @Override
    public void addEvaluation(AddNewEvaluationDto addNewEvaluationDto) {
        if(addNewEvaluationDto.isQuickReservation()){
            QuickReservationBoat boatReservation = quickReservationBoatService.getById(addNewEvaluationDto.getReservationId());
            boatEvaluationRepository.save(new BoatEvaluation(null, LocalDateTime.now(), addNewEvaluationDto.getCommentForTheEntity(), addNewEvaluationDto.getGradeForTheEntity(), false, boatReservation.getClient(), boatReservation.getOwnersUsername(), boatReservation.getBoat()));
        }else{
            BoatReservation boatReservation = boatReservationService.getById(addNewEvaluationDto.getReservationId());
            boatEvaluationRepository.save(new BoatEvaluation(null, LocalDateTime.now(), addNewEvaluationDto.getCommentForTheEntity(), addNewEvaluationDto.getGradeForTheEntity(), false, boatReservation.getClient(), boatReservation.getOwnersUsername(), boatReservation.getBoat()));
        }
    }

    @Override
    public BoatEvaluation findById(Long id) {
        return boatEvaluationRepository.getById(id);
    }
}
