package com.cs590.authentication.controller;

import com.cs590.authentication.controller.AuthController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthController.class)
@WithMockUser
public class AuthControllerTest {
}
