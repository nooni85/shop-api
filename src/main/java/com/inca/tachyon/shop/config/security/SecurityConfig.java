package com.inca.tachyon.shop.config.security;

import com.inca.tachyon.shop.auth.jwt.JwtTokenizer;
import com.inca.tachyon.shop.config.security.configurer.TachyonShopFilterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 보안 설정 파일
 * @author Sangwoo Joo
 */
@Configuration
public class SecurityConfig {
    private JwtTokenizer jwtTokenizer;

    private CorsConfig corsConfig;

    public SecurityConfig(JwtTokenizer jwtTokenizer, CorsConfig corsConfig) {
        this.jwtTokenizer = jwtTokenizer;
        this.corsConfig = corsConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .addFilter(this.corsConfig.corsFilter())
            .csrf().disable()
            .cors(withDefaults())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .apply(new TachyonShopFilterConfigurer(jwtTokenizer, passwordEncoder()))
            .and()
                .authorizeHttpRequests(authorize ->
                    authorize.anyRequest().permitAll()
                );
        return http.build();
    }
}
