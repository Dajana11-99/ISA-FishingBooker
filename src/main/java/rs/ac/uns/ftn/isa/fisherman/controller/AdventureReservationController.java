package rs.ac.uns.ftn.isa.fisherman.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.*;
import rs.ac.uns.ftn.isa.fisherman.mapper.AdventureMapper;
import rs.ac.uns.ftn.isa.fisherman.mapper.AdventureReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.service.AdventureReservationService;
import rs.ac.uns.ftn.isa.fisherman.service.FishingInstructorService;
import rs.ac.uns.ftn.isa.fisherman.service.InstructorReservationReportService;
import rs.ac.uns.ftn.isa.fisherman.service.PenaltyService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/reservationAdventure", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureReservationController {
 @Autowired
 private AdventureReservationService adventureReservationService;
 @Autowired
 private FishingInstructorService fishingInstructorService;
 @Autowired
 private InstructorReservationReportService instructorReservationReportService;
 @Autowired
 private PenaltyService penaltyService;
 private AdventureReservationMapper adventureReservationMapper= new AdventureReservationMapper();
 private final AdventureMapper adventureMapper = new AdventureMapper();

    @PostMapping("/instructorCreates")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> instructorCreates (@RequestBody AdventureReservationDto adventureReservationDto) {
        FishingInstructor fishingInstructor= fishingInstructorService.findByUsername(adventureReservationDto.getAdventureDto().getFishingInstructorUsername());
        AdventureReservation adventureReservation = adventureReservationMapper.adventureReservationDtoToAdventureReservation(adventureReservationDto,fishingInstructor);
        if(adventureReservationService.instructorCreates(adventureReservation,adventureReservationDto.getClientUsername())) {

            return new ResponseEntity<>("Success.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value= "/getByInstructorUsername/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<AdventureReservationDto>> getPresentByInstructorId(@PathVariable("username")String username) {
        Set<AdventureReservationDto> adventureReservations= new HashSet<>();
        for(AdventureReservation adventureReservation: adventureReservationService.getPresentByInstructorId(username))
        adventureReservations.add(adventureReservationMapper.adventureReservationToAdventureReservationDto(adventureReservation));
        return new ResponseEntity<>(adventureReservations,HttpStatus.OK);
    }

    @GetMapping("/getPastReservations/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<AdventureReservationDto>> getPastReservations (@PathVariable("username") String username) {
        Set<AdventureReservationDto> reservationDtos=new HashSet<>();
        for(AdventureReservation adventureReservation: adventureReservationService.getPastReservations(username)){
            reservationDtos.add(adventureReservationMapper.adventureReservationToAdventureReservationDto(adventureReservation));
        }
        return new ResponseEntity<>(reservationDtos,HttpStatus.OK);
    }


   @PostMapping("/ownerCreatesReview/{reservationId}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> writeAReview (@PathVariable("reservationId") Long reservationId, @RequestBody OwnersReportDto ownersReportDto) {
        AdventureReservation reservation= adventureReservationService.findById(reservationId);
        InstructorReservationReport reservationReport=new InstructorReservationReport(ownersReportDto.getId(),
                ownersReportDto.isBadComment(),ownersReportDto.getComment(),ownersReportDto.getOwnersUsername(),
                ownersReportDto.getClientUsername(),ownersReportDto.isApproved(),reservation);
        instructorReservationReportService.save(reservationReport);
        reservation.setSuccessfull(ownersReportDto.isSuccess());
        reservation.setOwnerWroteAReport(true);
        adventureReservationService.save(reservation);
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }

    @PostMapping("/getAvailableAdventures")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<AdventureDto>> getAvailableAdventures (@RequestBody SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsBoatDto) {
        Set<AdventureDto> adventuresDto= new HashSet<>();
        for(Adventure adventure:adventureReservationService.getAvailableAdventures(searchAvailablePeriodsBoatDto)){
            AdventureDto adventureDto = adventureMapper.adventureToAdventureDto(adventure);
            adventureDto.setInstructorRating(adventure.getFishingInstructor().getRating());
            adventuresDto.add(adventureDto);
        }
        return new ResponseEntity<>(adventuresDto, HttpStatus.OK);
    }

    @PostMapping("/makeReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> makeReservation (@RequestBody AdventureReservationDto adventureReservationDto) {
        if(penaltyService.isUserBlockedFromReservation(adventureReservationDto.getClientUsername()))
            return new ResponseEntity<>("Client banned from making reservations!", HttpStatus.BAD_REQUEST);
        // TODO: if(cabinReservationCancellationService.clientHasCancellationForCabinInPeriod(cabinReservationDto))
        //  return new ResponseEntity<>("Client has cancellation for boat in given period!", HttpStatus.BAD_REQUEST);
        if(adventureReservationService.makeReservation(adventureReservationDto))
            return new ResponseEntity<>("Success.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful reservation.", HttpStatus.BAD_REQUEST);
    }
}
