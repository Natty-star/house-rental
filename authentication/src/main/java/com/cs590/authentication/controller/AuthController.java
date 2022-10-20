package com.cs590.authentication.controller;

import com.cs590.authentication.model.*;
import com.cs590.authentication.security.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        AuthenticationStatus status = authenticate(authRequest) ;

        if (!status.getIsAuthenticated()) {
            List<String> details = new ArrayList<>();
            details.add(status.getMessage());
            ErrorResponseDto error = new ErrorResponseDto(new Date(),
                    HttpStatus.UNAUTHORIZED.value(),
                    "UNAUTHORIZED", details, "uri");

            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(status.getData());

        final String token = jwtUtil.generateToken(json);

        return ResponseEntity.ok(new AutClientResponse(token));
    }

    private AuthenticationStatus authenticate(AuthRequest authRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> request = new HttpEntity<AuthRequest>(authRequest, headers);

        AuthResponse response = restTemplate.postForObject("http://localhost:8083/accounts/authenticate", request, AuthResponse.class);
        System.out.println(response);
        if (response == null) {
            return new AuthenticationStatus(false, "Missing Authorization Header", null);
        } else if (!response.getStatus()) {
            return new AuthenticationStatus(false, "Invalid Username/Password" , null);
        }

        return new AuthenticationStatus(true, "Authentication Successful", response);
    }


    @PostMapping("/validateUser")
    public ResponseEntity<?> isValid(@RequestBody TokenDto token) throws Exception {
        System.out.println(token);
        AuthResponse data = jwtUtil.validateToken(token.getToken());
        return ResponseEntity.ok(data);
    }

    @PostMapping("/authorizedUser")
    public ResponseEntity<?> isAuthorized(@RequestBody TokenDto token) throws Exception {
        System.out.println(token);
        Boolean response = jwtUtil.isAuthorizedToken(token.getToken());
        return ResponseEntity.ok(response);
    }

}
