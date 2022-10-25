package edu.miu.cs590.reservation.controller;


import edu.miu.cs590.reservation.dto.ReservationRequest;
import edu.miu.cs590.reservation.model.Reservation;
import edu.miu.cs590.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;


    //create reservation
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationRequest reservationRequest){
         String resp = reservationService.create(reservationRequest);
         return  new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{userEmail}")
    public List<Reservation> getReservationByUser(@PathVariable String userEmail){
        return reservationService.getByUser(userEmail);
    }

    @PostMapping("/checkout/{id}")
    public String checkout(@PathVariable String id){
        return reservationService.checkout(id);
    }





}
