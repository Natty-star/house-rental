package edu.miu.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    private PaymentType paymentType;

    private String routingNumber;

    private String bankAccount;

    private String ccNumber;

    private String CCV;

    private String payPalEmail;
}
