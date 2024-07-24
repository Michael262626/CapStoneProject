package com.africa.semiclon.capStoneProject.security.config;

import com.africa.semiclon.capStoneProject.security.filter.CustomAuthorizationFilter;
import com.africa.semiclon.capStoneProject.security.filter.CustomUsernameAndPasswordAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final CustomAuthorizationFilter authorizationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        var authenticationFilter = new CustomUsernameAndPasswordAuthFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth");
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(c->c.sessionCreationPolicy(STATELESS))
                .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter, CustomUsernameAndPasswordAuthFilter.class)
                .authorizeHttpRequests(c->c.requestMatchers(POST,"/api/v1/auth").permitAll()
                        .requestMatchers("/api/v1/waste").hasAuthority("USER"))
                .build();
    }
}
