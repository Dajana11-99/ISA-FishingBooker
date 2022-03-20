package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinDto;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.mapper.AdditionalServiceMapper;
import rs.ac.uns.ftn.isa.fisherman.mapper.CabinMapper;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.service.CabinOwnerService;
import rs.ac.uns.ftn.isa.fisherman.service.CabinService;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/cabins", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabinController {

    private static final String SUCCESS ="Success";

    @Autowired
    private CabinService cabinService;
    @Autowired
    private CabinOwnerService cabinOwnerService;

    private final CabinMapper cabinMapper=new CabinMapper();
    private final AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();

    @PreAuthorize("hasRole('CABINOWNER')")
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CabinDto cabinDto){
            boolean services = false;
            Cabin cabin = cabinMapper.cabinDtoToCabin(cabinDto);
            cabin.setCabinOwner(cabinOwnerService.findByUsername(cabinDto.getOwnerUsername()));
            cabinService.save(cabin);

            if (cabinDto.getAdditionalServices() != null) {
                cabin.setAdditionalServices(additionalServiceMapper.additionalServicesDtoToAdditionalServices(cabinDto.getAdditionalServices()));
                services = true;
            }
            if (services)
                cabinService.save(cabin);
            return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CABINOWNER')")
    @PostMapping("/findCabinsByOwnersUsername")
    public ResponseEntity<Set<CabinDto>> getByOwnerId(@RequestBody UserRequestDTO owner){
        Set<CabinDto> cabins=new HashSet<>();
        for(Cabin cabin: cabinService.findByOwnersId(cabinOwnerService.findByUsername(owner.getUsername()).getId()))
            cabins.add(cabinMapper.cabinToCabinDto(cabin));
        return new ResponseEntity<>(cabins,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CABINOWNER')")
    @PostMapping("/findByName")
    public ResponseEntity<CabinDto> findByName(@RequestBody CabinDto cabinDto){
        CabinDto cabin= cabinMapper.cabinToCabinDto(cabinService.findByName(cabinDto.getName()));
        return new ResponseEntity<>(cabin,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CABINOWNER')")
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody CabinDto cabinDto){
        Cabin cabin=cabinMapper.cabinDtoToCabin(cabinDto);
        cabinService.delete(cabin.getId());
        return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CABINOWNER')")
    @PostMapping("/edit")
    public ResponseEntity<String> edit(@RequestBody CabinDto cabinDto){
        Cabin cabin=cabinMapper.cabinDtoEditToCabin(cabinDto);
        boolean deleteOldImages= cabinDto.getImages() == null;
        cabinService.edit(cabin,deleteOldImages);
        return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/getAll")
    public ResponseEntity<Set<CabinDto>> getAll(){
        Set<CabinDto> cabins=new HashSet<>();
        for(Cabin cabin: cabinService.findAll())
            cabins.add(cabinMapper.cabinToCabinDto(cabin));
        return new ResponseEntity<>(cabins,HttpStatus.OK);
    }
}
