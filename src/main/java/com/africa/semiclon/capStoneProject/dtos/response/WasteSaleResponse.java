package com.africa.semiclon.capStoneProject.dtos.response;

import com.africa.semiclon.capStoneProject.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class WasteSaleResponse {
    private Category wasteType;
    private BigDecimal salePrice;
    private LocalDateTime saleDate;
}
