package cs590.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cs590.accountservice.DTO.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import java.security.SecureRandom;
import java.util.List;


@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String id;

    private String firstName;

    private String lastName;
    @Indexed(unique = true)
    private String email;

    @Encrypted
    private String password;

    private List<Role> roles;
    private Address address;

    private PaymentType preferredPayment;


    private List<PaymentMethod> paymentMethods;

    public Account(String firstName, String lastName, String email, String password, List<Role> roles, Address address, PaymentType preferredPayment, List<PaymentMethod> paymentMethods) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.roles = roles;
        this.preferredPayment = preferredPayment;
        this.paymentMethods = paymentMethods;
    }

}
