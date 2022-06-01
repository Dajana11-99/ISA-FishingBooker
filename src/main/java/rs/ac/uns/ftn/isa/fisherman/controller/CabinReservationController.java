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
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/reservationCabin", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabinReservationController {
    private static final String SUCCESS ="Success";
    @Autowired
    private CabinReservationService cabinReservationService;
    @Autowired
    private CabinOwnerService cabinOwnerService;
    @Autowired
    private CabinReservationCancellationService cabinReservationCancellationService;
    @Autowired
    private CabinOwnersReservationReportService cabinOwnersReservationReportService;
    @Autowired
    private PenaltyService penaltyService;

    private final CabinMapper cabinMapper = new CabinMapper();
    private final CabinReservationMapper cabinReservationMapper=new CabinReservationMapper();

    @PostMapping("/getAvailableCabins")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<CabinDto>> getAvailableCabins (@RequestBody SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto) {
        Set<CabinDto> cabinsDto= new HashSet<>();
        for(Cabin cabin: cabinReservationService.getAvailableCabins(searchAvailablePeriodsCabinDto)){
            cabinsDto.add(cabinMapper.cabinToCabinDto(cabin));
        }
        return new ResponseEntity<>(cabinsDto, HttpStatus.OK);
    }

    @PostMapping("/makeReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> makeReservation (@RequestBody CabinReservationDto cabinReservationDto) {
        if(penaltyService.isUserBlockedFromReservation(cabinReservationDto.getClientUsername()))
            return new ResponseEntity<>("Client banned from making reservations!", HttpStatus.BAD_REQUEST);
        if(cabinReservationCancellationService.clientHasCancellationForCabinInPeriod(cabinReservationDto.getCabinDto().getId(), cabinReservationDto.getClientUsername(), cabinReservationDto.getStartDate(), cabinReservationDto.getEndDate()))
            return new ResponseEntity<>("Client has cancellation for cabin in given period!", HttpStatus.BAD_REQUEST);
        try {
        if(cabinReservationService.makeReservation(cabinReservationDto))
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>("Cabin already reserved in period!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Someone already made reservation or owner edited cabin. Please try again..", HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/ownerCreates")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> ownerCreates (@RequestBody CabinReservationDto cabinReservationDto) throws Exception {
        CabinOwner cabinOwner = cabinOwnerService.findByUsername(cabinReservationDto.getCabinDto().getOwnerUsername());
        CabinReservation cabinReservation= cabinReservationMapper.cabinOwnerReservationDtoToCabinReservation(cabinReservationDto);
        cabinReservation.getCabin().setCabinOwner(cabinOwner);
        try {
            if(cabinReservationService.ownerCreates(cabinReservation,cabinReservationDto.getClientUsername())) {

                return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Unsuccessful reservation.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Someone already made reservation or owner edited cabin. Please try again..", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value= "/getByCabinId/{cabinId}")
    public ResponseEntity<Set<CabinReservationDto>> getPresentByCabinId(@PathVariable ("cabinId") Long cabinId) {
        Set<CabinReservationDto> cabinReservationDtos= new HashSet<>();
        for(CabinReservation cabinReservation: cabinReservationService.getPresentByCabinId(cabinId))
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getUpcomingReservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<CabinReservationDto>> getUpcomingReservations(@RequestBody UserRequestDTO userRequestDTO) {
        Set<CabinReservationDto> cabinReservationDtos= new HashSet<>();
        for(CabinReservation cabinReservation: cabinReservationService.getUpcomingClientReservationsByUsername(userRequestDTO.getUsername()))
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @PostMapping(value= "/getReservationsHistory")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<CabinReservationDto>> getReservationsHistory(@RequestBody UserRequestDTO userRequestDTO) {
        Set<CabinReservationDto> cabinReservationDtos= new HashSet<>();
        for(CabinReservation cabinReservation: cabinReservationService.getClientReservationHistoryByUsername(userRequestDTO.getUsername())){
            CabinReservationDto cabinReservationDto = cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation);
            cabinReservationDtos.add(cabinReservationDto);
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @PostMapping("/cancelReservation")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> cancelReservation (@RequestBody CabinReservationDto cabinReservationDto) {
        if(cabinReservationDto.getStartDate().minusDays(3).isBefore(LocalDateTime.now()))
            return new ResponseEntity<>("Unsuccessful cancellation. Less than 3 days left until start!", HttpStatus.BAD_REQUEST);
        if(!cabinReservationService.reservationExists(cabinReservationDto.getId()))
            return new ResponseEntity<>("Unsuccessful cancellation. Reservation doesn't exist or it is already cancelled!", HttpStatus.BAD_REQUEST);
        if(cabinReservationCancellationService.addCancellation(cabinReservationDto))
            return new ResponseEntity<>("Successful cancellation.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Unsuccessful cancellation.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getByOwnerUsername/{username:.+}/")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<Set<CabinReservationDto>> getByOwnerUsername (@PathVariable("username") String username) {

        Set<CabinReservationDto> cabinReservationDtos=new HashSet<>();
        for(CabinReservation cabinReservation: cabinReservationService.findReservationsByOwnerUsername(username)){
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }

    @GetMapping("/getPastReservations/{username:.+}/")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<Set<CabinReservationDto>> getPastReservations (@PathVariable("username") String username) {

        Set<CabinReservationDto> cabinReservationDtos=new HashSet<>();
        for(CabinReservation cabinReservation: cabinReservationService.getPastReservations(username)){
            cabinReservationDtos.add(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservation));
        }
        return new ResponseEntity<>(cabinReservationDtos,HttpStatus.OK);
    }
    @PostMapping("/ownerCreatesReview/{reservationId}")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> writeAReview (@PathVariable("reservationId") Long reservationId, @RequestBody OwnersReportDto ownersReportDto) {
        if(!ownersReportDto.isSuccess())
            penaltyService.addPenalty(ownersReportDto.getClientUsername());
        CabinReservation reservation= cabinReservationService.getById(reservationId);
        CabinOwnersReservationReport reservationReport=new CabinOwnersReservationReport(ownersReportDto.getId(),
                ownersReportDto.isBadComment(),ownersReportDto.getComment(),ownersReportDto.getOwnersUsername(),
                ownersReportDto.getClientUsername(),ownersReportDto.isApproved(),reservation);
        cabinOwnersReservationReportService.save(reservationReport);
        reservation.setSuccessfull(ownersReportDto.isSuccess());
        reservation.setOwnerWroteAReport(true);
        cabinReservationService.save(reservation);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping(value= "/getById/{id}")
    public ResponseEntity<CabinReservationDto> getById(@PathVariable ("id") Long id) {
        return new ResponseEntity<>(cabinReservationMapper.cabinReservationToCabinReservationDto(cabinReservationService.getById(id)),HttpStatus.OK);
    }

    @PostMapping("/searchAvailableCabins")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<CabinDto>> searchAvailableCabins (@RequestBody SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto) {
        Set<CabinDto> cabinsDto= new HashSet<>();
        for(Cabin cabin: cabinReservationService.searchAvailableCabins(searchAvailablePeriodsCabinDto)){
            cabinsDto.add(cabinMapper.cabinToCabinDto(cabin));
        }
        return new ResponseEntity<>(cabinsDto, HttpStatus.OK);
    }
}
