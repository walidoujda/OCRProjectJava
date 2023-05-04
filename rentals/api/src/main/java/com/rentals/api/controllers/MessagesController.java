package com.rentals.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentals.api.models.Message;
import com.rentals.api.repositories.MessageRepository;
import com.rentals.api.request.MessagesRequest;
import com.rentals.api.response.MessageResponse;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/messages/")
public class MessagesController {
    @Autowired
    MessageRepository messageRepository;

    @PostMapping
    public ResponseEntity<?> addMessage(@Valid @RequestBody MessagesRequest messageRequest) {

        try {
            Message message = new Message();
            message.setId(null);
            message.setRental_id(messageRequest.getRental_id());
            message.setUser_id(messageRequest.getUser_id());
            message.setMessage(messageRequest.getMessage());
            messageRepository.save(message);
            ObjectMapper mapper = new ObjectMapper();
            MessageResponse messageResponse = new MessageResponse(message.getMessage());
            return ResponseEntity.ok().body(mapper.writeValueAsString(messageResponse));

        } catch (Exception e) {
            return ResponseEntity.ok().body("User Content." + e.getMessage());
            // e.printStackTrace();
        }

    }
}
