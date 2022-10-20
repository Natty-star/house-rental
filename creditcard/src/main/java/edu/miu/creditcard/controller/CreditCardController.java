package edu.miu.creditcard.controller;

import edu.miu.creditcard.entity.CreditCard;
import edu.miu.creditcard.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class CreditCardController {
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Autowired
    CreditCardService creditCardService;

    @PostMapping()
    public Mono<String> pay(@RequestBody CreditCard card){
        return creditCardService.pay(card);
    }

}
