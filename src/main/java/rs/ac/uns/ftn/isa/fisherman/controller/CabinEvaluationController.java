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
import rs.ac.uns.ftn.isa.fisherman.dto.CabinDto;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinEvaluationDto;
import rs.ac.uns.ftn.isa.fisherman.service.CabinEvaluationService;

@RestController
@RequestMapping(value = "/cabinEvaluation", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabinEvaluationController {

    @Autowired
    CabinEvaluationService cabinEvaluationService;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/addEvaluation")
    public ResponseEntity<String> addEvaluation(@RequestBody CabinEvaluationDto cabinEvaluationDto){
        if(cabinEvaluationService.addEvaluation(cabinEvaluationDto)){
            return new ResponseEntity<>("Evaluation successfully added.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Reservation already has evaluation!", HttpStatus.BAD_REQUEST);
        }
    }
}
