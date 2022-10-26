package cs590.accountservice.service.Imp;

import cs590.accountservice.DTO.AuthResponse;
import cs590.accountservice.entity.Account;
import cs590.accountservice.entity.Address;
import cs590.accountservice.entity.PaymentMethod;
import cs590.accountservice.entity.PaymentType;
import cs590.accountservice.repository.AccountRepository;
import cs590.accountservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImp implements AccountService {

    PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public AccountServiceImp(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Account createAccount(Account account) {

        try {
            String encodedPassword = this.passwordEncoder.encode(account.getPassword());
            account.setPassword(encodedPassword);
            return accountRepository.save(account);
        } catch (Exception e){
            log.error("User already exists with this email address: " + LocalDateTime.now());
            throw new RuntimeException("User already exists with this email address: " + account.getEmail());
        }

    }

    public Account findByEmail(String email) {
        Account account = accountRepository.findAccountByEmail(email);
        return account;
    }
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    public Account updateAccount(String email, Account newAccount) {

        String encodedPassword = this.passwordEncoder.encode(newAccount.getPassword());

        Query query = new Query().addCriteria(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("firstName", newAccount.getFirstName());
        update.set("lastName", newAccount.getLastName());
//        update.set("password", encodedPassword);
        update.set("address", newAccount.getAddress());
        update.set("roles", newAccount.getRoles());
        update.set("preferredPayment", newAccount.getPreferredPayment());
        update.set("paymentMethods", newAccount.getPaymentMethods());

        return  mongoTemplate.findAndModify(query, update , Account.class);
    }

    public Account getAccount(String email, String password) {
//        Account account = accountRepository.findAccountByEmailAndPassword(email, password);
        Account account = accountRepository.findAccountByEmail(email);
        return this.passwordEncoder.matches(password,account.getPassword())? account : null;
    }

    public List<PaymentMethod> addPaymentMethod(String email, PaymentMethod newMethod) {
        Account account = this.accountRepository.findAccountByEmail(email);

        if (account != null) {
            List<PaymentMethod> methods = account.getPaymentMethods();
            methods.add(newMethod);
            account.setPaymentMethods(methods);
            this.accountRepository.save(account);
            return account.getPaymentMethods();
        }
        log.error("User doesn't exists at: " + LocalDateTime.now());
        return null;
    }

    public String getPreferredPayment(String email) {
        Account account = this.accountRepository.findAccountByEmail(email);
        if (account != null) {
           return account.getPreferredPayment().toString();
        }

        log.error("User doesn't exists at: " + LocalDateTime.now());
        return null;
    }

    public PaymentMethod getPaymentDetail(String email, PaymentType paymentType) {
        Account account = this.accountRepository.findAccountByEmail(email);
        String preferredType = paymentType!= null? paymentType.toString() : this.getPreferredPayment(email);

        if (account != null) {
            List<PaymentMethod> paymentMethods= account.getPaymentMethods();

            if(paymentMethods != null) {
                Optional<PaymentMethod> preferredMethod= paymentMethods.stream()
                        .filter(paymentMethod -> paymentMethod.getPaymentType().toString().equals(preferredType)).findFirst();

                return preferredMethod.isPresent() ? preferredMethod.get() : null;
            } else {
                log.error("User payment methods is null : " + LocalDateTime.now());
            }
        } else {
            log.error("User doesn't exists : " + LocalDateTime.now());
        }

        return null;
    }

    public AuthResponse getAuthResponse(Account account){
        return new AuthResponse(true, account.getFirstName(), account.getLastName(), account.getEmail(), account.getRoles());
    }
}
