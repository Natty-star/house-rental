package edu.miu.cs590.reservation.controller;

import edu.miu.cs590.reservation.config.KafkaTopic;
import edu.miu.cs590.reservation.dto.NotificationRequest;
import edu.miu.cs590.reservation.dto.ReservationRequest;
import edu.miu.cs590.reservation.model.Reservation;
import edu.miu.cs590.reservation.service.ReservationService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

//    @Autowired
//    KafkaTemplate<String,NotificationRequest> kafkaTemplate;
//
//    private static final String TOPIC = "natty";

    @PostMapping
    public String create(@RequestBody ReservationRequest reservationRequest){
        reservationService.create(reservationRequest);
        return "Successfully Reserved";
    }

    @GetMapping("/{userEmail}")
    public List<Reservation> getReservationByUser(@PathVariable String userEmail){
        return reservationService.getByUser(userEmail);
    }

//    @PostMapping("/publish")
//    public String testKafka(@RequestBody NotificationRequest notificationRequest){
//        kafkaTemplate.send(TOPIC,notificationRequest);
//        return "Published Successfully";
//    }




}
