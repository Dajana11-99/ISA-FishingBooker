package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.*;

import rs.ac.uns.ftn.isa.fisherman.mapper.AvailableBoatPeriodMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;

import rs.ac.uns.ftn.isa.fisherman.service.AvailableBoatPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnerService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/boatsPeriod", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailableBoatPeriodController {
    private static final String SUCCESS ="Success";
    private static final String ALREADY_EXISTS ="Already exists.";
    @Autowired
    private AvailableBoatPeriodService availableBoatPeriodService;

    @Autowired
    private BoatOwnerService boatOwnerService;
    @Autowired
    private BoatService boatService;
    private final AvailableBoatPeriodMapper availableBoatPeriodMapper= new AvailableBoatPeriodMapper();


    @PostMapping("/getAvailablePeriod")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<Set<AvailablePeriodDto>> getAvailablePeriod (@RequestBody BoatDto boatDto) {
        Set<AvailablePeriodDto> availableBoatPeriods= new HashSet<>();
        for(AvailableBoatPeriod availableBoatPeriod:availableBoatPeriodService.getAvailablePeriod(boatDto.getId())){
           availableBoatPeriods.add(availableBoatPeriodMapper.availableBoatPeriodToAvailablePeriodDto(availableBoatPeriod));
        }
        return new ResponseEntity<>(availableBoatPeriods, HttpStatus.OK);
    }

    @PostMapping("/setAvailableBoatsPeriod")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<String> setAvailableBoatsPeriod (@RequestBody AvailablePeriodDto availablePeriodDto) {
        Boat boat = boatService.findById(availablePeriodDto.getPropertyId());
        AvailableBoatPeriod availableBoatPeriod = availableBoatPeriodMapper
                .availablePeriodDtoToAvailableBoatPeriod(availablePeriodDto,boat);
        if(availableBoatPeriodService.setAvailableBoatPeriod(availableBoatPeriod))
             return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>(ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/deleteAvailableBoatsPeriod")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<String> deleteAvailableBoatsPeriod (@RequestBody AvailablePeriodDto availablePeriodDto) {
        Boat boat = boatService.findById(availablePeriodDto.getPropertyId());
        if(availableBoatPeriodService.deleteAvailableBoatsPeriod
                (availableBoatPeriodMapper.availablePeriodDtoToAvailableBoatPeriod(availablePeriodDto,boat)))
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/editAvailableBoatsPeriod")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<String> editAvailableCabinsPeriod (@RequestBody List<AvailablePeriodDto> periods) {
        Boat boat = boatService.findById(periods.get(0).getPropertyId());
        if(availableBoatPeriodService.editAvailableBoatsPeriod
                (availableBoatPeriodMapper.availablePeriodDtoToAvailableBoatPeriod(periods.get(0),boat),
                        availableBoatPeriodMapper.availablePeriodDtoToAvailableBoatPeriod(periods.get(1),boat)))
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }



}
