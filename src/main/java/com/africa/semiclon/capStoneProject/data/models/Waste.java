package com.africa.semiclon.capStoneProject.data.models;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.data.models.User;
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

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
@ToString
@Table(name = "waste")
public class Waste {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long wasteId;
    private Long userId;
    private String url;
//    private Long agentId;
    @Enumerated(value = STRING)
    private Category type;
    private BigDecimal price;
    private String quantity;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeCreated;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeUpdated;
    @ManyToOne
    private User uploader;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime wasteCollectionDate;
    @OneToOne
    private Agent agent;
    private String description;

    @PrePersist
    private void setTimeCreated(){
        this.timeCreated= now();
    }

    @PreUpdate
    private void setTimeUpdated(){
        this.timeUpdated= now();
    }
}
