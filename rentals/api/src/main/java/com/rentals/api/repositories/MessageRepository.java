package com.rentals.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentals.api.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
