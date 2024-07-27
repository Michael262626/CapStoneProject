package com.africa.semiclon.capStoneProject.config;

import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.services.implemenation.PaystackServiceImpl;
import com.africa.semiclon.capStoneProject.services.interfaces.PaystackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class PayStackConfig {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${paystack.secret.api.key}")
    private String paystackSecretKey;

    @Bean
    public PaystackService paystackService() {
        return new PaystackServiceImpl(paystackSecretKey, userRepository, transactionRepository);
    }
}
