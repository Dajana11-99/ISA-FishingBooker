package rs.ac.uns.ftn.isa.fisherman.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.AvailablePeriodDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.AvailableInstructorPeriodMapper;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;
import rs.ac.uns.ftn.isa.fisherman.service.AvailableInstructorPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.FishingInstructorService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/instructorsPeriod", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailableInstructorPeriodController {
    private static final String SUCCESS ="Success";
    private static final String ALREADY_EXISTS ="Already exists.";
    @Autowired
    private AvailableInstructorPeriodService availableInstructorPeriodService;
    @Autowired
    private FishingInstructorService fishingInstructorService;


    private final AvailableInstructorPeriodMapper availableInstructorPeriodMapper = new AvailableInstructorPeriodMapper();

    @GetMapping("/getAvailablePeriod/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<Set<AvailablePeriodDto>> getAvailablePeriod (@PathVariable ("username") String username) {
        Set<AvailablePeriodDto> periods= new HashSet<>();
        for(AvailableInstructorPeriod availableInstructorPeriod: availableInstructorPeriodService.getAvailablePeriod(username))
            periods.add(availableInstructorPeriodMapper.availableInstructorPeriodToAvailablePeriodDto(availableInstructorPeriod));
        return new ResponseEntity<>(periods, HttpStatus.OK);
    }


    @PostMapping("/setAvailableInstructorPeriod")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> setAvailableInstructorPeriod(@RequestBody AvailablePeriodDto availablePeriodDto){
        FishingInstructor fishingInstructor= fishingInstructorService.findByUsername(availablePeriodDto.getUsername());
        AvailableInstructorPeriod availableInstructorPeriod= availableInstructorPeriodMapper
                .availablePeriodDtoToAvailableInstructorPeriod(availablePeriodDto,fishingInstructor);
        if(availableInstructorPeriodService.setAvailableInstructorPeriod(availableInstructorPeriod))
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>(ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/deleteAvailableInstructorsPeriod")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> deleteAvailableInstructorsPeriod (@RequestBody AvailablePeriodDto availablePeriodDto) {
        FishingInstructor fishingInstructor = fishingInstructorService.findByUsername(availablePeriodDto.getUsername());
        if(availableInstructorPeriodService.deleteAvailableBoatsPeriod
                (availableInstructorPeriodMapper.availablePeriodDtoToAvailableInstructorPeriod(availablePeriodDto,fishingInstructor)))
             return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/editAvailableInstructorsPeriod")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> editAvailableInstructorsPeriod (@RequestBody List<AvailablePeriodDto> periods) {
        FishingInstructor fishingInstructor = fishingInstructorService.findByUsername(periods.get(0).getUsername());
        if(availableInstructorPeriodService.editAvailableInstructorsPeriod
                (availableInstructorPeriodMapper.availablePeriodDtoToAvailableInstructorPeriod(periods.get(0),fishingInstructor),
                        availableInstructorPeriodMapper.availablePeriodDtoToAvailableInstructorPeriod(periods.get(1),fishingInstructor)))
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }





}
