package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.BoatSubscriptionDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.BoatSubscriptionMapper;
import rs.ac.uns.ftn.isa.fisherman.model.BoatSubscription;
import rs.ac.uns.ftn.isa.fisherman.service.BoatSubscriptionService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/boatSubscription", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatSubscriptionController {

    @Autowired
    BoatSubscriptionService boatSubscriptionService;

    private final BoatSubscriptionMapper boatSubscriptionMapper = new BoatSubscriptionMapper();

    @PostMapping("/addSubscription")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addSubscription (@RequestBody BoatSubscriptionDto boatSubscriptionDto) {
        boatSubscriptionService.addSubscription(boatSubscriptionDto.getClientUsername(), boatSubscriptionDto.getBoatDto().getId());
        return new ResponseEntity<>("Successful subscription!", HttpStatus.OK);
    }

    @PostMapping("/removeSubscription")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> removeSubscription (@RequestBody BoatSubscriptionDto boatSubscriptionDto) {
        boatSubscriptionService.removeSubscription(boatSubscriptionDto.getClientUsername(), boatSubscriptionDto.getBoatDto().getId());
        return new ResponseEntity<>("Subscription successfully removed!", HttpStatus.OK);
    }

    @GetMapping("/getByClientUsername/{username:.+}/")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Set<BoatSubscriptionDto>> getByClientUsername (@PathVariable("username") String username) {
        Set<BoatSubscriptionDto> boatSubscriptions=new HashSet<>();
        for(BoatSubscription boatSubscription: boatSubscriptionService.findSubscriptionsByClientUsername(username)){
            boatSubscriptions.add(boatSubscriptionMapper.boatSubscriptionToBoatSubscriptionDto(boatSubscription));
        }
        return new ResponseEntity<>(boatSubscriptions,HttpStatus.OK);
    }
}
