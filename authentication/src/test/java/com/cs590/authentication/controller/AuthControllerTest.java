package com.cs590.authentication.controller;

import com.cs590.authentication.controller.AuthController;
import com.cs590.authentication.model.*;
import com.cs590.authentication.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthController.class)
@WithMockUser
public class AuthControllerTest {
    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    List<Role> roles = Arrays.asList(
            new Role("admin"),  new Role("guest")
    );

    AuthResponse mockAccount = new AuthResponse(true, "Selam", "yilma",  "selu@gmail.com",  roles);
    AuthenticationStatus  status = new AuthenticationStatus(true, "Logged in successfully!", mockAccount);

    @Test
    public void authenticate() throws Exception {
        AuthRequest login = new AuthRequest("selu@gmail.com", "1234");
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(login);
        AuthResponse authResponse = new AuthResponse(true, mockAccount.getFirstName(), mockAccount.getLastName(), mockAccount.getEmail(), mockAccount.getRoles());
        Mockito.when(authService.authenticate(Mockito.any(AuthRequest.class))).thenReturn(status);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/authentication/authenticate")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    public void validate() throws Exception {
        TokenDto token = new TokenDto("thisisfacketoken");
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(token);

        AuthResponse authResponse = new AuthResponse(true, mockAccount.getFirstName(), mockAccount.getLastName(), mockAccount.getEmail(), mockAccount.getRoles());
        Mockito.when(authService.validateToken(Mockito.anyString())).thenReturn(authResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/authentication/validateUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void isAuthorized() throws Exception {
        TokenDto token = new TokenDto("thisisfacketoken");
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(token);

        AuthResponse authResponse = new AuthResponse(true, mockAccount.getFirstName(), mockAccount.getLastName(), mockAccount.getEmail(), mockAccount.getRoles());
        Mockito.when(authService.isAuthorizedToken(Mockito.anyString())).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/authentication/authorizedUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
