package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.StatisticsReportDto;
import rs.ac.uns.ftn.isa.fisherman.mapper.ReservationPointsMapper;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;
import rs.ac.uns.ftn.isa.fisherman.model.ReservationPoints;
import rs.ac.uns.ftn.isa.fisherman.service.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/adventureStatisticsReport", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureStatisticsReportController {
    @Autowired
    private ReservationPointsService reservationPointsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdventureService adventureService;
    @Autowired
    private AdventureReservationService adventureReservationService;
    @Autowired
    private QuickReservationAdventureService quickReservationAdventureService;
    @Autowired
    private DateService dateService;
    private final ReservationPointsMapper reservationPointsMapper=new ReservationPointsMapper();

    @GetMapping("/findAdventureStatistics/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<StatisticsReportDto> findAdventureStatistics (@PathVariable("username") String username) {
        StatisticsReportDto adventureStats=new StatisticsReportDto();
        ReservationPoints reservationPoints=reservationPointsService.get();
        adventureStats.setReservationsPointsDto(reservationPointsMapper.reservationsPointsToDto(reservationPoints));
        adventureStats.setOwnerRating(userService.findRatingByUsername(username));
        return new ResponseEntity<>(adventureStats, HttpStatus.OK);
    }
    @GetMapping("/countQuickReservations/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<Integer>> countQuickReservations (@PathVariable("username") String username) {
        List<Integer> quickReservationCount=new ArrayList<>();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        quickReservationCount.add(quickReservationAdventureService.countReservationsInPeriod(thisWeek.get(0),thisWeek.get(1),username));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        quickReservationCount.add(quickReservationAdventureService.countReservationsInPeriod(thisMonth.get(0),thisMonth.get(1),username));
        List<LocalDateTime> thisYear=dateService.findYear();
        quickReservationCount.add(quickReservationAdventureService.countReservationsInPeriod(thisYear.get(0),thisYear.get(1),username));
        return new ResponseEntity<>(quickReservationCount, HttpStatus.OK);
    }
    @GetMapping("/countReservations/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<Integer>> countReservations(@PathVariable("username") String username) {
        List<Integer> reservationCount=new ArrayList<>();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        reservationCount.add(adventureReservationService.countReservationsInPeriod(thisWeek.get(0),thisWeek.get(1),username));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        reservationCount.add(adventureReservationService.countReservationsInPeriod(thisMonth.get(0),thisMonth.get(1),username));
        List<LocalDateTime> thisYear=dateService.findYear();
        reservationCount.add(adventureReservationService.countReservationsInPeriod(thisYear.get(0),thisYear.get(1),username));
        return new ResponseEntity<>(reservationCount, HttpStatus.OK);
    }
    @PostMapping("/sumProfit/{username:.+}/")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<Double>> sumProfit(@PathVariable("username") String username,@RequestBody List<LocalDateTime> dateRange) {
        List<Double> profit=new ArrayList<>();
        profit.add(adventureReservationService.sumProfitOfPricesCalculatedByHours(adventureReservationService.findReservationsByOwnerToSumProfit(username,dateRange.get(0),dateRange.get(1)),dateRange.get(0),dateRange.get(1)));
        profit.add(quickReservationAdventureService.sumProfitOfPricesCalucatedByHours(quickReservationAdventureService.findReservationsByOwnerToSumProfit(username,dateRange.get(0),dateRange.get(1)),dateRange.get(0),dateRange.get(1)));
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/countQuickReservationsByAdventure/{username:.+}/{adventureName}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<Integer>> countQuickReservationsByAdventure (@PathVariable("username") String username,@PathVariable("adventureName") String adventureName) {
        List<Integer> quickReservationCount=new ArrayList<>();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        Long id= adventureService.findAdventureByName(adventureName,userService.findByUsername(username).getId()).getId();
        quickReservationCount.add(quickReservationAdventureService.countReservationsByAdventureInPeriod(thisWeek.get(0),thisWeek.get(1),id));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        quickReservationCount.add(quickReservationAdventureService.countReservationsByAdventureInPeriod(thisMonth.get(0),thisMonth.get(1),id));
        List<LocalDateTime> thisYear=dateService.findYear();
        quickReservationCount.add(quickReservationAdventureService.countReservationsByAdventureInPeriod(thisYear.get(0),thisYear.get(1),id));
        return new ResponseEntity<>(quickReservationCount, HttpStatus.OK);
    }
    @GetMapping("/countReservationsByAdventure/{username:.+}/{adventureName}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<Integer>> countReservationsByAdventure(@PathVariable("username") String username,@PathVariable("adventureName") String adventureName) {
        List<Integer> reservationCount=new ArrayList<>();
        List<LocalDateTime> thisWeek=dateService.findWeek();
        Long id= adventureService.findAdventureByName(adventureName,userService.findByUsername(username).getId()).getId();
        reservationCount.add(adventureReservationService.countReservationsByAdventureInPeriod(thisWeek.get(0),thisWeek.get(1),id));
        List<LocalDateTime> thisMonth=dateService.findMonth();
        reservationCount.add(adventureReservationService.countReservationsByAdventureInPeriod(thisMonth.get(0),thisMonth.get(1),id));
        List<LocalDateTime> thisYear=dateService.findYear();
        reservationCount.add(adventureReservationService.countReservationsByAdventureInPeriod(thisYear.get(0),thisYear.get(1),id));
        return new ResponseEntity<>(reservationCount, HttpStatus.OK);
    }
    @PostMapping("/sumProfitByAdventure/{username:.+}/{adventureName}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<Double>> sumProfit(@PathVariable("username") String username,@PathVariable("adventureName") String adventureName,@RequestBody List<LocalDateTime> dateRange) {
        List<Double> profit=new ArrayList<>();
        Long id= adventureService.findAdventureByName(adventureName,userService.findByUsername(username).getId()).getId();
        List<AdventureReservation> reservations=adventureReservationService.findReservationsByAdventureToSumProfit(id,dateRange.get(0),dateRange.get(1));
        List< QuickReservationAdventure > quickReservations=quickReservationAdventureService.findReservationsByAdventureToSumProfit(id,dateRange.get(0),dateRange.get(1));
        profit.add(adventureReservationService.sumProfitOfPricesCalculatedByHours(reservations,dateRange.get(0),dateRange.get(1)));
        profit.add(quickReservationAdventureService.sumProfitOfPricesCalucatedByHours(quickReservations,dateRange.get(0),dateRange.get(1)));
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumWeekProfitOfAllAdventuresForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumWeekProfit() {
        double profit=0.0;
        List<LocalDateTime> thisWeek=dateService.findWeek();
        profit+=adventureReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                adventureReservationService.findAllReservationsForAdminProfit(thisWeek.get(0),thisWeek.get(1)),thisWeek.get(0),thisWeek.get(1)
        );
        profit+=quickReservationAdventureService.sumProfitOfPricesCalculatedByHoursForAdmin(
                quickReservationAdventureService.findAllQucikReservationsForAdminProfit(thisWeek.get(0),thisWeek.get(1)),
                thisWeek.get(0),thisWeek.get(1)
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumTodaysProfitOfAllAdventuresForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumTodaysProfit() {
        double profit=0.0;
        List<LocalDateTime> today=new ArrayList<>();
        LocalDate date= LocalDate.now();
        LocalDateTime start= LocalTime.MIN.atDate(date);
        LocalDateTime end= LocalTime.MAX.atDate(date);

        profit+=adventureReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                adventureReservationService.findAllReservationsForAdminProfit(start,end),start,end
        );
        profit+=quickReservationAdventureService.sumProfitOfPricesCalculatedByHoursForAdmin(
               quickReservationAdventureService.findAllQucikReservationsForAdminProfit(start,end),
                start,end
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumMonthProfitOfAllAdventuresForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumMothProfit() {
        double profit=0.0;
        List<LocalDateTime> thisMonth=dateService.findMonth();
        profit+=adventureReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                adventureReservationService.findAllReservationsForAdminProfit(thisMonth.get(0),thisMonth.get(1)),thisMonth.get(0),thisMonth.get(1)
        );
        profit+=quickReservationAdventureService.sumProfitOfPricesCalculatedByHoursForAdmin(
                quickReservationAdventureService.findAllQucikReservationsForAdminProfit(thisMonth.get(0),thisMonth.get(1)),
                thisMonth.get(0),thisMonth.get(1)
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
    @GetMapping("/sumYearProfitOfAllAdventuresForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumYearProfit() {
        double profit=0.0;
        List<LocalDateTime> thisYear=dateService.findYear();
        profit+=adventureReservationService.sumProfitForAdminOfPricesCalculatedByHours(
                adventureReservationService.findAllReservationsForAdminProfit(thisYear.get(0),thisYear.get(1)),thisYear.get(0),thisYear.get(1)
        );
        profit+=quickReservationAdventureService.sumProfitOfPricesCalculatedByHoursForAdmin(
                quickReservationAdventureService.findAllQucikReservationsForAdminProfit(thisYear.get(0),thisYear.get(1)),
                thisYear.get(0),thisYear.get(1)
        );
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
}
