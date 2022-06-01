package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import javax.mail.MessagingException;
import java.util.List;


public interface UserService {
    User findByUsername(String username);
    List<User> findAll ();
    List<User> getNewUsers ();
    User registerCabinOwner(CabinOwner cabinOwner);
    User registerBoatOwner(BoatOwner boatOwner);
    User registerClient(Client client);
    User registerAdmin(Admin admin);
    User registerFishingInstructor(FishingInstructor fishingInstructor);
    User activateAccount(String email, String code);
    void acceptAccount(User user);
    void denyAccount(User user,String reason);
    void editUser(UserRequestDTO userRequest);
    String deleteUser(User user);
    void saveDeleteAccountRequest(String username, String reasonForDeleting);
    List<User> getAllRequestsForDeletingAccount();
    boolean sendDenyReason(String response, String recipient) throws MessagingException, Exception;
    boolean sendAcceptReason(String response, String recipient) throws MessagingException, Exception;
    String getUsernameFromToken(String s);
    void save(User user);
    Double findRatingByUsername(String username);
    void updateOwnersRating(String username,Double grade);
}