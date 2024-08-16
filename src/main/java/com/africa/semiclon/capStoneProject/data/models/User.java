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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Waste> wastes = new ArrayList<>();
//    @Column(name = "total_weight_collected", nullable = false)
//    private Integer totalWeightCollected;
    private String phoneNumber;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentPayStack> paymentPayStacks = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeCreated;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeUpdated;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;
    @OneToOne
    private Points points;

    @PrePersist
    private void setTimeCreated(){
        this.timeCreated= now();
    }
    @PreUpdate
    private void setTimeUpdated(){
        this.timeUpdated= now();
    }

    public void addWaste(Waste waste) {
        this.wastes.add(waste);
    }
}
