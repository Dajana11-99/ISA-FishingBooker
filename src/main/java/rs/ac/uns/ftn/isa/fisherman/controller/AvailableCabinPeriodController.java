package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.AvailablePeriodDto;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.AvailableCabinPeriodMapper;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableCabinPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;
import rs.ac.uns.ftn.isa.fisherman.service.AvailableCabinPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.CabinOwnerService;
import rs.ac.uns.ftn.isa.fisherman.service.CabinService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/cabinsPeriod", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailableCabinPeriodController {

    @Autowired
   private AvailableCabinPeriodService availableCabinPeriodService;
    @Autowired
    private CabinOwnerService cabinOwnerService;
    @Autowired
    private CabinService cabinService;

    private final AvailableCabinPeriodMapper availableCabinPeriodMapper= new AvailableCabinPeriodMapper();

    @PostMapping("/getAvailablePeriod")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<Set<AvailablePeriodDto>> getAvailablePeriod (@RequestBody CabinDto cabinDto) {
        Set<AvailablePeriodDto> availableCabinPeriods= new HashSet<>();
        for(AvailableCabinPeriod availableCabinPeriod:availableCabinPeriodService.getAvailablePeriod(cabinDto.getId())){
           availableCabinPeriods.add(availableCabinPeriodMapper.availableCabinPeriodToAvailablePeriodDto(availableCabinPeriod));
        }
        return new ResponseEntity<>(availableCabinPeriods, HttpStatus.OK);
    }

    @PostMapping("/setAvailableCabinsPeriod")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> setAvailableCabinsPeriod (@RequestBody AvailablePeriodDto availablePeriodDto) {
        CabinOwner cabinOwner= cabinOwnerService.findByUsername(availablePeriodDto.getUsername());
        Cabin cabin = cabinService.findById(availablePeriodDto.getPropertyId());
        if(availableCabinPeriodService.setAvailableCabinPeriod
                (availableCabinPeriodMapper.availablePeriodDtoToAvailableCabinPeriod(availablePeriodDto,cabinOwner,cabin)))
            return new ResponseEntity<>("Success", HttpStatus.OK);
        else
            return new ResponseEntity<>("Already exists", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/deleteAvailableCabinsPeriod")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> deleteAvailableCabinsPeriod (@RequestBody AvailablePeriodDto availablePeriodDto) {
        CabinOwner cabinOwner= cabinOwnerService.findByUsername(availablePeriodDto.getUsername());
        Cabin cabin = cabinService.findById(availablePeriodDto.getPropertyId());
        if(availableCabinPeriodService.deleteAvailableCabinsPeriod
                (availableCabinPeriodMapper.availablePeriodDtoToAvailableCabinPeriod(availablePeriodDto,cabinOwner,cabin)))
            return new ResponseEntity<>("Success", HttpStatus.OK);
        else
            return new ResponseEntity<>("Already exists", HttpStatus.BAD_REQUEST);
    }

}
