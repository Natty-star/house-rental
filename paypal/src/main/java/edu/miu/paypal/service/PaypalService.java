package edu.miu.paypal.service;

import edu.miu.paypal.entity.Paypal;
import edu.miu.paypal.repository.PaypalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaypalService {
    public PaypalService(PaypalRepo paypalRepo) {
        this.paypalRepo = paypalRepo;
    }

    @Autowired
    PaypalRepo paypalRepo;

    public String pay(Paypal paypal){
        paypalRepo.insert(paypal);
        System.out.println("Paypal service" + paypal);
        return "Saved";
    }

}
