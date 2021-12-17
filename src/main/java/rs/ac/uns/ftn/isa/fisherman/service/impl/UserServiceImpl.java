package rs.ac.uns.ftn.isa.fisherman.service.impl;

import java.util.List;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.mail.UserActivationLink;
import rs.ac.uns.ftn.isa.fisherman.model.Authority;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;
import rs.ac.uns.ftn.isa.fisherman.model.User;
import rs.ac.uns.ftn.isa.fisherman.repository.UserRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AuthorityService;
import rs.ac.uns.ftn.isa.fisherman.service.UserService;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;

import javax.mail.MessagingException;


@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService<String> mailService;
    private  AuthorityService authorityService;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService<String> mailService,AuthorityService authorityService){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.mailService=mailService;
        this.authorityService=authorityService;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email);
        return u;
    }


    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public CabinOwner registerCabinOwner(CabinOwner cabinOwner, String sourceURL) throws MessagingException {
        List<Authority> auth = authorityService.findByname(cabinOwner.getRole());
        cabinOwner.setAuthorities(auth);
       // cabinOwner.setPassword(passwordEncoder.encode(cabinOwner.getPassword()));
        String activationURL= RandomString.make(64);
        cabinOwner.setActivationURL(activationURL);
        cabinOwner=userRepository.save(cabinOwner);
        sendActivationURL(cabinOwner,sourceURL);
        return cabinOwner;
    }

    @Override
    public User activateAccount(String email, String code) {
        User user = findByEmail(email);
        if (!user.getActivationURL().equals(code)) {
            return null;
        }
        user.setEnabled(true);
        user.setActivationURL(null);
        user = this.userRepository.save(user);
        return user;
    }

    private void sendActivationURL(CabinOwner cabinOwner, String sourceURL) throws MessagingException {
        String verificationURL= sourceURL + "/activation/" + cabinOwner.getActivationURL() + "/" + cabinOwner.getEmail();
        mailService.sendMail(cabinOwner.getEmail(),verificationURL,new UserActivationLink());
    }

}