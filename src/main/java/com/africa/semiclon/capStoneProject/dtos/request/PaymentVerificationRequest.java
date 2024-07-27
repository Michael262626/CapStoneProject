package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Admin;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerificationRequest {

        @JsonProperty("admin_id")
        private Admin admin;
        @JsonProperty("user_id")
        private User user;
        @JsonProperty("waste_id")
        private Waste waste;
        @JsonProperty("reference")
        private String reference;

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("gateway_response")
        private String gatewayResponse;

        @JsonProperty("paid_at")
        private String paidAt;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("channel")
        private String channel;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("ip_address")
        private String ipAddress;

        @JsonProperty("pricing_plan_type")
        private String pricingPlanType;

        @JsonProperty("created_on")
        private Date createdOn = new Date();
}
