package rs.ac.uns.ftn.isa.fisherman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.MailDto;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.dto.VerificationDTO;
import rs.ac.uns.ftn.isa.fisherman.mapper.*;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.service.*;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {


    @Autowired
    private UserService userService;


    @Autowired
    private AdminService adminService;

    public  AccountController(){}
    private String success= "Success!";
    private AdminMapper adminMapper = new AdminMapper();



    @PostMapping("/acceptAccount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> acceptAccount(@RequestBody UserRequestDTO userRequest){
        userService.acceptAccount(userService.findByUsername(userRequest.getUsername()));

        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }
    @PostMapping("/denyAccount/{reason}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> denyAccount(@PathVariable ("reason") String reason, @RequestBody UserRequestDTO userRequest) {
        userService.denyAccount(userService.findByUsername(userRequest.getUsername()),reason);
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }


    @PostMapping("/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activate(@RequestBody VerificationDTO verificationDTO) {
        String email = verificationDTO.getEmail();
        String code = verificationDTO.getActivationCode();

        if(this.userService.activateAccount(email, code) != null){
            return new ResponseEntity<>(success, HttpStatus.OK);
        }
        return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/passwordStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> hasAlreadyResetPassword(@RequestBody UserRequestDTO userRequest) {
        Boolean passwordStatus= adminService.hasAlreadyResetPassword(userRequest.getUsername());
        return new ResponseEntity<>(passwordStatus, HttpStatus.OK);
    }




    @GetMapping("/getAllRequests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserRequestDTO>> getAllRequestsForDeletingAccount() {
        List<UserRequestDTO> users= new ArrayList<>();
       for(User user : userService.getAllRequestsForDeletingAccount())
            users.add(userMapper.userToDeleteUserRequestDTO(user));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/sendDenyReasonForDeletingAccount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sendDenyReasonForDeletingAccount(@RequestBody MailDto mailDto) throws MessagingException {
        userService.sendDenyReason(mailDto.getResponse(),mailDto.getRecipient());
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PostMapping("/sendAcceptReasonForDeletingAccount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sendAcceptReasonForDeletingAccount(@RequestBody MailDto mailDto) throws MessagingException {
        userService.sendAcceptReason(mailDto.getResponse(),mailDto.getRecipient());
        return new ResponseEntity<>(success, HttpStatus.OK);
    }



}
