package com.cs590.authentication.service;

import com.cs590.authentication.exception.JwtTokenMalformedException;
import com.cs590.authentication.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)

public class AuthServiceTest {
    @InjectMocks
    AuthService authService;

    @Mock
    private RestTemplate restTemplate;

    List<Role> roles = Arrays.asList(
            new Role("admin"),  new Role("guest")
    );

    AuthResponse mockAccount = new AuthResponse(true, "Selam", "yilma",  "selu@gmail.com",  roles);
    AuthenticationStatus status = new AuthenticationStatus(true, "Logged in successfully!", mockAccount);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "validity", 60);
        ReflectionTestUtils.setField(authService, "key", "TOP-SECRET");
    }


    @Test
    public void generateToken() {
        final String response = authService.generateToken("thisisfakesecret");
        Assertions.assertNotNull(response);
    }

    @Test
    public void validateToken() {
        Exception exception = Assertions.assertThrows(JwtTokenMalformedException.class, () -> {
            authService.validateToken("Bearer thisisfakesecret");
        });

        Assertions.assertTrue(exception.getMessage().contains("Invalid JWT token"));
    }

    @Test
    public void authorizedToken() {
        Exception exception = Assertions.assertThrows(JwtTokenMalformedException.class, () -> {
            authService.isAuthorizedToken("Bearer thisisfakesecret");
        });

        Assertions.assertTrue(exception.getMessage().contains("Invalid JWT token"));
    }


    @Test
    public void authenticate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AuthRequest authRequest = new AuthRequest("selam45@gmail.com", "1234");
        AuthResponse authResponse = new AuthResponse(true, mockAccount.getFirstName(), mockAccount.getLastName(), mockAccount.getEmail(), mockAccount.getRoles());
        HttpEntity<AuthRequest> request = new HttpEntity<AuthRequest>(authRequest, headers);
        Mockito.when(restTemplate.postForObject("http://account-service:8083/api/accounts/authenticate", request, AuthResponse.class)).thenReturn(authResponse);

        Assertions.assertTrue(authService.authenticate(authRequest).getIsAuthenticated());
    }
}
