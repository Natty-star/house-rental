package com.cs590.authentication.service;

import com.cs590.authentication.exception.JwtTokenIncorrectStructureException;
import com.cs590.authentication.exception.JwtTokenMalformedException;
import com.cs590.authentication.exception.JwtTokenMissingException;
import com.cs590.authentication.model.AuthResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Value("${secret}")
    private String key;

    @Value("${validity}")
    private Integer validity;

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
                throw new JwtTokenIncorrectStructureException("Incorrect Authentication Structure");
            }

            Jws<Claims> result = Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(parts[1]);

            AuthResponse user = new ObjectMapper().readValue(result.getBody().getSubject(), AuthResponse.class);

            return user;

        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean isAuthorizedToken(final String header) throws JwtTokenMalformedException, JwtTokenMissingException {
        AuthResponse response = validateToken(header);
        return response.getRoles().stream().anyMatch(role -> role.getName().equals("admin"));
    }

}
