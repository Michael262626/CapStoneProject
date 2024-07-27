package com.africa.semiclon.capStoneProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePlanResponse {

    private boolean status;
    private String message;
    private Data data;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Data {
        private String name;
        private String plan_code;
        private String description;
        private BigDecimal amount;
        private String interval;
        @JsonProperty("reference")
        private String reference;
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
    }
}
