package com.rentals.api.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentals.api.models.Rental;
import com.rentals.api.models.User;
import com.rentals.api.repositories.RentalsRepository;
import com.rentals.api.repositories.UserRepository;
import com.rentals.api.security.config.JwtTokenUtil;
import com.rentals.api.services.FileStorageService;

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
