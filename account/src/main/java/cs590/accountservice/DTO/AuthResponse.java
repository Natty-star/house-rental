package cs590.accountservice.DTO;

import cs590.accountservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private boolean status;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;

    public AuthResponse() {
    }
    //    private final String jwt;
}
