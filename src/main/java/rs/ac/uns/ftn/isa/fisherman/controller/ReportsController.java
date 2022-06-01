package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.OwnersReportDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.OwnersReportMapper;
import rs.ac.uns.ftn.isa.fisherman.model.OwnersReport;
import rs.ac.uns.ftn.isa.fisherman.service.OwnersReportService;
import rs.ac.uns.ftn.isa.fisherman.service.PenaltyService;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportsController {
    @Autowired
    private OwnersReportService ownersReportService;
    @Autowired
    private PenaltyService penaltyService;
    private final OwnersReportMapper ownersReportMapper= new OwnersReportMapper();
    @GetMapping("/getAllReports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<OwnersReportDto>> getAllReports () {
        Set<OwnersReportDto>reports= new HashSet<>();
        for(OwnersReport ownersReport: ownersReportService.getAllUnApprovedReports())
             reports.add(ownersReportMapper.ownersReportToDto(ownersReport));
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping(value= "/sendReviewResponse")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sendReviewResponse(@RequestBody OwnersReportDto ownersReportDto) {
        if(ownersReportDto.isSuccess())
            penaltyService.addPenalty(ownersReportDto.getClientUsername());
        ownersReportService.sendReviewResponse(ownersReportDto.getClientUsername(),ownersReportDto.getOwnersUsername(),ownersReportDto.getComment());
        ownersReportService.setReviewStatus(ownersReportDto.getId());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
