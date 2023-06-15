package com.inca.tachyon.shop.auth.handler;

import com.inca.tachyon.shop.auth.userdetails.TachyonUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TachyonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        TachyonUserDetails tachyonUserDetails = (TachyonUserDetails) authentication.getPrincipal();

        log.info("User {} authentication successfully", tachyonUserDetails.getUsername());
    }
}
