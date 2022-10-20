package edu.miu.cs590.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    private double price;
    private String propertyName;
    private PaymentType propertyTitle;
    private LocalDate startDate;
    private LocalDate  endDate;
}
