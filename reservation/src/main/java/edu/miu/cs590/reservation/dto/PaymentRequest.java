package edu.miu.cs590.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
//    private String reservationId;
//    private String userEmail;
//    private String propertyId;
//    private double paymentAmount;
//    private PaymentType paymentType;
//    private PaymentMethod paymentMethod;

    private String email;
    private String propertyId;
    private String reservationId;
    private Double totalAmount;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
}
