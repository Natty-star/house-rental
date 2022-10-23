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
public class NotificationRequest {
    private String gustUserEmail;
    private String hostUserEmail;
    private String propertyName;
    private String propertyTitle;
    private String startDate;
    private String endDate;


}
