package com.cs590.authentication.controller;

import com.cs590.authentication.model.*;
import com.cs590.authentication.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        AuthenticationStatus status = authService.authenticate(authRequest) ;

        if (!status.getIsAuthenticated()) {
            List<String> details = new ArrayList<>();
            details.add(status.getMessage());
            ErrorResponseDto error = new ErrorResponseDto(new Date(),
                    HttpStatus.UNAUTHORIZED.value(),
                    "UNAUTHORIZED", details, "uri");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(status.getData());

        final String token = authService.generateToken(json);

        return ResponseEntity.ok(new AutClientResponse(token));
    }


    @PostMapping("/validateUser")
    public ResponseEntity<?> isValid(@RequestBody TokenDto token) throws Exception {
        System.out.println(token);
        AuthResponse data = authService.validateToken(token.getToken());
        return ResponseEntity.ok(data);
    }

    @PostMapping("/authorizedUser")
    public ResponseEntity<?> isAuthorized(@RequestBody TokenDto token) throws Exception {
        System.out.println(token);
        Boolean response = authService.isAuthorizedToken(token.getToken());
        return ResponseEntity.ok(response);
    }

}
