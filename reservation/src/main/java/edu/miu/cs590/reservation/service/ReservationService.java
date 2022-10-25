package edu.miu.cs590.reservation.service;

import edu.miu.cs590.reservation.dto.*;
import edu.miu.cs590.reservation.exceptionResponse.reservationException.PropertyAlReadyReserved;
import edu.miu.cs590.reservation.model.Reservation;
import edu.miu.cs590.reservation.model.ReservationStatus;
import edu.miu.cs590.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private WebClient.Builder webClient;

    @Autowired
    KafkaTemplate<String,NotificationRequest> kafkaTemplate;
    private static final String TOPIC = "notification";

    public String create(ReservationRequest reservationRequest){
        Property property = getProperty(reservationRequest.getPropertyId());

        if (!property.isStatus()){
            Reservation reservation = Reservation.builder()
                    .propertyId(reservationRequest.getPropertyId())
                    .userEmail(reservationRequest.getUserEmail())
                    .startDate(reservationRequest.getStartDate())
                    .endDate(reservationRequest.getEndDate())
                    .reservedAt(new Date())
                    .status(ReservationStatus.Pending)
                    .price(ChronoUnit.DAYS.between(reservationRequest.getStartDate(),reservationRequest.getEndDate()) * property.getPrice())
                    .build();

            //reserve with status pending
            Reservation newReservation = reservationRepository.save(reservation);

        //Prepare Payment Request
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .totalAmount(newReservation.getPrice())
                .reservationId(newReservation.getId())
                .paymentMethod(reservationRequest.getPaymentMethod())
                .paymentType(reservationRequest.getPaymentType())
                .propertyId(reservationRequest.getPropertyId())
                .email(reservationRequest.getUserEmail())
                .build();

        //send to payment
        Mono<String> paymentResp = payment(paymentRequest);
        paymentResp.subscribe();
       //log.info(paymentResp.block());

          if (paymentResp.block() != null){

              //update reservation status
              Reservation res = newReservation;
              res.setStatus(ReservationStatus.Reserved);
              reservationRepository.save(res);

            //Process property reservation
            Mono<String> propertyReserved = propertyReservation(reservationRequest.getPropertyId());
            propertyReserved.subscribe();
            log.info("Property reserved");


            //Prepare Notification Request
            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .gustUserEmail(reservationRequest.getUserEmail())
                    .hostUserEmail(property.getUserEmail())
                    .propertyName(property.getPropertyName())
                    .propertyTitle(property.getPropertyTitle())
                    .startDate(reservationRequest.getStartDate().toString())
                    .endDate(reservationRequest.getEndDate().toString())
                    .build();

            // messaging to kafka
            sendToKafka(notificationRequest);
            return "Successfully Reserved";
          }else {

              //update reservation status
              Reservation res = newReservation;
              res.setStatus(ReservationStatus.Cancelled);
              reservationRepository.save(res);

              log.error("Reservation Cancelled");
            throw new PropertyAlReadyReserved("Reservation Cancelled Due to payment");
        }


        }else {
            throw new PropertyAlReadyReserved("The property is already reserved");
            //log.info("The house already reserved");
            //return "The house already reserved";
        }

    }

    private Property getProperty(String propertyId){
        Property property = webClient.build().get()
                .uri("http://property-service:8085/api/property", uriBuilder -> uriBuilder.path("/{id}").build(propertyId))
                .retrieve()
                .bodyToMono(Property.class)
                .block();
        log.info("payment processed");
        return property;
    }
    private Mono<String> propertyReservation(String propertyId){
        ReservationStatusUpdate request = new ReservationStatusUpdate(propertyId);
        Mono<String> propertyReservationResponse = webClient.build()
                .post()
                .uri("http://property-service:8085/api/property/updateStatus")
                .body(Mono.just(request),ReservationStatusUpdate.class)
                .retrieve()
                .bodyToMono(String.class);
        log.info("Property reserved");
        return propertyReservationResponse;

    }

    private Mono<String> payment(PaymentRequest paymentRequest){
        System.out.println("In side the payment" + paymentRequest);
        Mono<String> paymentResponse = webClient.build().post()
                .uri("http://payment-service:8086/payments/pay")
                .body(Mono.just(paymentRequest), PaymentRequest.class)
                .retrieve()
                .bodyToMono(String.class);

        return paymentResponse;
    }

    private void sendToKafka(NotificationRequest notificationRequest){
        Message<NotificationRequest> message = MessageBuilder
                .withPayload(notificationRequest)
                .setHeader(KafkaHeaders.TOPIC, "notification")
                .build();
        kafkaTemplate.send(message);
        //kafkaTemplate.send(TOPIC,notificationRequest);
        log.info("Notification sent");

    }

    public List<Reservation> getByUser(String userEmail){
        return reservationRepository.findByUserEmail(userEmail);
    }

    public String checkout(String id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setStatus(ReservationStatus.Cancelled);
        return "Checked out";
    }
}
