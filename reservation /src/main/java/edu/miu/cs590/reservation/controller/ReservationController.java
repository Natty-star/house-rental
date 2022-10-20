package edu.miu.cs590.reservation.controller;

import edu.miu.cs590.reservation.dto.ReservationRequest;
import edu.miu.cs590.reservation.model.Reservation;
import edu.miu.cs590.reservation.service.ReservationService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public String create(@RequestBody ReservationRequest reservationRequest){
        reservationService.create(reservationRequest);
        return "Successfully Created";
    }

    @GetMapping("/{userEmail}")
    public List<Reservation> getReservationByUser(@PathVariable String userEmail){
        return reservationService.getByUser(userEmail);
    }


}
