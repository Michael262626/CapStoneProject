package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RegisterWasteRequest {
    private Category type;
    private String quantity;
    private BigDecimal price;
    private String description;
    private Long agentId;
}
