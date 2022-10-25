package edu.miu.cs590.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document("reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    @Id
    private String id;
    private String userEmail;
    private String propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private Date reservedAt;
    private ReservationStatus status;
}
