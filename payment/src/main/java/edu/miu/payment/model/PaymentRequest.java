package edu.miu.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private String email;
    private String propertyId;
    private String reservationId;
    private Double totalAmount;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
}
