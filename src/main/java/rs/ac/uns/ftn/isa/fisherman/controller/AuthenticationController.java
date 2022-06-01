package rs.ac.uns.ftn.isa.fisherman.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.fisherman.dto.ChangePasswordDto;
import rs.ac.uns.ftn.isa.fisherman.dto.LogInDto;
import rs.ac.uns.ftn.isa.fisherman.dto.VerificationDTO;
import rs.ac.uns.ftn.isa.fisherman.model.Client;
import rs.ac.uns.ftn.isa.fisherman.service.LoginService;
import rs.ac.uns.ftn.isa.fisherman.service.UserService;
import rs.ac.uns.ftn.isa.fisherman.model.User;
import rs.ac.uns.ftn.isa.fisherman.model.UserTokenState;
import rs.ac.uns.ftn.isa.fisherman.mapper.*;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.security.TokenUtils;
import rs.ac.uns.ftn.isa.fisherman.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private static final String SUCCESS = "Success.";
    private static final String EMAIL_ALREADY_IN_USE = "Email already in use.";

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;

    private LoginService loginService;

    @Autowired
    public  AuthenticationController(LoginService logInService){
      this.loginService = logInService;
  }

    private final CabinOwnerMapper cabinOwnerMapper = new CabinOwnerMapper();
    private final BoatOwnerMapper boatOwnerMapper = new BoatOwnerMapper();
    private final FishingInstructorMapper fishingInstructorMapper = new FishingInstructorMapper();
    private final ClientMapper clientMapper = new ClientMapper();
    private final UserMapper userMapper=new UserMapper();

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody LogInDto userRequest) {
        try{
            UserTokenState userTokenState = loginService.logIn(userRequest.getUsername(),userRequest.getPassword());
            return ResponseEntity.ok(userTokenState);
        }catch (Exception e){
            return new ResponseEntity<>(new UserTokenState(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {
        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);
        String userType = user.getClass().getSimpleName();
        try{
            this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate());
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(userType, refreshedToken, expiresIn));
        } catch (Exception e){
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @PostMapping("/signUpCabinOwner")
    public ResponseEntity<String> registerCabinOwner(@RequestBody UserRequestDTO userRequest) {

            User existUser=userService.findByUsername(userRequest.getUsername());
            if(existUser== null)
            {
                this.userService.registerCabinOwner(cabinOwnerMapper.userRequestDTOToCabinOwner(userRequest));
                return ResponseEntity.status(201).body(SUCCESS);
            }
              return ResponseEntity.badRequest().body(EMAIL_ALREADY_IN_USE);
    }


    @PostMapping("/findByEmail")
    public UserRequestDTO findByEmail(@RequestBody UserRequestDTO userRequest){
        return userMapper.userToUserRequestDTO(userService.findByUsername(userRequest.getUsername()));
    }
    @PostMapping("/editUser")
    public void editUser(@RequestBody UserRequestDTO userRequest){
        userService.editUser(userRequest);
    }

    @PostMapping("/signUpBoatOwner")
    public ResponseEntity<String> registerBoatOwner(@RequestBody UserRequestDTO userRequest)  {
        User existUser = this.userService.findByUsername(userRequest.getUsername());
        if (existUser != null) {
            return new ResponseEntity<>(EMAIL_ALREADY_IN_USE, HttpStatus.BAD_REQUEST);
        }
        this.userService.registerBoatOwner(boatOwnerMapper.userRequestDtoToBoatOwner(userRequest));
        return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
    }

    @PostMapping("/signUpFishingInstructor")
    public ResponseEntity<String> registerFishingInstructor( @RequestBody UserRequestDTO userRequest) {
        User existUser = this.userService.findByUsername(userRequest.getUsername());
        if (existUser != null) {
            return new ResponseEntity<>(EMAIL_ALREADY_IN_USE, HttpStatus.BAD_REQUEST);
        }
        this.userService.registerFishingInstructor(fishingInstructorMapper.userRequestDtoToFishingInstructor(userRequest));
        return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
    }

    @PostMapping("/signUpClient")
    public ResponseEntity<String> registerClient( @RequestBody UserRequestDTO userRequest){
        User existUser = this.userService.findByUsername(userRequest.getUsername());
        if (existUser != null) {
            return new ResponseEntity<>(EMAIL_ALREADY_IN_USE, HttpStatus.BAD_REQUEST);
        }
        Client client=clientMapper.userRequestDtoToClient(userRequest);
        client.setRating(null);
        this.userService.registerClient(client);
        return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
    }

    @PostMapping("/clientAccountActivation")
    public ResponseEntity<String> activateClientAccount(@RequestBody VerificationDTO verificationDTO) {
        if(this.userService.activateAccount(verificationDTO.getEmail(), verificationDTO.getActivationCode()) != null){
            return new ResponseEntity<>("Successfully activated account!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad request!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePassword) {
        if(userDetailsService.changePassword(changePassword.getOldPassword(),changePassword.getNewPassword()))
             return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<>("No authentication manager set. can't change Password", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/saveDeleteAccountRequest")
    public ResponseEntity<String> saveDeleteAccountRequest( @RequestBody UserRequestDTO userRequest) {
        userService.saveDeleteAccountRequest(userRequest.getUsername(),userRequest.getReasonForDeleting());
        return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
    }
}