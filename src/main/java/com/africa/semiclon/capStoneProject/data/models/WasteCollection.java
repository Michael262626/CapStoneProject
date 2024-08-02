package com.africa.semiclon.capStoneProject.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime dateAndTimeCollected;
}
