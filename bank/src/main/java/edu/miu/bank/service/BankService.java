package edu.miu.bank.service;

import edu.miu.bank.entity.Bank;
import edu.miu.bank.repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {
    public BankService(BankRepo bankRepo) {
        this.bankRepo = bankRepo;
    }

    @Autowired
    BankRepo bankRepo;

    public String pay(Bank bank){
        bankRepo.insert(bank);
        System.out.println("Bank service" + bank);
        return "Saved";
    }

}
