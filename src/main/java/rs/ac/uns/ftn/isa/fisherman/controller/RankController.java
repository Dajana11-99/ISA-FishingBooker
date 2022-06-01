package rs.ac.uns.ftn.isa.fisherman.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.RankDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.RankMapper;
import rs.ac.uns.ftn.isa.fisherman.model.Rank;
import rs.ac.uns.ftn.isa.fisherman.service.RankService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/ranks", produces = MediaType.APPLICATION_JSON_VALUE)
public class RankController {

    @Autowired
    private RankService rankService;
    private final RankMapper rankMapper=new RankMapper();

   @PostMapping("/updatePoints")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updatePoints (@RequestBody List<RankDto> rankDtos) {
       for(RankDto rankDto:rankDtos )
           rankService.update(rankMapper.dtoToRank(rankDto));
       return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN') " + "|| hasRole('CLIENT')")
    public ResponseEntity<List<RankDto>> getAll () {
        List<RankDto> ranks= new ArrayList<>();
        for(Rank rank:rankService.getAll())
            ranks.add(rankMapper.rankToDto(rank));
        return new ResponseEntity<>(ranks, HttpStatus.OK);
    }
}
