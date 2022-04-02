package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinSubscriptionDto;
import rs.ac.uns.ftn.isa.fisherman.service.CabinSubscriptionService;

@RestController
@RequestMapping(value = "/cabinSubscription", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabinSubscriptionController {

    @Autowired
    CabinSubscriptionService cabinSubscriptionService;

    @PostMapping("/addSubscription")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addSubscription (@RequestBody CabinSubscriptionDto cabinSubscriptionDto) {
        cabinSubscriptionService.addSubscription(cabinSubscriptionDto.getClientUsername(), cabinSubscriptionDto.getCabinDto().getId());
        return new ResponseEntity<>("Successful subscription!", HttpStatus.OK);
    }

    @PostMapping("/removeSubscription")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> removeSubscription (@RequestBody CabinSubscriptionDto cabinSubscriptionDto) {
        cabinSubscriptionService.removeSubscription(cabinSubscriptionDto.getClientUsername(), cabinSubscriptionDto.getCabinDto().getId());
        return new ResponseEntity<>("Subscription successfully removed!", HttpStatus.OK);
    }
}
