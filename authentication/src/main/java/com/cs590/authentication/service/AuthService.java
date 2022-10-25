package com.cs590.authentication.service;

import com.cs590.authentication.exception.JwtTokenIncorrectStructureException;
import com.cs590.authentication.exception.JwtTokenMalformedException;
import com.cs590.authentication.exception.JwtTokenMissingException;
import com.cs590.authentication.model.AuthRequest;
import com.cs590.authentication.model.AuthResponse;
import com.cs590.authentication.model.AuthenticationStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class AuthService {

    @Value("${secret}")
    private String key;

    @Value("${validity}")
    private Integer validity;

    @Autowired
    private RestTemplate restTemplate;

    public String generateToken(String data) {
        Claims claims = Jwts.claims().setSubject(data);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + validity * 1000 * 60 * 10;
        Date exp = new Date(expMillis);

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, key).compact();

    }

    public AuthResponse validateToken(final String header) throws JwtTokenMalformedException, JwtTokenMissingException {

        try {
            String[] parts = header.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                log.error("Invalid header: Incorrect Authentication Structure at:" + LocalDateTime.now());
                throw new JwtTokenIncorrectStructureException("Incorrect Authentication Structure");
            }

            Jws<Claims> result = Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(parts[1]);

            AuthResponse user = new ObjectMapper().readValue(result.getBody().getSubject(), AuthResponse.class);

            return user;

        } catch (SignatureException ex) {
            log.error("Json parser failed: Invalid JWT signature at:" + LocalDateTime.now());
            throw new JwtTokenMalformedException("Invalid JWT signature at:" + LocalDateTime.now());
        } catch (MalformedJwtException ex) {
            log.error("Json parser failed: Invalid JWT token");
            throw new JwtTokenMalformedException("Invalid JWT token at:" + LocalDateTime.now());
        } catch (ExpiredJwtException ex) {
            log.error("Json parser failed: Expired JWT token");
            throw new JwtTokenMalformedException("Expired JWT token at:" + LocalDateTime.now());
        } catch (UnsupportedJwtException ex) {
            log.error("Json parser failed: Unsupported JWT token");
            throw new JwtTokenMalformedException("Unsupported JWT token at:" + LocalDateTime.now());
        } catch (IllegalArgumentException ex) {
            log.error("Json parser failed: JWT claims string is empty");
            throw new JwtTokenMissingException("JWT claims string is empty at:" + LocalDateTime.now());
        } catch (JsonMappingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Boolean isAuthorizedToken(final String header) throws JwtTokenMalformedException, JwtTokenMissingException {
        AuthResponse response = validateToken(header);
        return response.getRoles().stream().anyMatch(role -> role.getName().equals("admin"));
    }

    public AuthenticationStatus authenticate(AuthRequest authRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> request = new HttpEntity<AuthRequest>(authRequest, headers);

        AuthResponse response = restTemplate.postForObject("http://account-service:8083/api/accounts/authenticate", request, AuthResponse.class);
        System.out.println(response);
        if (response == null) {
            log.error("Missing Authorization Header at:" + LocalDateTime.now());
            return new AuthenticationStatus(false, "Missing Authorization Header", null);
        } else if (!response.getStatus()) {
            log.error("Invalid Username/Password at:" + LocalDateTime.now());
            return new AuthenticationStatus(false, "Invalid Username/Password" , null);
        }

        return new AuthenticationStatus(true, "Authentication Successful", response);
    }

}
