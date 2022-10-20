package edu.miu.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMethod {
    private PaymentType paymentType;

    private String routingNumber;

    private String bankAccount;

    private String ccNumber;

    private String CCV;

    private String payPalEmail;
}
