package cs590.accountservice.commandRunner;

import cs590.accountservice.entity.*;
import cs590.accountservice.repository.AccountRepository;
import cs590.accountservice.repository.AddressRepository;
import cs590.accountservice.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public void run(String... args) throws Exception {
        Address address = new Address("US","IA","FAIRFIELD", "52557", "1000 N Street");
        Account account = new Account("Selam", "yilma",  "selu@gmail.com", "1234", null, address, PaymentType.BANK);


        PaymentMethod method = new PaymentMethod(
                PaymentType.BANK, "R120000", "BA-1234445555",null, null, null);

        PaymentMethod method2 = new PaymentMethod(
                PaymentType.PAYPAL,null,null,null,null,"PP1234445555");

        List<PaymentMethod> methods = Arrays.asList(method, method2); //account.getPaymentMethods();
        account.setPaymentMethods(methods);

        List<Role> roles = Arrays.asList(
                new Role("admin"),  new Role("guest")
        );

        account.setRoles(roles);

//        accountRepository.save(account);
    }
}
