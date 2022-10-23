package edu.miu.cs590.reservation.controller;

import edu.miu.cs590.reservation.model.Reservation;
import edu.miu.cs590.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;
    private Reservation reservation;



    @BeforeEach
    void setUp() {
         reservation = Reservation.builder()
                .id("1")
                .propertyId("1")
                .userEmail("natty@gmail.com")
                .startDate(LocalDate.of(2022,10,20))
                .endDate(LocalDate.of(2022,10,24))
                .price(200)
                .build();
    }

    @Test
    void create() {
    }

    @Test
    void getReservationByUser() throws Exception {
        Mockito.when(reservationService.getByUser("natty@gmail.com")).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservation/natty@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}