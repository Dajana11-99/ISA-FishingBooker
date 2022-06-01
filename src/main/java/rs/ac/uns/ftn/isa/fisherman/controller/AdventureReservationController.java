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
import rs.ac.uns.ftn.isa.fisherman.service.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/reservationAdventure", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureReservationController {
    private static final String SUCCESS ="Success";
 @Autowired
 private AdventureReservationService adventureReservationService;
 @Autowired
 private FishingInstructorService fishingInstructorService;
 @Autowired
 private InstructorReservationReportService instructorReservationReportService;
 @Autowired
 private PenaltyService penaltyService;
 @Autowired
 private AdventureReservationCancellationService adventureReservationCancellationService;
 private final AdventureReservationMapper adventureReservationMapper= new AdventureReservationMapper();
 private final AdventureMapper adventureMapper = new AdventureMapper();

    @PostMapping("/instructorCreates")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> instructorCreates (@RequestBody AdventureReservationDto adventureReservationDto) {
        FishingInstructor fishingInstructor= fishingInstructorService.findByUsername(adventureReservationDto.getAdventureDto().getFishingInstructorUsername());
        AdventureReservation adventureReservation = adventureReservationMapper.adventureReservationDtoToAdventureReservation(adventureReservationDto,fishingInstructor);
        try {
            if (adventureReservationService.instructorCreates(adventureReservation, adventureReservationDto.getClientUsername()))
                return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            else
                return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Someone already made reservation or owner edited adventure. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value= "/getByInstructorUsername/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<AdventureReservationDto>> getPresentByInstructorId(@PathVariable("username")String username) {
        Set<AdventureReservationDto> adventureReservations= new HashSet<>();
        for(AdventureReservation adventureReservation: adventureReservationService.getPresentByInstructorId(username)){
            adventureReservations.add(adventureReservationMapper.adventureReservationToAdventureReservationDto(adventureReservation));
        }
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
        if(!ownersReportDto.isSuccess())
            penaltyService.addPenalty(ownersReportDto.getClientUsername());
        AdventureReservation reservation= adventureReservationService.findById(reservationId);
        InstructorReservationReport reservationReport=new InstructorReservationReport(ownersReportDto.getId(),
                ownersReportDto.isBadComment(),ownersReportDto.getComment(),ownersReportDto.getOwnersUsername(),
                ownersReportDto.getClientUsername(),ownersReportDto.isApproved(),reservation);
        instructorReservationReportService.save(reservationReport);
        reservation.setSuccessfull(ownersReportDto.isSuccess());
        reservation.setOwnerWroteAReport(true);
        adventureReservationService.save(reservation);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/getAvailableAdventures")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<AdventureDto>> getAvailableAdventures (@RequestBody SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsAdventureDto) {
        Set<AdventureDto> adventuresDto= new HashSet<>();
        for(Adventure adventure:adventureReservationService.getAvailableAdventures(searchAvailablePeriodsAdventureDto)){
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
        if(adventureReservationCancellationService.clientHasCancellationWithInstructorInPeriod(adventureReservationDto.getOwnersUsername(), adventureReservationDto.getClientUsername(), adventureReservationDto.getStartDate(), adventureReservationDto.getEndDate()))
            return new ResponseEntity<>("Client has cancellation for boat in given period!", HttpStatus.BAD_REQUEST);
        try {
            if(adventureReservationService.makeReservation(adventureReservationDto))
                return new ResponseEntity<>("Success.", HttpStatus.OK);
            else
                return new ResponseEntity<>("Fishing instructor already reserved in period!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Someone already made reservatin or owner edited adventure. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value= "/getUpcomingReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<AdventureReservationDto>> getUpcomingReservations(@RequestBody UserRequestDTO userRequestDTO) {
        Set<AdventureReservationDto> adventureReservationDtos = new HashSet<>();
        for(AdventureReservation adventureReservation: adventureReservationService.getUpcomingClientReservationsByUsername(userRequestDTO.getUsername())){
            AdventureReservationDto adventureReservationDto = adventureReservationMapper.adventureReservationToAdventureReservationDto(adventureReservation);
            adventureReservationDto.getAdventureDto().setInstructorRating(adventureReservation.getFishingInstructor().getRating());
            adventureReservationDtos.add(adventureReservationDto);
        }
        return new ResponseEntity<>(adventureReservationDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getReservationsHistory")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<AdventureReservationDto>> getReservationsHistory(@RequestBody UserRequestDTO userRequestDTO) {
        Set<AdventureReservationDto> adventureReservationDtos = new HashSet<>();
        for(AdventureReservation adventureReservation: adventureReservationService.getClientReservationHistoryByUsername(userRequestDTO.getUsername())){
            AdventureReservationDto adventureReservationDto = adventureReservationMapper.adventureReservationToAdventureReservationDto(adventureReservation);
            adventureReservationDto.getAdventureDto().setInstructorRating(adventureReservation.getFishingInstructor().getRating());
            adventureReservationDtos.add(adventureReservationDto);
        }
        return new ResponseEntity<>(adventureReservationDtos,HttpStatus.OK);
    }

    @PostMapping("/cancelReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> cancelReservation (@RequestBody AdventureReservationDto adventureReservationDto) {
        if(adventureReservationDto.getStartDate().minusDays(3).isBefore(LocalDateTime.now()))
            return new ResponseEntity<>("Unsuccessful cancellation. Less than 3 days left until start!", HttpStatus.BAD_REQUEST);
        if(!adventureReservationService.reservationExists(adventureReservationDto.getId()))
            return new ResponseEntity<>("Unsuccessful cancellation. Reservation doesn't exist or it is already cancelled!", HttpStatus.BAD_REQUEST);
        if(adventureReservationCancellationService.addCancellation(adventureReservationDto))
            return new ResponseEntity<>("Successful cancellation.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful cancellation.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/searchAvailableAdventures")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<AdventureDto>> searchAvailableAdventures (@RequestBody SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsAdventureDto) {
        Set<AdventureDto> adventuresDto= new HashSet<>();
        for(Adventure adventure:adventureReservationService.searchAvailableAdventures(searchAvailablePeriodsAdventureDto)){
            AdventureDto adventureDto = adventureMapper.adventureToAdventureDto(adventure);
            adventureDto.setInstructorRating(adventure.getFishingInstructor().getRating());
            adventuresDto.add(adventureDto);
        }
        return new ResponseEntity<>(adventuresDto, HttpStatus.OK);
    }
}
