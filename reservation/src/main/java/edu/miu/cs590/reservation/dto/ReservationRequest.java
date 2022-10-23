package edu.miu.cs590.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {

    private String userEmail;
    private String propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
}
