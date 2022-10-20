package com.cs590.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Boolean status;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
}
