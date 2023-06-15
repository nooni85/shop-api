package com.inca.tachyon.shop.auth.filter;

import com.google.gson.Gson;
import com.inca.tachyon.shop.auth.entity.Roleable;
import com.inca.tachyon.shop.auth.jwt.JwtTokenizer;
import com.inca.tachyon.shop.exception.ExceptionCode;
import com.inca.tachyon.shop.exception.TachyonShopException;
import com.inca.tachyon.shop.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    private void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
//        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Map<String, Object> claims = verifyJws(request);
        setAuthenticationToContext(claims);
//        try {
//            Map<String, Object> claims = verifyJws(request);
//            setAuthenticationToContext(claims);
//        } catch (SignatureException se) {
//            response.setHeader("exception", se.toString());
//        } catch (ExpiredJwtException ee) {
//            response.setHeader("exception", ee.toString());
//        } catch (Exception e) {
//            response.setHeader("exception", e.toString());
//        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = Optional.ofNullable(((List<Roleable>)claims.get("roles")))
                .stream().map(roleable -> new SimpleGrantedAuthority("ROLE_"+roleable))
                .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}