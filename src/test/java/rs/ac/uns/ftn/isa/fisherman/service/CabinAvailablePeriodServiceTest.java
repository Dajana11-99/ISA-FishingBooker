package rs.ac.uns.ftn.isa.fisherman.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.AvailableCabinPeriodRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.AvailableCabinPeriodServiceImpl;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CabinAvailablePeriodServiceTest {
    @Mock
    private AvailableCabinPeriodRepository availableCabinPeriodRepository;

    @InjectMocks
    private AvailableCabinPeriodServiceImpl availableCabinPeriodService;

    @Test
    public void testAddFreeDaysWithCuttingExistingAvailablePeriod() {
        LocalDateTime currentDate=LocalDateTime.now();
        LocalDateTime availableDaysStart=currentDate.plusDays(1);
        LocalDateTime availableDaysEnd=currentDate.plusDays(10);
        LocalDateTime freeDaysStart=currentDate.plusDays(5);
        LocalDateTime freeDaysEnd=currentDate.plusDays(7);

        AvailableCabinPeriod availableCabinPeriod=new AvailableCabinPeriod(1L,availableDaysStart,availableDaysEnd,new CabinOwner(),new Cabin());
        AvailableCabinPeriod freeDays=new AvailableCabinPeriod(1L,freeDaysStart,freeDaysEnd,new CabinOwner(),new Cabin());

        when(availableCabinPeriodRepository.findAll()).thenReturn(Arrays.asList(availableCabinPeriod,new AvailableCabinPeriod()));

        availableCabinPeriodService.setEditedAvailablePeriod(availableCabinPeriod,freeDays);
        when(availableCabinPeriodRepository.save(freeDays)); //save u servisu napravi 2 nova perioda


        assertEquals(availableCabinPeriod.getStartDate(),availableDaysStart);
        assertEquals(availableCabinPeriod.getEndDate().plusMinutes(1),freeDaysStart);
        assertEquals(freeDays.getStartDate(),freeDaysEnd.plusMinutes(1));
        assertEquals(freeDays.getEndDate(),availableDaysEnd);
    }

    @Test
    public void testAddFreeDaysAtTheBeginningOfTheAvailablePeriodInterval() {
        LocalDateTime currentDate=LocalDateTime.now();
        LocalDateTime availableDaysStart=currentDate.plusDays(1);
        LocalDateTime availableDaysEnd=currentDate.plusDays(10);
        LocalDateTime freeDaysStart=currentDate.plusDays(1);
        LocalDateTime freeDaysEnd=currentDate.plusDays(3);

        AvailableCabinPeriod availableCabinPeriod=new AvailableCabinPeriod(1L,availableDaysStart,availableDaysEnd,new CabinOwner(),new Cabin());
        AvailableCabinPeriod freeDays=new AvailableCabinPeriod(1L,freeDaysStart,freeDaysEnd,new CabinOwner(),new Cabin());

        when(availableCabinPeriodRepository.findAll()).thenReturn(Arrays.asList(availableCabinPeriod,new AvailableCabinPeriod()));

        availableCabinPeriodService.setEditedAvailablePeriod(availableCabinPeriod,freeDays);
        when(availableCabinPeriodRepository.save(availableCabinPeriod));
        assertEquals(availableCabinPeriod.getStartDate(),freeDaysEnd.plusMinutes(1));
        assertEquals(availableCabinPeriod.getEndDate(),availableDaysEnd);
    }
    @Test
    public void testAddFreeDaysAtTheEndOfTheAvailablePeriodInterval() {
        LocalDateTime currentDate=LocalDateTime.now();
        LocalDateTime availableDaysStart=currentDate.plusDays(1);
        LocalDateTime availableDaysEnd=currentDate.plusDays(10);
        LocalDateTime freeDaysStart=currentDate.plusDays(7);
        LocalDateTime freeDaysEnd=currentDate.plusDays(10);

        AvailableCabinPeriod availableCabinPeriod=new AvailableCabinPeriod(1L,availableDaysStart,availableDaysEnd,new CabinOwner(),new Cabin());
        AvailableCabinPeriod freeDays=new AvailableCabinPeriod(1L,freeDaysStart,freeDaysEnd,new CabinOwner(),new Cabin());

        when(availableCabinPeriodRepository.findAll()).thenReturn(Arrays.asList(availableCabinPeriod,new AvailableCabinPeriod()));

        availableCabinPeriodService.setEditedAvailablePeriod(availableCabinPeriod,freeDays);
        when(availableCabinPeriodRepository.save(availableCabinPeriod));
        assertEquals(availableCabinPeriod.getStartDate(),availableDaysStart);
        assertEquals(availableCabinPeriod.getEndDate(),freeDaysStart.minusMinutes(1));
    }
}
