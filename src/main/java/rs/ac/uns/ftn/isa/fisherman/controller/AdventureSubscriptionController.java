package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.AdventureSubscriptionDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.AdventureSubscriptionMapper;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureSubscription;
import rs.ac.uns.ftn.isa.fisherman.service.AdventureSubscriptionService;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/adventureSubscription", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureSubscriptionController {

    @Autowired
    AdventureSubscriptionService adventureSubscriptionService;

    private final AdventureSubscriptionMapper adventureSubscriptionMapper = new AdventureSubscriptionMapper();

    @PostMapping("/addSubscription")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addSubscription (@RequestBody AdventureSubscriptionDto adventureSubscriptionDto) {
        adventureSubscriptionService.addSubscription(adventureSubscriptionDto.getClientUsername(), adventureSubscriptionDto.getAdventureDto().getId());
        return new ResponseEntity<>("Successful subscription!", HttpStatus.OK);
    }

    @PostMapping("/removeSubscription")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> removeSubscription (@RequestBody AdventureSubscriptionDto adventureSubscriptionDto) {
        adventureSubscriptionService.removeSubscription(adventureSubscriptionDto.getClientUsername(), adventureSubscriptionDto.getAdventureDto().getId());
        return new ResponseEntity<>("Subscription successfully removed!", HttpStatus.OK);
    }

    @GetMapping("/getByClientUsername/{username:.+}/")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<AdventureSubscriptionDto>> getByClientUsername (@PathVariable("username") String username) {
        Set<AdventureSubscriptionDto> adventureSubscriptionDtos=new HashSet<>();
        for(AdventureSubscription adventureSubscription: adventureSubscriptionService.findSubscriptionsByClientUsername(username)){
            adventureSubscriptionDtos.add(adventureSubscriptionMapper.adventureSubscriptionToAdventureSubscriptionDto(adventureSubscription));
        }
        return new ResponseEntity<>(adventureSubscriptionDtos,HttpStatus.OK);
    }
}
