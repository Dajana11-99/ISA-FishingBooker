package rs.ac.uns.ftn.isa.fisherman.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isa.fisherman.dto.AddressDTO;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.UserRepository;
import rs.ac.uns.ftn.isa.fisherman.service.impl.UserServiceImpl;
import javax.transaction.Transactional;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @Transactional
    @Rollback(true)
    public void testEditCabinOwnerPersonalInformation() {
        UserRequestDTO userRequestDTO=new UserRequestDTO(1L,"milica@gmail.com","123",
                "Milica","Jovanovic", "021/345-345",
                new AddressDTO(	19.833549,45.267136,"Serbia",
                "Novi Sad","Novosadska 1"),"reason","CABINOWNER",3.4);
        User user=new CabinOwner(1L,"Milica","Petrovic","milica@gmail.com",
                "123","011/345-345",new Address(20.457273,44.787197,"Serbia",
                "Beograd","Beogradska 1"),"reason",3.4);

        when(userRepository.getAll()).thenReturn(Arrays.asList(user));
        when(userRepository.findByUsername(userRequestDTO.getUsername())).thenReturn(user);

        userService.editUser(userRequestDTO);
        userRepository.save(user);

        assertEquals(user.getAddress().getCity(),userRequestDTO.getAddress().getCity());
        assertEquals(user.getAddress().getStreetAndNum(),userRequestDTO.getAddress().getStreetAndNum());
        assertEquals(user.getPhoneNum(),userRequestDTO.getPhoneNum());
        assertEquals(user.getLastName(),userRequestDTO.getLastname());
    }
}
