package com.africa.semiclon.capStoneProject.data.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long agentId;
    private String username;
    private String password;
    private String verificationToken;
    private Boolean verified;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @OneToOne
    private Address address;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeCreated;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeUpdated;

    @PrePersist
    private void setTimeCreated(){
        this.timeCreated= now();
    }
    @PreUpdate
    private void setTimeUpdated(){
        this.timeUpdated= now();
    }
}
