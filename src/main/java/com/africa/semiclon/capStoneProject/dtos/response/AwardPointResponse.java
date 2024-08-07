package com.africa.semiclon.capStoneProject.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AwardPointResponse {
    private String message;
    private BigDecimal awardedPoints;
    private BigDecimal totalPoints;
}
