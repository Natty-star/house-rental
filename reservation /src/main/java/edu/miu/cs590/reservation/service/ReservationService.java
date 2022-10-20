package edu.miu.cs590.reservation.service;

import edu.miu.cs590.reservation.dto.NotificationRequest;
import edu.miu.cs590.reservation.dto.PaymentRequest;
import edu.miu.cs590.reservation.dto.Property;
import edu.miu.cs590.reservation.dto.ReservationRequest;
import edu.miu.cs590.reservation.model.Reservation;
import edu.miu.cs590.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    private static final String TOPIC = "reservation";
    @Autowired
    private WebClient.Builder webClient;

    @Autowired
    KafkaTemplate<String,NotificationRequest> kafkaTemplate;

    public void create(ReservationRequest reservationRequest){
        Property property = getProperty(reservationRequest.getPropertyId());
//        Property property = webClient.build().get()
//                .uri("http://localhost:8085/api/product", uriBuilder -> uriBuilder.path("/{id}").build(reservationRequest.getPropertyId()))
//                .retrieve()
//                .bodyToMono(Property.class)
//                .block();

        Reservation reservation = Reservation.builder()
                .propertyId(reservationRequest.getPropertyId())
                .userEmail(reservationRequest.getUserEmail())
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .price(ChronoUnit.DAYS.between(reservationRequest.getStartDate(),reservationRequest.getEndDate()) * 10)
                .build();

        Reservation newReservation = reservationRepository.save(reservation);

        //Process property reservation
        propertyReservation(property.getPropertyTitle());




//        PaymentRequest paymentRequest = PaymentRequest.builder()
//                .paymentAmount(newReservation.getPrice())
//                .reservationId(newReservation.getId())
//                .paymentMethod(reservationRequest.getPaymentMethod())
//                .paymentType(reservationRequest.getPaymentType())
//                .propertyId(reservationRequest.getPropertyId())
//                .userEmail(reservationRequest.getUserEmail())
//                .build();
//
//        String paymentResp = payment(paymentRequest);
//        log.info(paymentResp);
//

//        NotificationRequest notificationRequest = NotificationRequest.builder()
//                .hostUserEmail(reservationRequest.getUserEmail())
//                .guestUserEmail(property.getUserEmail())
//                .propertyTitle(property.getPropertyTitle())
//                .propertyName(property.getPropertyName())
//                .startDate(reservationRequest.getStartDate())
//                .endDate(reservationRequest.getEndDate())
//                .build();

        //messaging to kafka
//        sendToKafka(notificationRequest);


    }

    private Property getProperty(String propertyId){
        Property property = webClient.build().get()
                .uri("http://localhost:8085/api/product", uriBuilder -> uriBuilder.path("/{id}").build(propertyId))
                .retrieve()
                .bodyToMono(Property.class)
                .block();
        return property;

    }
    private Mono<String> propertyReservation(String propertyId){
        Mono<String> propertyReservationResponse = webClient.build()
                .post()
                .uri("http://localhost:8085/api/product")
                .body(propertyId,String.class)
                .retrieve()
                .bodyToMono(String.class);
        log.info("Property reserved");
        return propertyReservationResponse;

    }

    private Mono<String> payment(PaymentRequest paymentRequest){
        Mono<String> paymentResponse = webClient.build().post()
                .uri("http://localhost:8086/payment")
                .body(paymentRequest, PaymentRequest.class)
                .retrieve()
                .bodyToMono(String.class);

        return paymentResponse;
    }

    private void sendToKafka(NotificationRequest notificationRequest){
        kafkaTemplate.send(TOPIC,notificationRequest);
        log.info("Notification sent");
        //implement
    }

    public List<Reservation> getByUser(String userEmail){
        return reservationRepository.findByUserEmail(userEmail);
    }
}
