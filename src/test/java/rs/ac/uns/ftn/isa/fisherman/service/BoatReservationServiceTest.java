package rs.ac.uns.ftn.isa.fisherman.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import rs.ac.uns.ftn.isa.fisherman.model.Client;
import rs.ac.uns.ftn.isa.fisherman.model.PaymentInformation;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.BoatReservationServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoatReservationServiceTest {
    @Mock
    private BoatReservationRepository boatReservationRepository;

    @InjectMocks
    private BoatReservationServiceImpl boatReservationService;

    @Test
    public void testSumProfitByHours() {
        BoatReservation firstReservation=new BoatReservation(1L, LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(3),null,new PaymentInformation(120.00,20.00,100.00),
                false,"bo@gmail.com",null,null,false);
        BoatReservation secondReservation=new BoatReservation(2L,LocalDateTime.now().plusHours(6),
                LocalDateTime.now().plusHours(7),null,new PaymentInformation(70.00,20.00,50.00),
                false,"bo@gmail.com",null,null,false);
        BoatReservation thirdReservation=new BoatReservation(3L,LocalDateTime.now().plusHours(8),
                LocalDateTime.now().plusHours(9),null,new PaymentInformation(70.00,20.00,50.00),
                false,"bo@gmail.com",null,null,false);
        BoatReservation fourthReservation=new BoatReservation(4L,LocalDateTime.now().plusHours(11),
                LocalDateTime.now().plusHours(3),null,new PaymentInformation(70.00,20.00,50.00),
                false,"bo@gmail.com",null,null,false);


        when(boatReservationRepository.findAll()).thenReturn(Arrays.asList(firstReservation,secondReservation,thirdReservation,fourthReservation));
        when(boatReservationRepository.findReservationsInPeriodToSumProfit("bo@gmail.com",
                LocalDateTime.now().plusHours(5),LocalDateTime.now().plusHours(10))) //zelim da pronadje samo rez u ovom intervalu i sabere profit po satu
                .thenReturn(Arrays.asList(secondReservation,thirdReservation));


        List<BoatReservation> reservations = boatReservationService.findReservationsToSumProfit("bo@gmail.com",
                LocalDateTime.now().plusHours(5),LocalDateTime.now().plusHours(10));
        double profit= boatReservationService.sumProfitOfPricesCalucatedByHours(reservations,LocalDateTime.now().plusHours(5),LocalDateTime.now().plusHours(10));
        assertThat(reservations).isNotNull();
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

}
