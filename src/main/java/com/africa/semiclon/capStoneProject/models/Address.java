package com.africa.semiclon.capStoneProject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String streetName;
    private String city;
    private String zipCode;
    private String postalCode;
}
