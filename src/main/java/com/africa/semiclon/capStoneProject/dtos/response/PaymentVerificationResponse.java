package com.africa.semiclon.capStoneProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentVerificationResponse {

    @JsonProperty("_id")
    private Long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty("_id")
        private Long id;

        @JsonProperty("status")
        private String status;

        @JsonProperty("reference")
        private String reference;

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("gateway_response")
        private String gatewayResponse;

        @JsonProperty("paid_at")
        private String paidAt;

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime createdOn;

        @JsonProperty("channel")
        private String channel;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("ip_address")
        private String ipAddress;

        @JsonProperty("pricing_plan_type")
        private String pricingPlanType;

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime createdAt;

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime updatedAt;

        @PrePersist
        private void setTimeCreated() {
            this.createdAt = now();
        }

        @PreUpdate
        private void setTimeUpdated() {
            this.updatedAt = now();
        }
    }
}
