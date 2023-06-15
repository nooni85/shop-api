package com.inca.tachyon.shop.config.security.configurer;

import com.inca.tachyon.shop.auth.filter.JwtAuthenticationFilter;
import com.inca.tachyon.shop.auth.filter.JwtVerificationFilter;
import com.inca.tachyon.shop.auth.handler.TachyonAuthenticationFailureHandler;
import com.inca.tachyon.shop.auth.handler.TachyonAuthenticationSuccessHandler;
import com.inca.tachyon.shop.auth.jwt.JwtTokenizer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TachyonShopFilterConfigurer extends AbstractHttpConfigurer<TachyonShopFilterConfigurer, HttpSecurity> {
    private JwtTokenizer jwtTokenizer;
    private PasswordEncoder passwordEncoder;

    public TachyonShopFilterConfigurer(JwtTokenizer jwtTokenizer, PasswordEncoder passwordEncoder) {
        this.jwtTokenizer = jwtTokenizer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        JwtAuthenticationFilter jwtAuthenticationFilter = JwtAuthenticationFilter.builder()
                .jwtTokenizer(jwtTokenizer)
                .passwordEncoder(passwordEncoder)
                .authenticationManager(authenticationManager)
                .build();
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new TachyonAuthenticationFailureHandler());
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new TachyonAuthenticationSuccessHandler());
        JwtVerificationFilter jwtVerificationFilter = JwtVerificationFilter.builder()
                .jwtTokenizer(jwtTokenizer)
                .build();
        builder.addFilter(jwtAuthenticationFilter)
               .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
}
