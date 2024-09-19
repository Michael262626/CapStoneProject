package com.africa.semiclon.capStoneProject.data.models;

import com.africa.semiclon.capStoneProject.constants.CustomBigDecimalDeserializer;
import com.africa.semiclon.capStoneProject.constants.CustomBigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private Long userId;
    private Long adminId;
    private String reference;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String currency;
    private String ipAddress;
    private PricingPlanType planType;
    private Date createdOn;
    @OneToOne
    private Points points;
}

