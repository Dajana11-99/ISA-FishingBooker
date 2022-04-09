package rs.ac.uns.ftn.isa.fisherman.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.BoatReservationDto;
import rs.ac.uns.ftn.isa.fisherman.dto.OwnersReportDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.BoatReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnerService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnersReservationReportService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatReservationService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/reservationBoat", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatReservationController {
    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private BoatOwnerService boatOwnerService;
    @Autowired
    private BoatOwnersReservationReportService boatOwnersReservationReportService;

    private final BoatReservationMapper boatReservationMapper=new BoatReservationMapper();
    @PostMapping("/ownerCreates/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<String> ownerCreates (@PathVariable("username") String username,@RequestBody BoatReservationDto boatReservationDto) {
        BoatReservation boatReservation= boatReservationMapper.boatReservationOwnerDtoToBoatReservation(boatReservationDto);
        BoatOwner boatOwner= boatOwnerService.findByUsername(username);
        boatReservation.getBoat().setBoatOwner(boatOwner);
        if(boatReservationService.ownerCreates(boatReservation,boatReservationDto.getClientUsername())) {

            return new ResponseEntity<>("Success.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value= "/getByBoatId/{boatId}")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<Set<BoatReservationDto>> getPresentByCabinId(@PathVariable ("boatId") Long boatId) {
        Set<BoatReservationDto> boatReservationDtos= new HashSet<>();
        for(BoatReservation boatReservation: boatReservationService.getPresentByCabinId(boatId))
            boatReservationDtos.add(boatReservationMapper.boatReservationToBoatReservationDto(boatReservation));
        return new ResponseEntity<>(boatReservationDtos,HttpStatus.OK);
    }

    @GetMapping("/getByOwnerUsername/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<Set<BoatReservationDto>> getByOwnerUsername (@PathVariable("username") String username) {
        Set<BoatReservationDto> boatReservationDtos=new HashSet<>();
        for(BoatReservation boatReservation: boatReservationService.findReservationsByOwnerUsername(username)){
            boatReservationDtos.add(boatReservationMapper.boatReservationToBoatReservationDto(boatReservation));
        }
        return new ResponseEntity<>(boatReservationDtos,HttpStatus.OK);
    }

    @GetMapping("/getPastReservations/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<BoatReservationDto>> getPastReservations (@PathVariable("username") String username) {
        List<BoatReservationDto> boatReservationDtos=new ArrayList<>();
        for(BoatReservation boatReservation: boatReservationService.getPastReservations(username)){
            boatReservationDtos.add(boatReservationMapper.boatReservationToBoatReservationDto(boatReservation));
        }
        return new ResponseEntity<>(boatReservationDtos,HttpStatus.OK);
    }
    @PostMapping("/ownerCreatesReview/{reservationId}")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<String> writeAReview (@PathVariable("reservationId") Long reservationId, @RequestBody OwnersReportDto ownersReportDto) {
        BoatReservation reservation=boatReservationService.getById(reservationId);
        BoatOwnersReservationReport reservationReport=new BoatOwnersReservationReport(ownersReportDto.getId(),
                ownersReportDto.isBadComment(),ownersReportDto.getComment(),ownersReportDto.getOwnersUsername(),
                ownersReportDto.getClientUsername(),ownersReportDto.isApproved(),reservation);
        boatOwnersReservationReportService.save(reservationReport);
        reservation.setSuccessfull(ownersReportDto.isSuccess());
        reservation.setOwnerWroteAReport(true);
        boatReservationService.save(reservation);
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }

}
