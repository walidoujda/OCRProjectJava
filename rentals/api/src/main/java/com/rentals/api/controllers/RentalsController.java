package com.rentals.api.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.rentals.api.response.MessageResponse;
import com.rentals.api.security.config.JwtTokenUtil;
import com.rentals.api.services.FileStorageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/rentals")
public class RentalsController {
    @Autowired
    JwtTokenUtil jwtUtils;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    RentalsRepository rentalsRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String createRental(
            @CookieValue("rentals") String rentCookie,
            @PathVariable("id") Long rentalId, @RequestParam("picture") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description) {

        try {
            String userName = jwtUtils.getUserNameFromJwtToken(rentCookie);
            User user = userRepository.findByEmail(userName);
            String fileName = fileStorageService.storeFile(file);
            Rental rental = new Rental();
            rental.setId(rentalId);
            rental.setDescription(description);
            rental.setName(fileName);
            rental.setPicture("http://localhost:3001/uploads/" + fileName);
            rental.setPrice(price);
            rental.setSurface(surface);
            rental.setOwner_id(user.getId());
            rental.setCreated_at(new Date(System.currentTimeMillis()));
            rentalsRepository.save(rental);
            return fileName;
        } catch (Exception e) {
            return "User Content." + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getRental(@PathVariable("id") Long rentalId) {
        try {
            Optional<Rental> OptionalRentl = rentalsRepository.findById(rentalId);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(OptionalRentl.get());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getAllRentals() {
        try {
            List<Rental> rentals = rentalsRepository.findAll();
            ObjectMapper mapper = new ObjectMapper();
            return "{ \n  \"rentals\":" + mapper.writeValueAsString(rentals) + "}";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> updateRental(
            @CookieValue("rentals") String rentCookie,
            @PathVariable("id") Long rentalId,
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description) {
        try {
            String userName = jwtUtils.getUserNameFromJwtToken(rentCookie);
            User user = userRepository.findByEmail(userName);
            Rental rental = rentalsRepository.findById(rentalId).get();
            rental.setId(rentalId);
            rental.setDescription(description);
            rental.setPrice(price);
            rental.setSurface(surface);
            rental.setOwner_id(user.getId());
            rental.setUpdated_at(new Date(System.currentTimeMillis()));
            rentalsRepository.save(rental);
            ObjectMapper mapper = new ObjectMapper();
            MessageResponse messageResponse = new MessageResponse("success");
            return ResponseEntity.ok().body(mapper.writeValueAsString(messageResponse));
        } catch (Exception e) {
            return ResponseEntity.ok().body("Error : " + e.getMessage());
        }

    }
}
