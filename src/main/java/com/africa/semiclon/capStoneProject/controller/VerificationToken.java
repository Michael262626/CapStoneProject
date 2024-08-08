//package com.africa.semiclon.capStoneProject.controller;
//
//import com.africa.semiclon.capStoneProject.data.models.Agent;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.Calendar;
//import java.util.Date;
//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//public class VerificationToken {
//
//    public VerificationToken(String token, Agent agent) {
//        super();
//        this.token = token;
//        this.agent = agent;
//        this.expirationDateAndTime = this.getTokenExpirationDateAndTime();
//    }
//
//    public VerificationToken(String token) {
//        super();
//        this.token = token;
//        this.expirationDateAndTime = this.getTokenExpirationDateAndTime();
//    }
//
//    public Date getTokenExpirationDateAndTime() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(new Date().getTime());
//        calendar.add(Calendar.MINUTE,EXPIRATION_TIME);
//        return new Date(calendar.getTime().getTime());
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String token;
//    private static final int EXPIRATION_TIME = 15;
//    private Date expirationDateAndTime;
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private Agent agent;
//
//
//
//
//}
