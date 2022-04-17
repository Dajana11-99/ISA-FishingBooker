package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isa.fisherman.dto.AddNewEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.service.BoatEvaluationService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnerEvaluationService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatReservationService;

@RestController
@RequestMapping(value = "/boatEvaluation", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatEvaluationController {

    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private BoatEvaluationService boatEvaluationService;
    @Autowired
    private BoatOwnerEvaluationService boatOwnerEvaluationService;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/addEvaluation")
    public ResponseEntity<String> addEvaluation(@RequestBody AddNewEvaluationDto addNewEvaluationDto){
        if(!boatReservationService.checkIfReservationIsEvaluated(addNewEvaluationDto.getReservationId())){
            boatEvaluationService.addEvaluation(addNewEvaluationDto);
            boatOwnerEvaluationService.addEvaluation(addNewEvaluationDto);
            boatReservationService.markThatReservationIsEvaluated(addNewEvaluationDto.getReservationId());
            return new ResponseEntity<>("Evaluations successfully added.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Reservation already has evaluations!", HttpStatus.BAD_REQUEST);
        }
    }
}
