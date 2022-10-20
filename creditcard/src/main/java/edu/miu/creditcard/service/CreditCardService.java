package edu.miu.creditcard.service;

import edu.miu.creditcard.entity.CreditCard;
import edu.miu.creditcard.repository.CreditCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreditCardService {
    public CreditCardService(CreditCardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Autowired
    CreditCardRepo cardRepo;

    public Mono<String> pay(CreditCard card){
        cardRepo.insert(card);
        System.out.println("Credit card service " + card);
        return Mono.just("Saved");
    }

}
