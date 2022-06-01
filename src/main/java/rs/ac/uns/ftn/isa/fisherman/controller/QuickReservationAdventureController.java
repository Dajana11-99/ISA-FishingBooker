package rs.ac.uns.ftn.isa.fisherman.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.OwnersReportDto;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.mapper.QuickReservationAdventureMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/quickReservationAdventure", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuickReservationAdventureController {
    @Autowired
    private QuickReservationAdventureService quickReservationAdventureService;
    @Autowired
    private FishingInstructorService fishingInstructorService;
    @Autowired
    private InstructorQuickReportService instructorQuickReportService;
    @Autowired
    private PenaltyService penaltyService;
    @Autowired
    private AdventureReservationCancellationService adventureReservationCancellationService;
    private final QuickReservationAdventureMapper quickReservationAdventureMapper = new QuickReservationAdventureMapper();
    @PostMapping("/instructorCreates")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> instructorCreates (@RequestBody QuickReservationAdventureDto quickReservationAdventureDto) {
        FishingInstructor fishingInstructor= fishingInstructorService.findByUsername(quickReservationAdventureDto.getAdventureDto().getFishingInstructorUsername());
        QuickReservationAdventure quickReservationAdventure = quickReservationAdventureMapper.dtoToQuickReservationAdventure(quickReservationAdventureDto,fishingInstructor);
        try {
            if (quickReservationAdventureService.instructorCreates(quickReservationAdventure)) {
                return new ResponseEntity<>("Success.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Someone made reservation !", HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping(value= "/getByInstructorId/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<QuickReservationAdventureDto>> getByInstructorId(@PathVariable ("username")String username) {

        Set<QuickReservationAdventureDto> reservationDtos= new HashSet<>();
        for(QuickReservationAdventure quickReservationAdventure: quickReservationAdventureService.getByInstructorUsername(username))
            reservationDtos.add(quickReservationAdventureMapper.quickAdventureReservationToQuickAdventureReservationDto(quickReservationAdventure));
        return new ResponseEntity<>(reservationDtos,HttpStatus.OK);
    }


    @GetMapping("/getPastReservations/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<QuickReservationAdventureDto>> getPastReservations (@PathVariable("username") String username) {
        Set<QuickReservationAdventureDto> reservationDtos=new HashSet<>();
        for(QuickReservationAdventure quickReservationAdventure: quickReservationAdventureService.getPastReservations(username)){
            reservationDtos.add(quickReservationAdventureMapper.quickAdventureReservationToQuickAdventureReservationDto(quickReservationAdventure));
        }
        return new ResponseEntity<>(reservationDtos,HttpStatus.OK);
    }

     @PostMapping("/ownerCreatesReview/{reservationId}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> writeAReview (@PathVariable("reservationId") Long reservationId, @RequestBody OwnersReportDto ownersReportDto) {
         if(!ownersReportDto.isSuccess())
             penaltyService.addPenalty(ownersReportDto.getClientUsername());
        QuickReservationAdventure reservation= quickReservationAdventureService.findById(reservationId);
        InstructorQuickReport reservationReport=new InstructorQuickReport(ownersReportDto.getId(),
                ownersReportDto.isBadComment(),ownersReportDto.getComment(),ownersReportDto.getOwnersUsername(),
                ownersReportDto.getClientUsername(),ownersReportDto.isApproved(),reservation);
        instructorQuickReportService.save(reservationReport);
        reservation.setSuccessfull(ownersReportDto.isSuccess());
        reservation.setOwnerWroteAReport(true);
        quickReservationAdventureService.save(reservation);
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }

    @GetMapping("/getAvailableReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationAdventureDto>> getAvailableReservations () {
        Set<QuickReservationAdventureDto> reservationDtos=new HashSet<>();
        for(QuickReservationAdventure quickReservationAdventure: quickReservationAdventureService.getAvailableReservations()){
            reservationDtos.add(quickReservationAdventureMapper.quickAdventureReservationToQuickAdventureReservationDto(quickReservationAdventure));
        }
        return new ResponseEntity<>(reservationDtos,HttpStatus.OK);
    }

    @PostMapping("/makeQuickReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> makeQuickReservation (@RequestBody QuickReservationAdventureDto quickReservationAdventureDto) {
        if(penaltyService.isUserBlockedFromReservation(quickReservationAdventureDto.getClientUsername()))
            return new ResponseEntity<>("Client banned from making reservations!", HttpStatus.BAD_REQUEST);
        if(adventureReservationCancellationService.clientHasCancellationWithInstructorInPeriod(quickReservationAdventureDto.getOwnersUsername(), quickReservationAdventureDto.getClientUsername(), quickReservationAdventureDto.getStartDate(), quickReservationAdventureDto.getEndDate()))
            return new ResponseEntity<>("Client has cancellation with instructor in given period!", HttpStatus.BAD_REQUEST);
        if(quickReservationAdventureService.makeQuickReservation(quickReservationAdventureDto)) {
            return new ResponseEntity<>("Successful booking!", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unsuccessful booking! Fishing instructor not available in given period!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value= "/getUpcomingReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationAdventureDto>> getUpcomingReservations(@RequestBody UserRequestDTO userRequestDTO) {
        Set<QuickReservationAdventureDto> quickReservationAdventureDtos = new HashSet<>();
        for(QuickReservationAdventure quickReservationAdventure: quickReservationAdventureService.getUpcomingClientQuickReservations(userRequestDTO.getUsername())){
            QuickReservationAdventureDto quickReservationAdventureDto = quickReservationAdventureMapper.quickAdventureReservationToQuickAdventureReservationDto(quickReservationAdventure);
            quickReservationAdventureDto.getAdventureDto().setInstructorRating(quickReservationAdventure.getFishingInstructor().getRating());
            quickReservationAdventureDtos.add(quickReservationAdventureDto);
        }
        return new ResponseEntity<>(quickReservationAdventureDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getReservationsHistory")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationAdventureDto>> getReservationsHistory(@RequestBody UserRequestDTO userRequestDTO) {
        Set<QuickReservationAdventureDto> quickReservationAdventureDtos = new HashSet<>();
        for(QuickReservationAdventure quickReservationAdventure: quickReservationAdventureService.getClientQuickReservationsHistory(userRequestDTO.getUsername())){
            QuickReservationAdventureDto quickReservationAdventureDto = quickReservationAdventureMapper.quickAdventureReservationToQuickAdventureReservationDto(quickReservationAdventure);
            quickReservationAdventureDto.getAdventureDto().setInstructorRating(quickReservationAdventure.getFishingInstructor().getRating());
            quickReservationAdventureDtos.add(quickReservationAdventureDto);
        }
        return new ResponseEntity<>(quickReservationAdventureDtos,HttpStatus.OK);
    }

    @PostMapping("/cancelReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> cancelReservation (@RequestBody QuickReservationAdventureDto adventureReservationDto) {
        if(adventureReservationDto.getStartDate().minusDays(3).isBefore(LocalDateTime.now()))
            return new ResponseEntity<>("Unsuccessful cancellation. Less than 3 days left until start!", HttpStatus.BAD_REQUEST);
        if(!quickReservationAdventureService.quickReservationExists(adventureReservationDto.getOwnersUsername(), adventureReservationDto.getStartDate(), adventureReservationDto.getEndDate()))
            return new ResponseEntity<>("Unsuccessful cancellation. Reservation doesn't exist or it is already cancelled!", HttpStatus.BAD_REQUEST);
        if(adventureReservationCancellationService.addCancellationQuickReservation(adventureReservationDto))
            return new ResponseEntity<>("Successful cancellation.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful cancellation.", HttpStatus.BAD_REQUEST);
    }
}
