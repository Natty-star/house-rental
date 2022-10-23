package edu.miu.cs590.reservation.service;

import edu.miu.cs590.reservation.model.Reservation;
import edu.miu.cs590.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @MockBean
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        Reservation reservation = Reservation.builder()
                .id("1")
                .propertyId("1")
                .userEmail("natty@gmail.com")
                .startDate(LocalDate.of(2022,10,20))
                .endDate(LocalDate.of(2022,10,24))
                .price(200)
                .build();
        Mockito.when(reservationRepository.findByUserEmail("natty@gmail.com")).thenReturn(List.of(reservation));
    }

    @Test
    void getByUser() {
        Reservation reservation = Reservation.builder()
                .id("1")
                .propertyId("1")
                .userEmail("natty@gmail.com")
                .startDate(LocalDate.of(2022,10,20))
                .endDate(LocalDate.of(2022,10,24))
                .price(200)
                .build();

        String userEmail = "natty@gmail.com";
        List<Reservation> reservation1 = reservationService.getByUser(userEmail);
        assertEquals(List.of(reservation) ,reservation1);

    }
}