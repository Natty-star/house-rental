package miu.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse implements Serializable {
    private String propertyName;
    private String propertyTitle;
    private double price;
    private String userEmail;
    private boolean status;
}
