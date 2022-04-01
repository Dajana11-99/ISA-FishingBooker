package rs.ac.uns.ftn.isa.fisherman.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationCabinDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.CabinReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.mapper.QuickReservationCabinMapper;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;
import rs.ac.uns.ftn.isa.fisherman.service.QuickReservationCabinService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/quickReservationCabin", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuickReservationCabinController {
    @Autowired
    private QuickReservationCabinService quickReservationCabinService;
    private final QuickReservationCabinMapper quickReservationCabinMapper=new QuickReservationCabinMapper();
    @PostMapping("/ownerCreates")
    @PreAuthorize("hasRole('CABINOWNER')")
    public ResponseEntity<String> ownerCreates (@RequestBody QuickReservationCabinDto quickReservationCabinDto) {
        QuickReservationCabin quickReservationCabin= quickReservationCabinMapper.dtoToQuickReservation(quickReservationCabinDto);
        if(quickReservationCabinService.ownerCreates(quickReservationCabin)){
            return new ResponseEntity<>("Success.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Unsuccessfull reservation.", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value= "/getByCabinId/{cabinId}")
    public ResponseEntity<Set<QuickReservationCabinDto>> getPresentByCabinId(@PathVariable ("cabinId") Long cabinId) {
        Set<QuickReservationCabinDto> quickReservationCabinDtos= new HashSet<>();
        for(QuickReservationCabin quickReservationCabin: quickReservationCabinService.getByCabinId(cabinId))
            quickReservationCabinDtos.add(quickReservationCabinMapper.quickReservationToDto(quickReservationCabin));
        return new ResponseEntity<>(quickReservationCabinDtos,HttpStatus.OK);
    }

}
