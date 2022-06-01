package rs.ac.uns.ftn.isa.fisherman.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.OwnersReportDto;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationCabinDto;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.mapper.QuickReservationCabinMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/quickReservationCabin", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuickReservationCabinController {
    @Autowired
    private QuickReservationCabinService quickReservationCabinService;
    @Autowired
    private CabinOwnersQuickReservationReportService cabinOwnersQuickReservationReportService;
    @Autowired
    private PenaltyService penaltyService;
    @Autowired
    private CabinReservationCancellationService cabinReservationCancellationService;
    private final QuickReservationCabinMapper quickReservationCabinMapper=new QuickReservationCabinMapper();

    @PostMapping("/ownerCreates")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> ownerCreates (@RequestBody QuickReservationCabinDto quickReservationCabinDto) throws Exception {
        QuickReservationCabin quickReservationCabin= quickReservationCabinMapper.dtoToQuickReservation(quickReservationCabinDto);
        try {
            if(quickReservationCabinService.ownerCreates(quickReservationCabin)){
                return new ResponseEntity<>("Success.", HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Someone already made reservation.", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value= "/getByCabinId/{cabinId}")
    public ResponseEntity<Set<QuickReservationCabinDto>> getPresentByCabinId(@PathVariable ("cabinId") Long cabinId) {
        Set<QuickReservationCabinDto> quickReservationCabinDtos= new HashSet<>();
        for(QuickReservationCabin quickReservationCabin: quickReservationCabinService.getByCabinId(cabinId))
            quickReservationCabinDtos.add(quickReservationCabinMapper.quickReservationToDto(quickReservationCabin));
        return new ResponseEntity<>(quickReservationCabinDtos,HttpStatus.OK);
    }
    @GetMapping("/getByOwnerUsername/{username:.+}/")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<Set<QuickReservationCabinDto>> getByOwnerUsername (@PathVariable("username") String username) {
        Set<QuickReservationCabinDto> cabinReservationDtos=new HashSet<>();
        for(QuickReservationCabin quickReservationCabin: quickReservationCabinService.findReservationsByOwnerId(username)){
            cabinReservationDtos.add(quickReservationCabinMapper.quickReservationToDto(quickReservationCabin));
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }
    @GetMapping("/getPastQuickReservations/{username:.+}/")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<Set<QuickReservationCabinDto>> getPastReservations (@PathVariable("username") String username) {
        Set<QuickReservationCabinDto> cabinReservationDtos=new HashSet<>();
        for(QuickReservationCabin quickReservationCabin: quickReservationCabinService.getPastReservations(username)){
            cabinReservationDtos.add(quickReservationCabinMapper.quickReservationToDto(quickReservationCabin));
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }
    @PostMapping("/ownerCreatesReview/{reservationId}")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> writeAReview (@PathVariable("reservationId") Long reservationId, @RequestBody OwnersReportDto ownersReportDto) {
        if(!ownersReportDto.isSuccess())
            penaltyService.addPenalty(ownersReportDto.getClientUsername());
        QuickReservationCabin reservation=quickReservationCabinService.findReservationById(reservationId);
        CabinQuickReservationReport reservationReport=new CabinQuickReservationReport(ownersReportDto.getId(),
                ownersReportDto.isBadComment(),ownersReportDto.getComment(),ownersReportDto.getOwnersUsername(),
                ownersReportDto.getClientUsername(),ownersReportDto.isApproved(),reservation);
        cabinOwnersQuickReservationReportService.save(reservationReport);
        reservation.setSuccessfull(ownersReportDto.isSuccess());
        reservation.setOwnerWroteAReport(true);
        quickReservationCabinService.save(reservation);
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }
    @GetMapping("/getAvailableReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationCabinDto>> getAvailableReservations () {
        Set<QuickReservationCabinDto> cabinReservationDtos=new HashSet<>();
        for(QuickReservationCabin quickReservationCabin: quickReservationCabinService.getAvailableReservations()){
            cabinReservationDtos.add(quickReservationCabinMapper.quickReservationToDto(quickReservationCabin));
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @PostMapping("/makeQuickReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> makeQuickReservation (@RequestBody QuickReservationCabinDto quickReservationCabinDto) {
        if(penaltyService.isUserBlockedFromReservation(quickReservationCabinDto.getClientUsername()))
            return new ResponseEntity<>("Client banned from making reservations!", HttpStatus.BAD_REQUEST);
        if(cabinReservationCancellationService.clientHasCancellationForCabinInPeriod(quickReservationCabinDto.getCabinDto().getId(), quickReservationCabinDto.getClientUsername(), quickReservationCabinDto.getStartDate(), quickReservationCabinDto.getEndDate()))
            return new ResponseEntity<>("Client has cancellation with instructor in given period!", HttpStatus.BAD_REQUEST);
        if(quickReservationCabinService.makeQuickReservation(quickReservationCabinDto)) {
            return new ResponseEntity<>("Successful booking!", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unsuccessful booking! Cabin not available in given period!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value= "/getUpcomingReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationCabinDto>> getUpcomingReservations(@RequestBody UserRequestDTO userRequestDTO) {
        Set<QuickReservationCabinDto> quickReservationCabinDtos = new HashSet<>();
        for(QuickReservationCabin quickReservationCabin: quickReservationCabinService.getUpcomingClientQuickReservations(userRequestDTO.getUsername()))
            quickReservationCabinDtos.add(quickReservationCabinMapper.quickReservationToDto(quickReservationCabin));
        return new ResponseEntity<>(quickReservationCabinDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getReservationsHistory")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationCabinDto>> getReservationsHistory(@RequestBody UserRequestDTO userRequestDTO) {
        Set<QuickReservationCabinDto> quickReservationCabinDtos = new HashSet<>();
        for(QuickReservationCabin quickReservationCabin: quickReservationCabinService.getClientQuickReservationsHistory(userRequestDTO.getUsername()))
            quickReservationCabinDtos.add(quickReservationCabinMapper.quickReservationToDto(quickReservationCabin));
        return new ResponseEntity<>(quickReservationCabinDtos,HttpStatus.OK);
    }

    @PostMapping("/cancelReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> cancelReservation (@RequestBody QuickReservationCabinDto cabinReservationDto) {
        if(cabinReservationDto.getStartDate().minusDays(3).isBefore(LocalDateTime.now()))
            return new ResponseEntity<>("Unsuccessful cancellation. Less than 3 days left until start!", HttpStatus.BAD_REQUEST);
        if(!quickReservationCabinService.quickReservationExists(cabinReservationDto.getCabinDto().getId(), cabinReservationDto.getStartDate(), cabinReservationDto.getEndDate()))
            return new ResponseEntity<>("Unsuccessful cancellation. Reservation doesn't exist or it is already cancelled!", HttpStatus.BAD_REQUEST);
        if(cabinReservationCancellationService.addCancellationQuickReservation(cabinReservationDto))
            return new ResponseEntity<>("Successful cancellation.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful cancellation.", HttpStatus.BAD_REQUEST);
    }
}
