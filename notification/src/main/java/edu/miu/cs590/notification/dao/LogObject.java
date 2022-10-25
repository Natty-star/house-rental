package edu.miu.cs590.notification.dao;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
public class LogObject {
    String status;
    String email;
    String reservationId;
    String paymentType;

}

