package rs.ac.uns.ftn.isa.fisherman.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.dto.SearchAvailablePeriodsBoatAndAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationCancellationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.BoatReservationServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoatReservationServiceTest {
    @Mock
    private BoatReservationRepository boatReservationRepository;
    @Mock
    private AvailableBoatPeriodService availableBoatPeriodService;
    @Mock
    private BoatReservationCancellationRepository boatReservationCancellationRepository;
    @Mock
    private QuickReservationBoatService quickReservationBoatService;
    @Mock
    private ClientService clientService;
    @InjectMocks
    private BoatReservationServiceImpl boatReservationService;

    @Test
    public void testSumProfitByHours() {
        BoatReservation firstReservation=new BoatReservation(2L,LocalDateTime.now().plusHours(6),
                LocalDateTime.now().plusHours(7),null,new PaymentInformation(70.00,20.00,50.00),
                false,"bo@gmail.com",null,null,false);
        BoatReservation secondReservation=new BoatReservation(3L,LocalDateTime.now().plusHours(8),
                LocalDateTime.now().plusHours(9),null,new PaymentInformation(70.00,20.00,50.00),
                false,"bo@gmail.com",null,null,false);

        List<BoatReservation> reservations = Arrays.asList(firstReservation,secondReservation);
        double profit= boatReservationService.sumProfitOfPricesCalculatedByHours(reservations,LocalDateTime.now().plusHours(5),LocalDateTime.now().plusHours(10));
        assertEquals(100.00,profit,0.001);
    }
    @Test
    public void testFindPastReservationsForBoatOwnerSuccessfull() {
        BoatReservation firstReservation=new BoatReservation(1L,LocalDateTime.now().minusDays(7),LocalDateTime.now().minusDays(6),new Client(),new PaymentInformation(),false,"bo@gmail.com",new Boat(),null,false);
        BoatReservation secondReservation=new BoatReservation(2L,LocalDateTime.now().minusDays(2),LocalDateTime.now().minusDays(1),new Client(),new PaymentInformation(),false,"bo@gmail.com",new Boat(),null,false);
        BoatReservation thirdReservation=new BoatReservation(3L,LocalDateTime.now().plusDays(2),LocalDateTime.now().plusDays(3),new Client(),new PaymentInformation(),false,"bo@gmail.com",new Boat(),null,false);

        when(boatReservationRepository.findAll()).thenReturn(Arrays.asList(firstReservation,secondReservation,thirdReservation));
        when(boatReservationRepository.getReservationsByOwnerUsername("bo@gmail.com")).thenReturn(Arrays.asList(firstReservation,secondReservation,thirdReservation));

        List<BoatReservation> pastReservations= boatReservationService.getPastReservations("bo@gmail.com");
        assertThat(pastReservations).hasSize(2);
        assertThat(pastReservations).isNotNull();
    }

    @Test
    public void testFindPastReservationsForBoatOwnerUnsuccessfull() {
        BoatReservation firstReservation=new BoatReservation(1L,LocalDateTime.now().plusDays(6),LocalDateTime.now().plusDays(7),new Client(),new PaymentInformation(),false,"bo@gmail.com",new Boat(),null,false);
        BoatReservation secondReservation=new BoatReservation(2L,LocalDateTime.now().plusDays(4),LocalDateTime.now().plusDays(5),new Client(),new PaymentInformation(),false,"bo@gmail.com",new Boat(),null,false);
        BoatReservation thirdReservation=new BoatReservation(3L,LocalDateTime.now().plusDays(2),LocalDateTime.now().plusDays(3),new Client(),new PaymentInformation(),false,"bo@gmail.com",new Boat(),null,false);

        when(boatReservationRepository.findAll()).thenReturn(Arrays.asList(firstReservation,secondReservation,thirdReservation));
        when(boatReservationRepository.getReservationsByOwnerUsername("bo@gmail.com")).thenReturn(Arrays.asList(firstReservation,secondReservation,thirdReservation));

        List<BoatReservation> pastReservations= boatReservationService.getPastReservations("bo@gmail.com");
        assertThat(pastReservations).isEmpty();
    }

    @Test
    public void testGetAvailableBoats(){
        Address address= new Address(53,43,"Serbia","Novi Sad","Dunavska");
        Boat boat1 = new Boat(1L, "Boat 1", "Type 1", 10.0, "CODE1", "200 HP",
                "200KM/H", "Sonar, GPS.", address, "Opis", 5, "Rules",
                "Fishing eq", 100.0, 4.5, "FREE.");
        Boat boat2 = new Boat(2L, "Boat 2", "Type 1", 10.0, "CODE1", "200 HP",
                "200KM/H", "Sonar, GPS.", address, "Opis", 7, "Rules",
                "Fishing eq", 120.0, 4.0, "FREE.");
        Boat boat3 = new Boat(3L, "Boat 3", "Type 1", 10.0, "CODE1", "200 HP",
                "200KM/H", "Sonar, GPS.", address, "Opis", 5, "Rules",
                "Fishing eq", 150.0, 3.5, "FREE.");

        AvailableBoatPeriod availableBoatPeriod1 = new AvailableBoatPeriod(1L, LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10), boat1);
        AvailableBoatPeriod availableBoatPeriod2 = new AvailableBoatPeriod(2L, LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10), boat2);
        AvailableBoatPeriod availableBoatPeriod3 = new AvailableBoatPeriod(3L, LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10), boat3);
        Set<AvailableBoatPeriod> availableBoatPeriodSet = new HashSet<>(Arrays.asList(availableBoatPeriod1, availableBoatPeriod2, availableBoatPeriod3));
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);

        SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsBoatAndAdventureDto = new SearchAvailablePeriodsBoatAndAdventureDto(startDate, endDate, 0.0, "testUser", 3.0, "", "", "", 5);

        when(availableBoatPeriodService.findPeriodsBetweenDates(startDate, endDate)).thenReturn(availableBoatPeriodSet);

        when(boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(1L, 1L, startDate, endDate)).thenReturn(false);
        when(boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(2L, 1L, startDate, endDate)).thenReturn(false);
        when(boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(3L, 1L, startDate, endDate)).thenReturn(false);

        when(boatReservationRepository.boatReservedInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(quickReservationBoatService.boatHasQuickReservationInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(boatReservationRepository.boatReservedInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(quickReservationBoatService.boatHasQuickReservationInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(boatReservationRepository.boatReservedInPeriod(3L, startDate, endDate)).thenReturn(false);
        when(quickReservationBoatService.boatHasQuickReservationInPeriod(3L, startDate, endDate)).thenReturn(false);

        when(clientService.findByUsername("testUser")).thenReturn(new Client(1L, "testUser"));

        assertThat(boatReservationService.getAvailableBoats(searchAvailablePeriodsBoatAndAdventureDto)).hasSize(3);
    }

    @Test
    public void testSearchAvailableBoats(){
        Address address1 = new Address(53,43,"Serbia","Novi Sad","Dunavska");
        Address address2 = new Address(53,43,"Serbia","Belgrade","Kralja Milana 1");
        Boat boat1 = new Boat(1L, "Boat 1", "Type 1", 10.0, "CODE1", "200 HP",
                "200KM/H", "Sonar, GPS.", address1, "Opis", 5, "Rules",
                "Fishing eq", 100.0, 4.5, "FREE.");
        Boat boat2 = new Boat(2L, "Boat 2", "Type 1", 10.0, "CODE1", "200 HP",
                "200KM/H", "Sonar, GPS.", address1, "Opis", 7, "Rules",
                "Fishing eq", 120.0, 4.0, "FREE.");
        Boat boat3 = new Boat(3L, "Boat 3", "Type 1", 10.0, "CODE1", "200 HP",
                "200KM/H", "Sonar, GPS.", address2, "Opis", 5, "Rules",
                "Fishing eq", 150.0, 3.5, "FREE.");

        AvailableBoatPeriod availableBoatPeriod1 = new AvailableBoatPeriod(1L, LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10), boat1);
        AvailableBoatPeriod availableBoatPeriod2 = new AvailableBoatPeriod(2L, LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10), boat2);
        AvailableBoatPeriod availableBoatPeriod3 = new AvailableBoatPeriod(3L, LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10), boat3);
        Set<AvailableBoatPeriod> availableBoatPeriodSet = new HashSet<>(Arrays.asList(availableBoatPeriod1, availableBoatPeriod2, availableBoatPeriod3));
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);

        SearchAvailablePeriodsBoatAndAdventureDto searchAvailablePeriodsBoatAndAdventureDto = new SearchAvailablePeriodsBoatAndAdventureDto(startDate, endDate, 0.0, "testUser", 3.0, "", "Novi Sad", "Serbia", 5);

        when(availableBoatPeriodService.findPeriodsBetweenDates(startDate, endDate)).thenReturn(availableBoatPeriodSet);

        when(boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(1L, 1L, startDate, endDate)).thenReturn(false);
        when(boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(2L, 1L, startDate, endDate)).thenReturn(false);
        when(boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(3L, 1L, startDate, endDate)).thenReturn(false);

        when(boatReservationRepository.boatReservedInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(quickReservationBoatService.boatHasQuickReservationInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(boatReservationRepository.boatReservedInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(quickReservationBoatService.boatHasQuickReservationInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(boatReservationRepository.boatReservedInPeriod(3L, startDate, endDate)).thenReturn(false);
        when(quickReservationBoatService.boatHasQuickReservationInPeriod(3L, startDate, endDate)).thenReturn(false);

        when(clientService.findByUsername("testUser")).thenReturn(new Client(1L, "testUser"));

        assertThat(boatReservationService.searchAvailableBoats(searchAvailablePeriodsBoatAndAdventureDto)).hasSize(2);
    }
}
