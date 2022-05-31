package rs.ac.uns.ftn.isa.fisherman.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.dto.*;
import rs.ac.uns.ftn.isa.fisherman.mapper.CabinReservationMapper;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationCancellationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.ReservationCabinServiceImpl;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CabinReservationServiceTest {
    @Mock
    private CabinReservationRepository cabinReservationRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private CabinReservationCancellationRepository cabinReservationCancellationRepository;
    @Mock
    private AvailableCabinPeriodService availableCabinPeriodService;
    @Mock
    private QuickReservationCabinService quickReservationCabinService;
    @Mock
    private CabinOwnerService cabinOwnerService;
    @Mock
    private ReservationPaymentService reservationPaymentService;

    private final CabinReservationMapper cabinReservationMapper = new CabinReservationMapper();

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

        List<CabinReservation> reservations = Arrays.asList(firstReservation,secondReservation);
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

    @Test
    public void testGetAvailableCabins(){
        Address address1 = new Address(53,43,"Serbia","Novi Sad","Dunavska");
        Address address2 = new Address(53,43,"Serbia","Belgrade","Kralja Milana 1");
        Cabin cabin1 = new Cabin(1L, "Cabin 1", "Fantastic cabin", 2, 2, "No children", 50, address1, 4.0, "FREE.");
        Cabin cabin2 = new Cabin(2L, "Cabin 2", "Fantastic cabin", 2, 3, "No children", 120, address1, 4.2, "FREE.");
        Cabin cabin3 = new Cabin(3L, "Cabin 3", "Fantastic cabin", 1, 5, "No children", 85, address2, 4.4, "FREE.");
        Cabin cabin4 = new Cabin(4L, "Cabin 4", "Fantastic cabin", 1, 2, "No children", 140, address2, 4.6, "FREE.");
        AvailableCabinPeriod availableCabinPeriod1 = new AvailableCabinPeriod(1L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(10),new CabinOwner(), cabin1);
        AvailableCabinPeriod availableCabinPeriod2 = new AvailableCabinPeriod(2L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(10),new CabinOwner(), cabin2);
        AvailableCabinPeriod availableCabinPeriod3 = new AvailableCabinPeriod(3L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(10),new CabinOwner(), cabin3);
        AvailableCabinPeriod availableCabinPeriod4 = new AvailableCabinPeriod(4L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(4),new CabinOwner(), cabin4);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(5);

        SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto = new SearchAvailablePeriodsCabinDto(startDate, endDate, 0.0, "testUser", 3.0, "", "", "", 2, 2);

        when(cabinReservationCancellationRepository.getByUsersId(1L)).thenReturn(Arrays.asList());
        when(availableCabinPeriodService.findAll()).thenReturn(Arrays.asList(availableCabinPeriod1, availableCabinPeriod2, availableCabinPeriod3, availableCabinPeriod4)); // add available periods

        when(cabinReservationRepository.cabinReservedInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(cabinReservationRepository.cabinReservedInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(cabinReservationRepository.cabinReservedInPeriod(3L, startDate, endDate)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(3L, startDate, endDate)).thenReturn(false);

        when(clientService.findByUsername("testUser")).thenReturn(new Client(1L, "testUser"));

        assertThat(reservationCabinService.getAvailableCabins(searchAvailablePeriodsCabinDto)).hasSize(2);
    }

    @Test
    public void testSearchAvailableCabins(){
        Address address1 = new Address(53,43,"Serbia","Novi Sad","Dunavska");
        Address address2 = new Address(53,43,"Serbia","Belgrade","Kralja Milana 1");
        Cabin cabin1 = new Cabin(1L, "Cabin 1", "Fantastic cabin", 2, 2, "No children", 50, address1, 4.0, "FREE.");
        Cabin cabin2 = new Cabin(2L, "Cabin 2", "Fantastic cabin", 2, 3, "No children", 120, address1, 4.2, "FREE.");
        Cabin cabin3 = new Cabin(3L, "Cabin 3", "Fantastic cabin", 1, 5, "No children", 85, address2, 4.4, "FREE.");
        Cabin cabin4 = new Cabin(4L, "Cabin 4", "Fantastic cabin", 1, 2, "No children", 140, address2, 4.6, "FREE.");
        AvailableCabinPeriod availableCabinPeriod1 = new AvailableCabinPeriod(1L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(10),new CabinOwner(), cabin1);
        AvailableCabinPeriod availableCabinPeriod2 = new AvailableCabinPeriod(2L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(10),new CabinOwner(), cabin2);
        AvailableCabinPeriod availableCabinPeriod3 = new AvailableCabinPeriod(3L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(10),new CabinOwner(), cabin3);
        AvailableCabinPeriod availableCabinPeriod4 = new AvailableCabinPeriod(4L, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(4),new CabinOwner(), cabin4);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(5);

        SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto = new SearchAvailablePeriodsCabinDto(startDate, endDate, 70.0, "testUser", 3.0, "", "", "", 2, 2);

        when(cabinReservationCancellationRepository.getByUsersId(1L)).thenReturn(Arrays.asList());
        when(availableCabinPeriodService.findAll()).thenReturn(Arrays.asList(availableCabinPeriod1, availableCabinPeriod2, availableCabinPeriod3, availableCabinPeriod4)); // add available periods

        when(cabinReservationRepository.cabinReservedInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(1L, startDate, endDate)).thenReturn(false);
        when(cabinReservationRepository.cabinReservedInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(2L, startDate, endDate)).thenReturn(false);
        when(cabinReservationRepository.cabinReservedInPeriod(3L, startDate, endDate)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(3L, startDate, endDate)).thenReturn(false);

        when(clientService.findByUsername("testUser")).thenReturn(new Client(1L, "testUser"));

        assertThat(reservationCabinService.searchAvailableCabins(searchAvailablePeriodsCabinDto)).hasSize(1);
    }
    @Test
    public void testMakeReservation() throws Exception {
        CabinDto cabin = new CabinDto();
        cabin.setId(1L);
        cabin.setOwnerUsername("testOwner");
        cabin.setAddressDto(new AddressDTO());
        cabin.setName("testCabin");
        CabinOwner cabinOwner = new CabinOwner();
        cabinOwner.setId(2L);
        cabinOwner.setUsername("testOwner");
        LocalDateTime startDate1 = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate1 = LocalDateTime.now().plusDays(2);
        LocalDateTime startDate2 = LocalDateTime.now().plusDays(3);
        LocalDateTime endDate2 = LocalDateTime.now().plusDays(4);
        LocalDateTime startDate3 = LocalDateTime.now().plusDays(5);
        LocalDateTime endDate3 = LocalDateTime.now().plusDays(6);

        CabinReservationDto cabinReservationDto1 = new CabinReservationDto(1L, startDate1, endDate1, "testUser",
                "", new PaymentInformationDto(), false, false,"",
                cabin, null, false);
        CabinReservationDto cabinReservationDto2 = new CabinReservationDto(2L, startDate2, endDate2, "testUser",
                "", new PaymentInformationDto(), false, false,"",
                cabin, null, false);
        CabinReservationDto cabinReservationDto3 = new CabinReservationDto(2L, startDate3, endDate3, "testUser",
                "", new PaymentInformationDto(), false, false,"",
                cabin, null, false);
        CabinReservation cabinReservation = cabinReservationMapper.cabinReservationDtoToCabinReservation(cabinReservationDto3);
        cabinReservation.getCabin().setCabinOwner(cabinOwner);
        cabinReservation.setClient(clientService.findByUsername(cabinReservationDto3.getClientUsername()));

        when(cabinReservationRepository.cabinReservedInPeriod(1L, startDate1, endDate1)).thenReturn(true);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(1L, startDate1, endDate1)).thenReturn(false);
        when(cabinReservationRepository.cabinReservedInPeriod(1L, startDate2, endDate2)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(1L, startDate2, endDate2)).thenReturn(true);
        when(cabinReservationRepository.cabinReservedInPeriod(1L, startDate3, endDate3)).thenReturn(false);
        when(quickReservationCabinService.cabinHasQuickReservationInPeriod(1L, startDate3, endDate3)).thenReturn(false);
        when(cabinOwnerService.findByUsername("testOwner")).thenReturn(cabinOwner);
        when(clientService.findByUsername("testUser")).thenReturn(new Client(1L, "testUser"));
        when(reservationPaymentService.setTotalPaymentAmount(cabinReservation,cabinOwner)).thenReturn(new PaymentInformation());

        assertThat(reservationCabinService.makeReservation(cabinReservationDto1)).isFalse();
        assertThat(reservationCabinService.makeReservation(cabinReservationDto2)).isFalse();
        assertThat(reservationCabinService.makeReservation(cabinReservationDto3)).isTrue();
    }
}
