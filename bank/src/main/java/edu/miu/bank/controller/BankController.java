package edu.miu.bank.controller;

import edu.miu.bank.entity.Bank;
import edu.miu.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BankController {
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @Autowired
    BankService bankService;

    @PostMapping
    public Mono<String> pay(@RequestBody Bank bank){
        return bankService.pay(bank);
    }

}
