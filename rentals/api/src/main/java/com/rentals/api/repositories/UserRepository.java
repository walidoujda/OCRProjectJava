package com.rentals.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.rentals.api.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}