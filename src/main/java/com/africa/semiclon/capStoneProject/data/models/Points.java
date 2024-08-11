package com.africa.semiclon.capStoneProject.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
public class Points {
    @Id
    private Long UserId;
    private BigDecimal totalPoints;
    private BigDecimal pointEarned;
    private BigDecimal pointRedeemed;

}
