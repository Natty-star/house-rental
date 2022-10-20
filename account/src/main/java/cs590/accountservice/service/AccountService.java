package cs590.accountservice.service;

import cs590.accountservice.entity.*;
import java.util.List;

public interface AccountService {
    public Account createAccount(Account account);
    public Account findByEmail(String email);
    public List<Account> findAll() ;
    public Account updateAccount(String email, Account newAccount) ;
    public boolean deleteAccount(String email);
    public Account getAccount(String email, String password);
    public List<PaymentMethod> addPaymentMethod(String email, PaymentMethod newMethod);
    public Address updateAddress(String email, Address address);
    public Address getAddress(String email);

    public String getPreferredPayment(String email) ;

    public Boolean updatePreferredPayment(String email, PaymentType paymentType) ;

    public PaymentMethod getPaymentDetail(String email, PaymentType paymentType);

}