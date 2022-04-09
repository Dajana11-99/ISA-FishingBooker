package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.StatisticsReportDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.ReservationPointsMapper;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import rs.ac.uns.ftn.isa.fisherman.model.ReservationPoints;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/boatStatisticsReport", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatStatisticsReportController {
    @Autowired
    private ReservationPointsService reservationPointsService;
    @Autowired
    private UserService userService;
    @Autowired
    private BoatService boatService;
    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private QuickReservationBoatService quickReservationBoatService;
    @Autowired
    private DateService dateService;
    private final ReservationPointsMapper reservationPointsMapper=new ReservationPointsMapper();

    @GetMapping("/findBoatStatistics/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<StatisticsReportDto> findBoatStatistics (@PathVariable("username") String username) {
        StatisticsReportDto cabinStats=new StatisticsReportDto();
        ReservationPoints reservationPoints=reservationPointsService.get();
        cabinStats.setReservationsPointsDto(reservationPointsMapper.reservationsPointsToDto(reservationPoints));
        cabinStats.setOwnerRating(userService.findRatingByUsername(username));
        Long ownerId=  userService.findByUsername(username).getId();
        cabinStats.setAvgRating(boatService.findAvgBoatRatingByOwnerId(ownerId));
        return new ResponseEntity<>(cabinStats, HttpStatus.OK);
    }
    @GetMapping("/countQuickReservations/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<Integer>> countQuickReservations (@PathVariable("username") String username) {
        List<Integer> quickReservationCount=new ArrayList<>();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        quickReservationCount.add(quickReservationBoatService.countReservationsInPeriod(thisWeek.get(0),thisWeek.get(1),username));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        quickReservationCount.add(quickReservationBoatService.countReservationsInPeriod(thisMonth.get(0),thisMonth.get(1),username));
        List<LocalDateTime> thisYear=dateService.findYear();
        quickReservationCount.add(quickReservationBoatService.countReservationsInPeriod(thisYear.get(0),thisYear.get(1),username));
        return new ResponseEntity<>(quickReservationCount, HttpStatus.OK);
    }
    @GetMapping("/countReservations/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<Integer>> countReservations(@PathVariable("username") String username) {
        List<Integer> reservationCount=new ArrayList<>();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        reservationCount.add(boatReservationService.countReservationsInPeriod(thisWeek.get(0),thisWeek.get(1),username));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        reservationCount.add(boatReservationService.countReservationsInPeriod(thisMonth.get(0),thisMonth.get(1),username));
        List<LocalDateTime> thisYear=dateService.findYear();
        reservationCount.add(boatReservationService.countReservationsInPeriod(thisYear.get(0),thisYear.get(1),username));
        return new ResponseEntity<>(reservationCount, HttpStatus.OK);
    }
    @PostMapping("/sumProfit/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<Double>> sumProfit(@PathVariable("username") String username,@RequestBody List<LocalDateTime> dateRange) {
        List<Double> profit=new ArrayList<>();
        List<BoatReservation> reservations= boatReservationService.findReservationsToSumProfit(username,dateRange.get(0),dateRange.get(1));
        profit.add(boatReservationService.sumProfitOfPricesCalucatedByHours(reservations,dateRange.get(0),dateRange.get(1)));
        profit.add(quickReservationBoatService.findReservationsAndSumProfit(username,dateRange.get(0),dateRange.get(1)));
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
}
