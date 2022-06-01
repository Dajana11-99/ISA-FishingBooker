package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.ReservationsPointsDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.ReservationPointsMapper;
import rs.ac.uns.ftn.isa.fisherman.service.ReservationPointsService;

@RestController
@RequestMapping(value = "/reservationPoints", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationsPointsController {
    @Autowired
    private ReservationPointsService reservationPointsService;
    private final ReservationPointsMapper reservationPointsMapper= new ReservationPointsMapper();

    @GetMapping("/get")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationsPointsDto> get () {
        ReservationsPointsDto reservationPoints = reservationPointsMapper.reservationsPointsToDto(reservationPointsService.get());
        return new ResponseEntity<>(reservationPoints, HttpStatus.OK);
    }
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update (@RequestBody ReservationsPointsDto reservationsPointsDto) {
        reservationPointsService.update(reservationPointsMapper.dtoToReservationPoints(reservationsPointsDto));
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
