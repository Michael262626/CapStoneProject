package com.africa.semiclon.capStoneProject.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String streetName;
    private String city;
    private String zipCode;
//    private Agent agent
    private String postalCode;
}
