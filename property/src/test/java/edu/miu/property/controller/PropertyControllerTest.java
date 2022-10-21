package edu.miu.property.controller;


import edu.miu.property.dto.ReservationResponse;
import edu.miu.property.service.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PropertyServiceImpl propertyService;
    private ReservationResponse reservationResponse;


    @BeforeEach
    void setUp() {
        reservationResponse = ReservationResponse.builder()
                .propertyName("AB")
                .propertyTitle("Hotel")
                .price(100)
                .userEmail("m@gmail.com")
                .build();
    }

    @Test
    void getProperty() throws Exception {
        Mockito.when(propertyService.getProperty("63517d10125b6f60d0b2aa37")).thenReturn(reservationResponse);
        mockMvc.perform(get("/api/property/63517d10125b6f60d0b2aa37")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() {
    }
}