package edu.miu.cs590.reservation.dto;

import jdk.jfr.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethod {
    private PaymentType paymentType;
    private String routingNumber;
    private String bankAccount;
    private String ccNumber;
    private String CCV;
    private String payPalEmail;
}
