package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cabin", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CabinController {
    
}
