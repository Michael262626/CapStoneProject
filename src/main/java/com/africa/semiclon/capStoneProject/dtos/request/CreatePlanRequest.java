package com.africa.semiclon.capStoneProject.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanRequest {

    private Long id;

    @NotNull(message = "Plan name cannot be null")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Interval cannot be null")
    @JsonProperty("interval")
    private String interval;

    @NotNull(message = "Amount cannot be null")
    @JsonProperty("amount")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @JsonProperty("email")
    private String email;
}
