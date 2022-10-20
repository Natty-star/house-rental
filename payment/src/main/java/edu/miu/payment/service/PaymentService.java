package edu.miu.payment.service;


import edu.miu.payment.model.PaymentMethod;
import edu.miu.payment.model.PaymentRequest;
import edu.miu.payment.model.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    private WebClient.Builder webClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("#{${paymentmap}}")
    private Map<String, String> paymentMap;



    public String processPayment(PaymentRequest paymentRequest){
        PaymentMethod newPaymentMethod = new PaymentMethod();

        if(paymentRequest.getPaymentMethod() != null){
            // use the method from the request
            newPaymentMethod = paymentRequest.getPaymentMethod();

        } else if (paymentRequest.getPaymentMethod() == null) {
            if(paymentRequest.getPaymentType() != null){
                // call userService to get payment method
//                newPaymentMethod =
//                        restTemplate.getForObject(
//                        "localhost:8083/accounts/preferredPaymentMethod/" +
//                        paymentRequest.getEmail() + "/" +
//                        paymentRequest.getPaymentType(),
//                        PaymentMethod.class
//                );
                newPaymentMethod = getByType(paymentRequest.getPaymentType());
            }
            else {
                // call userService to get default payment method
//                newPaymentMethod =
//                restTemplate.getForObject(
//                            "localhost:8083/accounts/preferredPaymentMethod/" +
//                                 paymentRequest.getEmail(),
//                                 PaymentMethod.class
//                );
                newPaymentMethod = getByType(null);
            }
        }
        var uri = paymentMap.get(newPaymentMethod.getPaymentType().toString());
        paymentRequest.setPaymentMethod(newPaymentMethod);

//        String s = restTemplate.postForObject("http://localhost:"+uri,paymentRequest,String.class);
       Mono<String> s =
        this.webClient.build()
                .post()
                .uri("http://"+uri)
                .body(Mono.just(paymentRequest),PaymentRequest.class)
                .retrieve()
                .bodyToMono(String.class);
       s.subscribe(s1 -> {
           if(s1.equals("Saved")){
               log.info("Payment made by email {}, with reservation ID {}, and Payment Type {} was successful",
                       paymentRequest.getEmail(),
                       paymentRequest.getReservationId(),
                       paymentRequest.getPaymentMethod().getPaymentType()
               );
           }
           else {
               log.warn("Error has occurred with paymentType {}, user with email {}",
                       paymentRequest.getPaymentMethod().getPaymentType(),
                       paymentRequest.getEmail()
               );
           }
       });

       return "Payment in Process";

    }



    public PaymentMethod getByType(PaymentType paymentType){
        if(paymentType != null && paymentType.equals(PaymentType.CC)  ){
            return new PaymentMethod(
                     PaymentType.CC,
                    null,
                    null,
                    "CC123456789",
                    "324",
                    null
            );

        } else if (paymentType != null && paymentType.equals(PaymentType.PAYPAL)) {
            return new PaymentMethod(
                    PaymentType.PAYPAL,
                    null,
                    null,
                    null,
                    null,
                    "example@domain.com"
            );

        } else if (paymentType != null && paymentType.equals(PaymentType.BANK)) {
            return new PaymentMethod(
                    PaymentType.BANK,
                    "RoutingNumber_12334543",
                    "BankAccountNumber_37864582",
                    null,
                    null,
                    null
            );

        }else {
            return new PaymentMethod(
                    PaymentType.BANK,
                    "RoutingNumber_12334543",
                    "BankAccountNumber_37864582",
                    null,
                    null,
                    null
            );
        }
    }

}
