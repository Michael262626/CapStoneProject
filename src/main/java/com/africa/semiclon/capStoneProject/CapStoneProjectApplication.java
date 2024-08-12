package com.africa.semiclon.capStoneProject;

import com.africa.semiclon.capStoneProject.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class CapStoneProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CapStoneProjectApplication.class, args);
    }
}