package com.africa.semiclon.capStoneProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawRequest {
   private Long userId;
   private BigDecimal amount;
}
