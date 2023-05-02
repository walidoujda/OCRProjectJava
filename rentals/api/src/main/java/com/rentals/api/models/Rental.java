package com.rentals.api.models;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

import org.hibernate.annotations.CacheConcurrencyStrategy;

//To use the @Data annotation you should add the Lombok dependency.
@Entity
@Table(name = "rentals")
@Data
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "rentalCache")
public class Rental {
    public Rental() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surface", nullable = false)
    private Double surface;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "owner_id", nullable = false)
    private Long owner_id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

}
