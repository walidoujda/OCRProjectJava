package com.rentals.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentals.api.models.Rental;

public interface RentalsRepository extends JpaRepository<Rental, Long> {

}
