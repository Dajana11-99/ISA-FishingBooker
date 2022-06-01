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
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationBoat;
import rs.ac.uns.ftn.isa.fisherman.model.ReservationPoints;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @GetMapping("/countQuickReservationsByBoat/{username:.+}/{boatName}")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<Integer>> countQuickReservationsByBoat (@PathVariable("username") String username,@PathVariable("boatName") String boatName) {
        List<Integer> quickReservationCount=new ArrayList<>();
        Long id=boatService.findByNameAndOwner(boatName,userService.findByUsername(username).getId()).getId();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        quickReservationCount.add(quickReservationBoatService.countReservationsByBoatInPeriod(thisWeek.get(0),thisWeek.get(1),id));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        quickReservationCount.add(quickReservationBoatService.countReservationsByBoatInPeriod(thisMonth.get(0),thisMonth.get(1),id));
        List<LocalDateTime> thisYear=dateService.findYear();
        quickReservationCount.add(quickReservationBoatService.countReservationsByBoatInPeriod(thisYear.get(0),thisYear.get(1),id));
        return new ResponseEntity<>(quickReservationCount, HttpStatus.OK);
    }
    @GetMapping("/countReservationsByBoat/{username:.+}/{boatName}")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<Integer>> countReservationsByBoat(@PathVariable("username") String username,@PathVariable("boatName") String boatName) {
        List<Integer> reservationCount=new ArrayList<>();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        Long id=boatService.findByNameAndOwner(boatName,userService.findByUsername(username).getId()).getId();
        reservationCount.add(boatReservationService.countReservationsByBoatInPeriod(thisWeek.get(0),thisWeek.get(1),id));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        reservationCount.add(boatReservationService.countReservationsByBoatInPeriod(thisMonth.get(0),thisMonth.get(1),id));
        List<LocalDateTime> thisYear=dateService.findYear();
        reservationCount.add(boatReservationService.countReservationsByBoatInPeriod(thisYear.get(0),thisYear.get(1),id));
        return new ResponseEntity<>(reservationCount, HttpStatus.OK);
    }
    @PostMapping("/sumProfitByBoat/{username:.+}/{boatName}")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<Double>> sumProfitByBoat(@PathVariable("username") String username,@PathVariable("boatName") String boatName,@RequestBody List<LocalDateTime> dateRange) {
        List<Double> profit=new ArrayList<>();
        Long id=boatService.findByNameAndOwner(boatName,userService.findByUsername(username).getId()).getId();
        List<BoatReservation> reservations= boatReservationService.findReservationsByBoatToSumProfit(id,dateRange.get(0),dateRange.get(1));
        List<QuickReservationBoat> quickReservationBoats=quickReservationBoatService.findReservationsToSumProfitByBoat(id,dateRange.get(0),dateRange.get(1));
        profit.add(boatReservationService.sumProfitOfPricesCalculatedByHours(reservations,dateRange.get(0),dateRange.get(1)));
        profit.add(quickReservationBoatService.sumProfitOfPricesCalculatedByHours(quickReservationBoats,dateRange.get(0),dateRange.get(1)));
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @PostMapping("/sumProfit/{username:.+}/")
    @PreAuthorize("hasRole('BOATOWNER')")
    public ResponseEntity<List<Double>> sumProfit(@PathVariable("username") String username,@RequestBody List<LocalDateTime> dateRange) {
        List<Double> profit=new ArrayList<>();
        List<BoatReservation> reservations= boatReservationService.findReservationsToSumProfit(username,dateRange.get(0),dateRange.get(1));
        List<QuickReservationBoat> quickReservationBoats=quickReservationBoatService.findReservationsToSumProfit(username,dateRange.get(0),dateRange.get(1));
        profit.add(boatReservationService.sumProfitOfPricesCalculatedByHours(reservations,dateRange.get(0),dateRange.get(1)));
        profit.add(quickReservationBoatService.sumProfitOfPricesCalculatedByHours(quickReservationBoats,dateRange.get(0),dateRange.get(1)));
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumWeekProfitOfAllBoatsForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumWeekProfit() {
        double profit=0.0;
        List<LocalDateTime> thisWeek=dateService.findWeek();
        profit+=boatReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                boatReservationService.findAllReservationsForAdminProfit(thisWeek.get(0),thisWeek.get(1)),thisWeek.get(0),thisWeek.get(1)
        );
        profit+=quickReservationBoatService.sumProfitOfPricesCalculatedByHoursForAdmin(
                quickReservationBoatService.findAllQuickReservationsForAdminProfit(thisWeek.get(0),thisWeek.get(1)),
                thisWeek.get(0),thisWeek.get(1)
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumTodaysProfitOfAllBoatsForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumTodaysProfit() {
        double profit=0.0;
        LocalDate date= LocalDate.now();
        LocalDateTime start= LocalTime.MIN.atDate(date);
        LocalDateTime end= LocalTime.MAX.atDate(date);

        profit+=boatReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                boatReservationService.findAllReservationsForAdminProfit(start,end),start,end
        );
        profit+=quickReservationBoatService.sumProfitOfPricesCalculatedByHoursForAdmin(
                quickReservationBoatService.findAllQuickReservationsForAdminProfit(start,end),
                start,end
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumMonthProfitOfAllBoatsForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumMothProfit() {
        double profit=0.0;
        List<LocalDateTime> thisMonth=dateService.findMonth();
        profit+=boatReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                boatReservationService.findAllReservationsForAdminProfit(thisMonth.get(0),thisMonth.get(1)),thisMonth.get(0),thisMonth.get(1)
        );
        profit+=quickReservationBoatService.sumProfitOfPricesCalculatedByHoursForAdmin(
                quickReservationBoatService.findAllQuickReservationsForAdminProfit(thisMonth.get(0),thisMonth.get(1)),
                thisMonth.get(0),thisMonth.get(1)
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumYearProfitOfAllBoatsForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumYearProfit() {
        double profit=0.0;
        List<LocalDateTime> thisYear=dateService.findYear();
        profit+=boatReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                boatReservationService.findAllReservationsForAdminProfit(thisYear.get(0),thisYear.get(1)),thisYear.get(0),thisYear.get(1)
        );
        profit+=quickReservationBoatService.sumProfitOfPricesCalculatedByHoursForAdmin(
                quickReservationBoatService.findAllQuickReservationsForAdminProfit(thisYear.get(0),thisYear.get(1)),
                thisYear.get(0),thisYear.get(1)
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
}
