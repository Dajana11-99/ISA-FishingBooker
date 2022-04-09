package rs.ac.uns.ftn.isa.fisherman.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.BoatServiceImpl;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoatServiceTest {
    @Mock
    private BoatRepository boatRepository;

    @InjectMocks
    private BoatServiceImpl boatService;

    @Test
    public void testCalculateAvgRatingOfAllBoatsByOwnersId() {
        BoatOwner boatOwner1=new BoatOwner(1L,"First owner","Lastname","bo@gmail.com","123","2130992103",null,"");
        BoatOwner boatOwner2=new BoatOwner(2L,"Second owner","Lastname","bo2@gmail.com","123","2130992103",null,"");
        Boat boat1= new Boat(1L,"Boat1","",13,"32123","123","3213","",null,"",1,"","",12.5,5.0,"FREE");
        Boat boat2= new Boat(2L,"Boat1","",13,"32123","123","3213","",null,"",1,"","",12.5,4.5,"FREE");
        Boat boat3= new Boat(3L,"Boat1","",13,"32123","123","3213","",null,"",1,"","",12.5,3.0,"FREE");
        Boat boat4= new Boat(4L,"Boat1","",13,"32123","123","3213","",null,"",1,"","",12.5,2.1,"FREE");
        Boat boat5= new Boat(5L,"Boat1","",13,"32123","123","3213","",null,"",1,"","",12.5,5.0,"FREE");
        boat1.setBoatOwner(boatOwner1);
        boat2.setBoatOwner(boatOwner1);
        boat3.setBoatOwner(boatOwner1);
        boat4.setBoatOwner(boatOwner1);
        boat5.setBoatOwner(boatOwner2);
        when(boatRepository.findAll()).thenReturn(Arrays.asList(boat1,boat2,boat3,boat4,boat5));
        when(boatRepository.findByOwnersId(boatOwner1.getId())).thenReturn(Arrays.asList(boat1,boat2,boat3,boat4));

        double result=boatService.findAvgBoatRatingByOwnerId(boatOwner1.getId());
        assertEquals(3.65,result,0.001);
    }

}
