package rs.ac.uns.ftn.isa.fisherman.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.ReservationCabinServiceImpl;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CabinReservationServiceTest {
    @Mock
    private CabinReservationRepository cabinReservationRepository;

    @InjectMocks
    private ReservationCabinServiceImpl reservationCabinService;

    @Test
    public void testSumOwnersProfitByDays() {

        CabinReservation firstReservation=new CabinReservation(1L,LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),null,new PaymentInformation(120.00,20.00,100.00),
                false,"co@gmail.com",null,null,false);
        CabinReservation secondReservation=new CabinReservation(2L,LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(4),null,new PaymentInformation(70.00,20.00,50.00),
                false,"co@gmail.com",null,null,false);
        CabinReservation thirdReservation=new CabinReservation(2L,LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),null,new PaymentInformation(70.00,20.00,50.00),
                false,"co2@gmail.com",null,null,false);
        when(cabinReservationRepository.findAll()).thenReturn(Arrays.asList(firstReservation,secondReservation,thirdReservation));
        when(cabinReservationRepository.findReservationsInPeriodToSumProfit("co@gmail.com",
                LocalDateTime.now(),LocalDateTime.now().plusDays(6)))
                .thenReturn(Arrays.asList(firstReservation,secondReservation));


        List<CabinReservation> reservations = reservationCabinService.findReservationsToSumProfit("co@gmail.com",
                LocalDateTime.now(),LocalDateTime.now().plusDays(6));
        double profit= reservationCabinService.sumProfitOfPricesCalculatedByDays(reservations,LocalDateTime.now(),LocalDateTime.now().plusDays(6));

        assertEquals(150.00,profit,0.001);
    }
    @Test
    public void testCalculateNumberOfOverlapingDatesForReservationReport() {
        LocalDateTime currentDate=LocalDateTime.now();
        LocalDateTime startReport=currentDate.now().plusDays(1);
        LocalDateTime endReport=currentDate.now().plusDays(10);
        LocalDateTime reservationStart=currentDate.now().minusDays(4);
        LocalDateTime reservationEnd=currentDate.now().plusDays(4);
        long numOfOverlapingDates= reservationCabinService.calculateOverlapingDates(
                startReport,endReport,reservationStart,reservationEnd);
        assertEquals(3,numOfOverlapingDates);
    }

}
