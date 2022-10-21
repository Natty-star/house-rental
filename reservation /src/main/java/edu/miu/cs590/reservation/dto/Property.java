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
    private String userEmail;
    private double price;
    private String propertyName;
    private String propertyTitle;
    private boolean status;
}
