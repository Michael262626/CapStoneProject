package com.africa.semiclon.capStoneProject.data.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @OneToOne
    private Address address;
    private String phoneNumber;
    @Enumerated(value = STRING)
    @OneToMany
    private List<Transaction> transactions;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeCreated;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeUpdated;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

    @PrePersist
    private void setTimeCreated(){
        this.timeCreated= now();
    }
    @PreUpdate
    private void setTimeUpdated(){
        this.timeUpdated= now();
    }
}
