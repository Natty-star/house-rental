package edu.miu.creditcard.controller;

import edu.miu.creditcard.entity.CreditCard;
import edu.miu.creditcard.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreditCardController {
    @Autowired
    CreditCardService creditCardService;

    @PostMapping()
    public String pay(@RequestBody CreditCard card){
        return creditCardService.pay(card);
    }

}
