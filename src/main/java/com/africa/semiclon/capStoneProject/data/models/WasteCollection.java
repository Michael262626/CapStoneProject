package com.africa.semiclon.capStoneProject.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
public class WasteCollection {
    @Id
    private Long id;
    @ManyToOne
    private User userId;
    @ManyToOne
    private Agent agentId;
    private Category wasteCategory;
    private Double wasteWeigh;
    @JsonSerialize
    private LocalDateTime dateAndTimeCollected;

    @PrePersist
    public void setDateAndTimeCollected(){
        this.dateAndTimeCollected = now();
    }
}
