package edu.miu.bank.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Bank {
    @Id
    private String id;
    private String email;
    private String propertyId;
    private String reservationId;
    private Double totalAmount;
    private PaymentMethod paymentMethod;


}
