package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.constants.CustomBigDecimalDeserializer;
import com.africa.semiclon.capStoneProject.constants.CustomBigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    private Long userId;
    private int amount;
}
