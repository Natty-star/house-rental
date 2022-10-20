package edu.miu.paypal.service;

import edu.miu.paypal.entity.Paypal;
import edu.miu.paypal.repository.PaypalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaypalService {
    @Autowired
    PaypalRepo cardRepo;

    public String pay(Paypal paypal){
        cardRepo.insert(paypal);
        System.out.println("Paypal service" + paypal);
        return "Saved";
    }

}
