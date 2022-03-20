package rs.ac.uns.ftn.isa.fisherman.service.impl;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.mail.*;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.UserRepository;
import rs.ac.uns.ftn.isa.fisherman.security.TokenUtils;
import rs.ac.uns.ftn.isa.fisherman.service.AuthorityService;
import rs.ac.uns.ftn.isa.fisherman.service.UserService;

import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private MailService<String> mailService;
    private  AuthorityService authorityService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailService<String> mailService, AuthorityService authorityService, PasswordEncoder passwordEncoder, TokenUtils tokenUtils){
        this.userRepository=userRepository;
        this.mailService=mailService;
        this.authorityService=authorityService;
        this.passwordEncoder =passwordEncoder;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
      return userRepository.findByUsername(username);
    }
    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    public String findRoleById(Long id){
        return userRepository.findRoleById(id);
    }
    @Override
    public CabinOwner registerCabinOwner(CabinOwner cabinOwner) throws MessagingException {
        List<Authority> auth = authorityService.findByname(cabinOwner.getRoleApp());
        cabinOwner.setAuthorities(auth);
        cabinOwner.setPassword(passwordEncoder.encode(cabinOwner.getPassword()));
        cabinOwner=userRepository.save(cabinOwner);
        return cabinOwner;
    }
    @Override
    public BoatOwner registerBoatOwner(BoatOwner boatOwner) throws MessagingException {

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
    public FishingInstructor registerFishingInstructor(FishingInstructor fishingInstructor) throws MessagingException {
        List<Authority> auth = authorityService.findByname(fishingInstructor.getRoleApp());
        fishingInstructor.setAuthorities(auth);
        fishingInstructor.setPassword(passwordEncoder.encode(fishingInstructor.getPassword()));
        fishingInstructor=userRepository.save(fishingInstructor);
        return fishingInstructor;
    }

    @Override
    public Client registerClient(Client client) throws MessagingException {
        List<Authority> auth = authorityService.findByname(client.getRoleApp());
        client.setAuthorities(auth);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client=userRepository.save(client);
        return client;
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
    private void sendActivationURL(CabinOwner cabinOwner, String sourceURL) throws MessagingException {
        String verificationURL= sourceURL + "/activation/" + cabinOwner.getActivationURL() + "/" + cabinOwner.getUsername();
        mailService.sendMail(cabinOwner.getUsername(),verificationURL,new AccountAcceptedInfo());
    }
    public void acceptAccount(User user){
        user.setEnabled(true);
        userRepository.save(user);
        try {
            mailService.sendMail(user.getUsername(),user.getUsername(),new AccountAcceptedInfo());
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
        User newInfo= findByUsername(userRequest.getUsername());
        newInfo.setPhoneNum(userRequest.getPhoneNum());
        newInfo.getAddress().setCountry(userRequest.getAddress().getCountry());
        newInfo.getAddress().setCity(userRequest.getAddress().getCity());
        newInfo.getAddress().setStreetAndNum(userRequest.getAddress().getStreetAndNum());
        newInfo.getAddress().setLongitude(userRequest.getAddress().getLongitude());
        newInfo.getAddress().setLatitude(userRequest.getAddress().getLatitude());
        userRepository.save(newInfo);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
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
    public void sendDenyReason(String response, String recipient) throws MessagingException {
        mailService.sendMail(recipient,response,new AccountDeletingDenied());
        User user=userRepository.findByUsername(recipient);
        user.setReasonForDeleting("");
        userRepository.save(user);
    }

    @Override
    public void sendAcceptReason(String response, String recipient) throws MessagingException {
        mailService.sendMail(recipient,response,new AccountDeletingAccepted());
        User user=userRepository.findByUsername(recipient);
        userRepository.delete(user);
    }

}