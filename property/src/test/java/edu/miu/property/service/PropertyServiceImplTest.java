package edu.miu.property.service;

import miu.property.dto.ReservationResponse;
import miu.property.model.Address;
import miu.property.model.Property;
import miu.property.repository.PropertyRepo;
import miu.property.service.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PropertyServiceImplTest {

//    @Autowired

    private Address address;
    private ReservationResponse response;

    @MockBean
    private PropertyRepo propertyRepo;
    private PropertyServiceImpl propertyService;

//    private Property property;


    @BeforeEach
    void setUp() {
        Double[] location = new Double[2];
        location[0]= 10.0;
        location[1]= 9.0;
        List<String> images = new ArrayList<>();
        images.add("https://propertymanagmentportal.s3.us-east-2.amazonaws.com/1666284815248_jpeg");
        address = new Address("Fairfield","Iowa","52557","1000N");

        Property p = Property.builder()
                .id("63517d10125b6f60d0b2aa37")
                .propertyName("AB")
                .title("Hotel")
                .price(100.0)
                .status(true)
                .address(address)
                .userEmail("m@gmail.com")
                .location(location)
                .images(images)
                .build();

//        System.out.println(propertyRepo.findById("635184b8431b355613eccf47").get());
//        Mockito.when(propertyRepo.findById("63517d10125b6f60d0b2aa37").get())
//                .thenReturn(p);

    }

    @Test
    void getProperty() {
        Double[] location = new Double[2];
        location[0]= 10.0;
        location[1]= 9.0;
        List<String> images = new ArrayList<>();
        images.add("https://propertymanagmentportal.s3.us-east-2.amazonaws.com/1666284815248_jpeg");
        address = new Address("Fairfield","Iowa","52557","1000N");
        Property p = Property.builder()
                .id("63517d10125b6f60d0b2aa37")
                .propertyName("AB")
                .title("Hotel")
                .price(100.0)
                .status(true)
                .address(address)
                .userEmail("m@gmail.com")
                .location(location)
                .images(images)
                .build();

        String id = "63517d10125b6f60d0b2aa37";
         ReservationResponse reservationResponse = propertyService.getProperty(id);
        ReservationResponse response = ReservationResponse.builder()
                .propertyName("AB")
                .propertyTitle("Hotel")
                .price(100.0)
                .userEmail("m@gmail.com")
                .build();

        assertEquals(reservationResponse,response);
    }
}