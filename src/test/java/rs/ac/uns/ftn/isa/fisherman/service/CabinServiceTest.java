package rs.ac.uns.ftn.isa.fisherman.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.model.AdditionalServices;
import rs.ac.uns.ftn.isa.fisherman.model.Address;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.CabinServiceImpl;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CabinServiceTest {
    @Mock
    private CabinRepository cabinRepository;

    @InjectMocks
    private CabinServiceImpl cabinService;

    @Test
    public void testFindCabinByName() {
        Set<AdditionalServices> cabinServices=new HashSet<>();
        AdditionalServices additionalService=new AdditionalServices(1L,"WiFi",1);
        cabinServices.add(additionalService);
        Cabin cabin1=new Cabin(1L,"My cabin 1","",1,2,"",150,new Address(46,45,"Country","City","StreeetAndNum"),cabinServices,5.0,"FREE");
        Cabin cabin2=new Cabin(2L,"My cabin 2","",1,2,"",150,new Address(46,45,"Country","City","StreeetAndNum"),cabinServices,5.0,"FREE");
        Cabin cabin3=new Cabin(3L,"My cabin 3","",1,2,"",150,new Address(46,45,"Country","City","StreeetAndNum"),cabinServices,5.0,"FREE");

        when(cabinRepository.findAll()).thenReturn(Arrays.asList(cabin1,cabin2,cabin3));
        when(cabinRepository.findByName("My cabin 4")).thenReturn(null);

        Cabin result=cabinService.findByName("My cabin 4");
        assertNull(result);
    }
}
