package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.*;
import rs.ac.uns.ftn.isa.fisherman.mapper.CabinReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.mapper.CabinMapper;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import rs.ac.uns.ftn.isa.fisherman.service.CabinEvaluationService;
import rs.ac.uns.ftn.isa.fisherman.service.CabinOwnerService;
import rs.ac.uns.ftn.isa.fisherman.service.CabinReservationCancellationService;
import rs.ac.uns.ftn.isa.fisherman.service.ReservationCabinService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/reservationCabin", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationCabinController {

    @Autowired
    private ReservationCabinService reservationCabinService;
    @Autowired
    private CabinOwnerService cabinOwnerService;
    @Autowired
    private CabinReservationCancellationService cabinReservationCancellationService;
    @Autowired
    private CabinEvaluationService cabinEvaluationService;

    private final CabinMapper cabinMapper = new CabinMapper();
    private final CabinReservationMapper cabinReservationMapper=new CabinReservationMapper();

    @PostMapping("/getAvailableCabins")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<CabinDto>> getAvailableCabins (@RequestBody SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto) {
        Set<CabinDto> cabinsDto= new HashSet<>();
        for(Cabin cabin:reservationCabinService.getAvailableCabins(searchAvailablePeriodsCabinDto)){
            cabinsDto.add(cabinMapper.cabinToCabinDto(cabin));
        }
        return new ResponseEntity<>(cabinsDto, HttpStatus.OK);
    }

    @PostMapping("/makeReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> makeReservation (@RequestBody CabinReservationDto cabinReservationDto) {
        if(reservationCabinService.makeReservation(cabinReservationDto))
            return new ResponseEntity<>("Success.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful reservation.", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/ownerCreates")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> ownerCreates (@RequestBody CabinReservationDto cabinReservationDto) {
        CabinReservation cabinReservation= cabinReservationMapper.cabinOwnerReservationDtoToCabinReservation(cabinReservationDto);
        if(reservationCabinService.ownerCreates(cabinReservation,cabinReservationDto.getClientUsername())) {

            return new ResponseEntity<>("Success.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unsuccessful reservation.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value= "/getByCabinId/{cabinId}")
    public ResponseEntity<Set<CabinReservationDto>> getPresentByCabinId(@PathVariable ("cabinId") Long cabinId) {
        Set<CabinReservationDto> cabinReservationDtos= new HashSet<>();
        for(CabinReservation cabinReservation: reservationCabinService.getPresentByCabinId(cabinId))
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getUpcomingReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<CabinReservationDto>> getUpcomingReservations(@RequestBody UserRequestDTO userRequestDTO) {
        Set<CabinReservationDto> cabinReservationDtos= new HashSet<>();
        for(CabinReservation cabinReservation: reservationCabinService.getUpcomingClientReservationsByUsername(userRequestDTO.getUsername()))
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getReservationsHistory")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<CabinReservationDto>> getReservationsHistory(@RequestBody UserRequestDTO userRequestDTO) {
        Set<CabinReservationDto> cabinReservationDtos= new HashSet<>();
        for(CabinReservation cabinReservation: reservationCabinService.getClientReservationHistoryByUsername(userRequestDTO.getUsername())){
            CabinReservationDto cabinReservationDto = cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation);
            cabinReservationDto.setEvaluated(cabinEvaluationService.reservationHasEvaluation(cabinReservation.getId()));
            cabinReservationDtos.add(cabinReservationDto);
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @PostMapping("/cancelReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> cancelReservation (@RequestBody CabinReservationDto cabinReservationDto) {
        if(cabinReservationCancellationService.addCancellation(cabinReservationDto))
            return new ResponseEntity<>("Successful cancellation.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful cancellation.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getByOwnerUsername/{username:.+}/")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<Set<CabinReservationDto>> getByOwnerUsername (@PathVariable("username") String username) {
        CabinOwner cabinOwner= cabinOwnerService.findByUsername(username);
        Set<CabinReservationDto> cabinReservationDtos=new HashSet<>();
        for(CabinReservation cabinReservation: reservationCabinService.findReservationsByOwnerId(cabinOwner.getId())){
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @GetMapping("/getPastReservations/{username:.+}/")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<Set<CabinReservationDto>> getPastReservations (@PathVariable("username") String username) {
        CabinOwner cabinOwner= cabinOwnerService.findByUsername(username);
        Set<CabinReservationDto> cabinReservationDtos=new HashSet<>();
        for(CabinReservation cabinReservation: reservationCabinService.getPastReservations(cabinOwner.getId())){
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }
}
