package rs.ac.uns.ftn.isa.fisherman.service.impl;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.service.*;
import rs.ac.uns.ftn.isa.fisherman.mail.*;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.UserRepository;
import rs.ac.uns.ftn.isa.fisherman.security.TokenUtils;

import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService<String> mailService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private CabinReservationService cabinReservationService;
    @Autowired
    private QuickReservationCabinService quickReservationCabinService;
    @Autowired
    private AdventureReservationService adventureReservationService;
    @Autowired
    private QuickReservationAdventureService quickReservationAdventureService;
    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private QuickReservationBoatService quickReservationBoatService;



    public UserServiceImpl() {
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
      return userRepository.findByUsername(username);
    }
    public List<User> findAll()  {
        return userRepository.getAll();
    }

    @Override
    public List<User> getNewUsers() {
        return  userRepository.getNewUsers();
    }

    public String findRoleById(Long id){
        return userRepository.findRoleById(id);
    }
    @Override
    public CabinOwner registerCabinOwner(CabinOwner cabinOwner) {
        List<Authority> auth = authorityService.findByname(cabinOwner.getRoleApp());
        cabinOwner.setAuthorities(auth);
        cabinOwner.setPassword(passwordEncoder.encode(cabinOwner.getPassword()));
        cabinOwner=userRepository.save(cabinOwner);
        return cabinOwner;
    }
    @Override
    public BoatOwner registerBoatOwner(BoatOwner boatOwner) {

        List<Authority> auth = authorityService.findByname(boatOwner.getRoleApp());
        boatOwner.setAuthorities(auth);
        boatOwner.setPassword(passwordEncoder.encode(boatOwner.getPassword()));
        boatOwner=userRepository.save(boatOwner);
        return boatOwner;
    }

    @Override
    public Admin registerAdmin(Admin admin) {
        List<Authority> auth = authorityService.findByname(admin.getRoleApp());
        admin.setAuthorities(auth);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setEnabled(true);
        admin=userRepository.save(admin);
        return admin;
    }

    @Override
    public FishingInstructor registerFishingInstructor(FishingInstructor fishingInstructor){
        List<Authority> auth = authorityService.findByname(fishingInstructor.getRoleApp());
        fishingInstructor.setAuthorities(auth);
        fishingInstructor.setPassword(passwordEncoder.encode(fishingInstructor.getPassword()));
        fishingInstructor=userRepository.save(fishingInstructor);
        return fishingInstructor;
    }

    @Override
    public Client registerClient(Client client){
        List<Authority> auth = authorityService.findByname(client.getRoleApp());
        client.setActivationURL(getEncodedString(client.getUsername()));
        client.setAuthorities(auth);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client=userRepository.save(client);
        try{
           sendActivationLink(client.getUsername(), client.getActivationURL());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
        return client;
    }

    private String getEncodedString(String username) {
        String encoded = passwordEncoder.encode(username);
        encoded = encoded.replaceAll("[^A-Za-z0-9]", "");
        return encoded.substring(0, 15);
    }

    private void sendActivationLink(String recipient, String activationCode) throws MessagingException {

        mailService.sendMail(recipient, "http://localhost:8080/accountActivation/" + activationCode + "/" + recipient,new ActivationLink());
    }

    public User activateAccount(String email, String code) {
        User user = findByUsername(email);
        if (!user.getActivationURL().equals(code)) {
            return null;
        }
        user.setEnabled(true);
        user.setActivationURL(null);
        user = this.userRepository.save(user);
        return user;
    }
    public void acceptAccount(User user){
        user.setEnabled(true);
        userRepository.save(user);

        try {
            mailService.sendMail(user.getUsername(),"Account accepted.",new AccountAcceptedInfo());
        } catch (MessagingException e) {
             logger.error(e.toString());
        }
    }
    public void denyAccount(User user,String reason){
        String email=user.getUsername();
        userRepository.delete(user);

        try {
            mailService.sendMail(email,reason,new AccountDeniedInfo());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public void editUser(UserRequestDTO userRequest) {
        User newInfo= userRepository.findByUsername(userRequest.getUsername());
        newInfo.setLastName(userRequest.getLastname());
        newInfo.setPhoneNum(userRequest.getPhoneNum());
        newInfo.getAddress().setCountry(userRequest.getAddress().getCountry());
        newInfo.getAddress().setCity(userRequest.getAddress().getCity());
        newInfo.getAddress().setStreetAndNum(userRequest.getAddress().getStreetAndNum());
        newInfo.getAddress().setLongitude(userRequest.getAddress().getLongitude());
        newInfo.getAddress().setLatitude(userRequest.getAddress().getLatitude());
        userRepository.save(newInfo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public String deleteUser(User user)   {
        try {
            if (checkIfCanDeletingUser(user)) {
                userRepository.delete(user);
                return "TRUE";
            }
            return "FALSE";
        }catch (Exception e){
            return "Exception"  ;
        }
    }

    @Override
    public void saveDeleteAccountRequest(String username, String reasonForDeleting) {
        User user = userRepository.findByUsername(username);
        user.setReasonForDeleting(reasonForDeleting);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllRequestsForDeletingAccount() {
        return userRepository.getReguestsForDeletingAccount();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public boolean sendDenyReason(String response, String recipient) throws Exception {
        User user=userRepository.findByUsername(recipient);
        if(user.getReasonForDeleting() == null)return false;
        user.setReasonForDeleting("");
        userRepository.save(user);
        mailService.sendMail(recipient,response,new AccountDeletingDenied());

        return  true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public boolean sendAcceptReason(String response, String recipient) throws Exception {
        User user=userRepository.findByUsername(recipient);
        if(user.getReasonForDeleting().equals(""))return false;
        userRepository.delete(user);
        mailService.sendMail(recipient,response,new AccountDeletingAccepted());
        return  true;
    }

    @Override
    public String getUsernameFromToken(String s) {
        return tokenUtils.getUsernameFromToken(s);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Double findRatingByUsername(String username) {
        return userRepository.findRatingByUsername(username);
    }

    @Override
    public void updateOwnersRating(String username,Double grade) {
        User user = userRepository.findByUsername(username);
        Double newGrade= user.getRating()+grade;
        double rating = newGrade/2.0;
        user.setRating(rating);
        userRepository.save(user);
    }
    private boolean checkIfCanDeletingUser(User user){
        if(user.getRoleApp().equals("ROLE_CABINOWNER")){
            if(checkCabinOwner(user)) return false ;
        }else if(user.getRoleApp().equals("ROLE_BOATOWNER")){
            if(checkBoatOwner(user)) return false ;
        }else if(user.getRoleApp().equals("ROLE_FISHING_INSTRUCTOR")){
            if(checkFishingInstructor(user)) return false ;
        }else if(user.getRoleApp().equals("ROLE_CLIENT")){
            if(checkClient(user)) return false ;
        }
        return  true;
    }

    private boolean checkClient(User user){
        if(cabinReservationService.checkIfClientHasFutureReservations(user.getId()) ||
                quickReservationCabinService.checkIfClientHasFutureReservations(user.getId())||
                boatReservationService.checkIfClientHasFutureReservations(user.getId()) ||
                quickReservationBoatService.checkIfClientHasFutureReservations(user.getId())||
                adventureReservationService.checkIfClientHasFutureReservations(user.getId()) ||
                quickReservationAdventureService.checkIfClientHasFutureReservations(user.getId()))
            return  true;

     return  false;
    }

    private boolean checkCabinOwner(User user){
        if(cabinReservationService.checkIfOwnerHasFutureReservations(user.getUsername()) ||
                quickReservationCabinService.checkIfOwnerHasFutureReservations(user.getUsername()))
            return  true;

        return  false;
    }

    private boolean checkBoatOwner(User user){
        if(boatReservationService.checkIfOwnerHasFutureReservations(user.getUsername()) ||
                quickReservationBoatService.checkIfOwnerHasFutureReservations(user.getUsername()))
            return  true;

        return  false;
    }

    private boolean checkFishingInstructor(User user){
        if(adventureReservationService.checkIfOwnerHasFutureReservations(user.getUsername()) ||
                quickReservationAdventureService.checkIfOwnerHasFutureReservations(user.getUsername()))
            return  true;

        return  false;
    }


}