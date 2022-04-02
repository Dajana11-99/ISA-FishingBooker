package rs.ac.uns.ftn.isa.fisherman.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.QuickReservationAdventureMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.service.FishingInstructorService;
import rs.ac.uns.ftn.isa.fisherman.service.QuickReservationAdventureService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/quickReservationAdventure", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuickReservationAdventureController {
    @Autowired
    private QuickReservationAdventureService quickReservationAdventureService;
    @Autowired
    private FishingInstructorService fishingInstructorService;
    private QuickReservationAdventureMapper quickReservationAdventureMapper = new QuickReservationAdventureMapper();
    @PostMapping("/instructorCreates")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> instructorCreates (@RequestBody QuickReservationAdventureDto quickReservationAdventureDto) {
        FishingInstructor fishingInstructor= fishingInstructorService.findByUsername(quickReservationAdventureDto.getAdventureDto().getFishingInstructorUsername());
        QuickReservationAdventure quickReservationAdventure = quickReservationAdventureMapper.dtoToQuickReservationAdventure(quickReservationAdventureDto,fishingInstructor);
        if(quickReservationAdventureService.instructorCreates(quickReservationAdventure)) {
            return new ResponseEntity<>("Success.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value= "/getByInstructorId/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<QuickReservationAdventureDto>> getByInstructorId(@PathVariable ("username")String username) {
        Long instructorId= fishingInstructorService.findByUsername(username).getId();
        Set<QuickReservationAdventureDto> reservationDtos= new HashSet<>();
        for(QuickReservationAdventure quickReservationAdventure: quickReservationAdventureService.getByInstructorId(instructorId))
            reservationDtos.add(quickReservationAdventureMapper.quickAdventureReservationToQuickAdventureReservationDto(quickReservationAdventure));
        return new ResponseEntity<>(reservationDtos,HttpStatus.OK);
    }


    @GetMapping("/getPastReservations/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<QuickReservationAdventureDto>> getPastReservations (@PathVariable("username") String username) {
        Long instructorId= fishingInstructorService.findByUsername(username).getId();
        Set<QuickReservationAdventureDto> reservationDtos=new HashSet<>();
        for(QuickReservationAdventure quickReservationAdventure: quickReservationAdventureService.getPastReservations(instructorId)){
            reservationDtos.add(quickReservationAdventureMapper.quickAdventureReservationToQuickAdventureReservationDto(quickReservationAdventure));
        }
        return new ResponseEntity<>(reservationDtos,HttpStatus.OK);
    }

}
