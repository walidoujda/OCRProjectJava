package com.rentals.api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentals.api.models.User;
import com.rentals.api.repositories.UserRepository;
import com.rentals.api.security.config.JwtTokenUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UsersController {

    @Autowired
    JwtTokenUtil jwtUtils;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getRental(@PathVariable("id") Integer userId) {
        try {
            Optional<User> OptionalUser = userRepository.findById(userId);
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(OptionalUser.get());
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
