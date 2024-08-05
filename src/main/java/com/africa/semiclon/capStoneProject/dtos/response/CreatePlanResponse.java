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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePlanResponse {

    private Boolean status;
    private String message;
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty("name")
        private String name;

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("interval")
        private String interval;

        @JsonProperty("integration")
        private String integration;

        @JsonProperty("plan_code")
        private String planCode;

        @JsonProperty("send_invoices")
        private String sendInvoices;

        @JsonProperty("send_sms")
        private String sendSms;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("id")
        private Long id;

        @JsonProperty("time")
        private Date createdAt;

        @JsonProperty("time")
        private Date updatedAt;

    }
}
