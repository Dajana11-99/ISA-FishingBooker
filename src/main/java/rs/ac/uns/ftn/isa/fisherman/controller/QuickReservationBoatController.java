package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.*;
import rs.ac.uns.ftn.isa.fisherman.mapper.QuickReservationBoatMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/quickReservationBoat", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuickReservationBoatController {
    @Autowired
    private QuickReservationBoatService quickReservationBoatService;
    @Autowired
    private BoatOwnerService boatOwnerService;
    @Autowired
    private BoatOwnersQuickReservationReportService boatOwnersQuickReservationReportService;
    @Autowired
    private PenaltyService penaltyService;
    @Autowired
    private BoatReservationCancellationService boatReservationCancellationService;
    private final QuickReservationBoatMapper quickReservationBoatMapper= new QuickReservationBoatMapper();

    @PostMapping("/ownerCreates/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<String> ownerCreates (@PathVariable("username") String username,@RequestBody QuickReservationBoatDto quickReservationBoatDto){
        QuickReservationBoat quickReservationBoat= quickReservationBoatMapper.dtoToBoatQuickReservation(quickReservationBoatDto);
        BoatOwner boatOwner= boatOwnerService.findByUsername(username);
        quickReservationBoat.getBoat().setBoatOwner(boatOwner);
        try {
            if (quickReservationBoatService.ownerCreates(quickReservationBoat)) {
                return new ResponseEntity<>("Success.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Someone made reservation!", HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping(value= "/getByBoatId/{boatId}")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<Set<QuickReservationBoatDto>> getPresentByBoatId(@PathVariable ("boatId") Long boatId) {
        Set<QuickReservationBoatDto> boatReservationDtos= new HashSet<>();
        for(QuickReservationBoat quickReservationBoat: quickReservationBoatService.getByBoatId(boatId))
            boatReservationDtos.add(quickReservationBoatMapper.boatQuickReservationToDto(quickReservationBoat));
        return new ResponseEntity<>(boatReservationDtos,HttpStatus.OK);
    }
    @GetMapping("/getByOwnerUsername/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<Set<QuickReservationBoatDto>> getByOwnerUsername (@PathVariable("username") String username) {
        Set<QuickReservationBoatDto> boatReservationDtos=new HashSet<>();
        for(QuickReservationBoat quickReservationBoat: quickReservationBoatService.findReservationsByOwnerUsername(username)){
            boatReservationDtos.add(quickReservationBoatMapper.boatQuickReservationToDto(quickReservationBoat));
        }
        return new ResponseEntity<>(boatReservationDtos,HttpStatus.OK);
    }
    @GetMapping("/getPastQuickReservations/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<Set<QuickReservationBoatDto>> getPastReservations (@PathVariable("username") String username) {

        Set<QuickReservationBoatDto> boatReservationDtos=new HashSet<>();
        for(QuickReservationBoat quickReservationBoat: quickReservationBoatService.getPastReservations(username)){
            boatReservationDtos.add(quickReservationBoatMapper.boatQuickReservationToDto(quickReservationBoat));
        }
        return new ResponseEntity<>(boatReservationDtos,HttpStatus.OK);
    }
    @PostMapping("/ownerCreatesReview/{reservationId}")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<String> writeAReview (@PathVariable("reservationId") Long reservationId, @RequestBody OwnersReportDto ownersReportDto) {
        if(!ownersReportDto.isSuccess())
            penaltyService.addPenalty(ownersReportDto.getClientUsername());
        QuickReservationBoat reservation=quickReservationBoatService.getById(reservationId);
        BoatQuickReservationReport reservationReport=new BoatQuickReservationReport(ownersReportDto.getId(),
                ownersReportDto.isBadComment(),ownersReportDto.getComment(),ownersReportDto.getOwnersUsername(),
                ownersReportDto.getClientUsername(),ownersReportDto.isApproved(),reservation);
        boatOwnersQuickReservationReportService.save(reservationReport);
        reservation.setSuccessfull(ownersReportDto.isSuccess());
        reservation.setOwnerWroteAReport(true);
        quickReservationBoatService.save(reservation);
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }
    @GetMapping("/getAvailableReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationBoatDto>> getAvailableReservations () {
        Set<QuickReservationBoatDto> boatReservationDtos=new HashSet<>();
        for(QuickReservationBoat quickReservationBoat: quickReservationBoatService.getAvailableReservations()){
            boatReservationDtos.add(quickReservationBoatMapper.boatQuickReservationToDto(quickReservationBoat));
        }
        return new ResponseEntity<>(boatReservationDtos,HttpStatus.OK);
    }

    @PostMapping("/makeQuickReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> makeQuickReservation (@RequestBody QuickReservationBoatDto quickReservationBoatDto) {
        if(penaltyService.isUserBlockedFromReservation(quickReservationBoatDto.getClientUsername()))
            return new ResponseEntity<>("Client banned from making reservations!", HttpStatus.BAD_REQUEST);
        if(boatReservationCancellationService.clientHasCancellationForBoatInPeriod(quickReservationBoatDto.getBoatDto().getId(), quickReservationBoatDto.getClientUsername(), quickReservationBoatDto.getStartDate(), quickReservationBoatDto.getEndDate()))
            return new ResponseEntity<>("Client has cancellation with instructor in given period!", HttpStatus.BAD_REQUEST);
        if(quickReservationBoatService.makeQuickReservation(quickReservationBoatDto)) {
            return new ResponseEntity<>("Successful booking!", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unsuccessful booking! Boat not available in given period!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value= "/getUpcomingReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationBoatDto>> getUpcomingReservations(@RequestBody UserRequestDTO userRequestDTO) {
        Set<QuickReservationBoatDto> quickReservationBoatDtos = new HashSet<>();
        for(QuickReservationBoat quickReservationBoat: quickReservationBoatService.getUpcomingClientQuickReservations(userRequestDTO.getUsername()))
            quickReservationBoatDtos.add(quickReservationBoatMapper.boatQuickReservationToDto(quickReservationBoat));
        return new ResponseEntity<>(quickReservationBoatDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getReservationsHistory")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<QuickReservationBoatDto>> getReservationsHistory(@RequestBody UserRequestDTO userRequestDTO) {
        Set<QuickReservationBoatDto> quickReservationBoatDtos = new HashSet<>();
        for(QuickReservationBoat quickReservationBoat: quickReservationBoatService.getClientQuickReservationsHistory(userRequestDTO.getUsername()))
            quickReservationBoatDtos.add(quickReservationBoatMapper.boatQuickReservationToDto(quickReservationBoat));
        return new ResponseEntity<>(quickReservationBoatDtos,HttpStatus.OK);
    }

    @PostMapping("/cancelReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> cancelReservation (@RequestBody QuickReservationBoatDto boatReservationDto) {
        if(boatReservationDto.getStartDate().minusDays(3).isBefore(LocalDateTime.now()))
            return new ResponseEntity<>("Unsuccessful cancellation. Less than 3 days left until start!", HttpStatus.BAD_REQUEST);
        if(!quickReservationBoatService.quickReservationExists(boatReservationDto.getId(), boatReservationDto.getStartDate(), boatReservationDto.getEndDate()))
            return new ResponseEntity<>("Unsuccessful cancellation. Reservation doesn't exist or it is already cancelled!", HttpStatus.BAD_REQUEST);
        if(boatReservationCancellationService.addCancellationQuickReservation(boatReservationDto))
            return new ResponseEntity<>("Successful cancellation.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful cancellation.", HttpStatus.BAD_REQUEST);
    }
}
