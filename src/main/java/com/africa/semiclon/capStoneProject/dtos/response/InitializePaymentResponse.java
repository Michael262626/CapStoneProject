package com.africa.semiclon.capStoneProject.dtos.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InitializePaymentResponse {

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
        private String authorizationUrl;
        private String accessCode;
        private String reference;
        private String gatewayResponse;
        private String paidAt;
        private String createdAt;
        private String channel;
        private String currency;
        private String ipAddress;
    }
}
