package com.brodband.service.Controllers;

import com.brodband.service.DTO.*;
import com.brodband.service.Models.User;
import com.brodband.service.Repositories.UserRepository;
import com.brodband.service.Services.UserService;
import com.brodband.service.Utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/Authenticate")
public class AuthController implements IAuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/SignIn")
    @Override
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequestDTO.getEmail(),
                            signInRequestDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new SignInResponseDTO(false,"Wrong credentials", null, ""), HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.loadUserByUsername(signInRequestDTO.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        final Optional<User> user = userRepository.findUserByEmail(signInRequestDTO.getEmail());
        User _user = user.get();
        return new ResponseEntity<>(new SignInResponseDTO(true,"Authenticated", new UserInfoDTO(
                _user.getUserId(),
                _user.getFirstName() + " "+ _user.getLastName(),
                _user.getEmail(),
                _user.getRole()
        ), token), HttpStatus.OK);
    }


    @PostMapping("/SignUp")
    @Override
    public ResponseEntity<BasicMessageDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        Optional<User> cUser = userRepository.findUserByEmail(signUpRequestDTO.getEmail());
        if(cUser.isPresent()) {
            return new ResponseEntity<>(new BasicMessageDTO(false, "User Already exists"), HttpStatus.BAD_REQUEST);
        }

        if(signUpRequestDTO.getRole().equalsIgnoreCase("admin") && !signUpRequestDTO.getMasterPassword().equals("India@123")) {
            return new ResponseEntity<>(new BasicMessageDTO(false, "Wrong Master Password"), HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setFirstName(signUpRequestDTO.getFirstName());
        newUser.setLastName(signUpRequestDTO.getLastName());
        newUser.setEmail(signUpRequestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        newUser.setRole(signUpRequestDTO.getRole());
        newUser.setInsertionDate(new Date());
        newUser.setIsActive(true);
        userRepository.save(newUser);
        return new ResponseEntity<>(new BasicMessageDTO(true, "User created"), HttpStatus.CREATED);
    }
}
