package edu.miu.payment.controller;

import edu.miu.payment.model.PaymentRequest;
import edu.miu.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    PaymentService paymentService;

    @PostMapping("/pay")
    public String makePayment(@RequestBody PaymentRequest request){
        return paymentService.processPayment(request);
    }

}
