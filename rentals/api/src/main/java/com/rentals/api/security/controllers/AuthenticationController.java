package com.rentals.api.security.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentals.api.models.ERole;
import com.rentals.api.models.User;
import com.rentals.api.repositories.UserRepository;
import com.rentals.api.security.config.JwtTokenUtil;
import com.rentals.api.security.model.MyUserDetails;
import com.rentals.api.security.request.LoginRequest;
import com.rentals.api.security.request.SignupRequest;
import com.rentals.api.security.response.JwtResponse;
import com.rentals.api.security.response.MessageResponse;
import com.rentals.api.security.response.UserInfoResponse;
import com.rentals.api.security.services.JwtUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
            throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateTokenFromUser(userDetails);
        ResponseCookie jwtCookie = jwtTokenUtil.generateJwtCookie(userDetails);

        List<String> roles = new ArrayList<String>();
        roles.add(ERole.ROLE_USER.name());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getEmail(),
                        userDetails.getEmail(),
                        roles));
        // return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody SignupRequest signUpRequest) throws Exception {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        String password = encoder.encode(signUpRequest.getPassword());
        User user = new User();

        user.setEmail(signUpRequest.getEmail());
        user.setPassword(password);
        user.setName(signUpRequest.getName());
        user.setCreated_at(new Date(System.currentTimeMillis()));

        return ResponseEntity.ok(userRepository.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String me(@CookieValue("rentals") String rentCookie)
            throws JsonProcessingException {
        String userName = jwtTokenUtil.getUserNameFromJwtToken(rentCookie);
        User user = userRepository.findByEmail(userName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }
}