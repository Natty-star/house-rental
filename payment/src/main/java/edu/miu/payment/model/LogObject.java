package edu.miu.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogObject {
    String status;
    String email;
    String reservationId;
    String paymentType;

}
