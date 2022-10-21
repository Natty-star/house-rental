package cs590.accountservice.service;


import cs590.accountservice.entity.*;
import cs590.accountservice.repository.AccountRepository;
import cs590.accountservice.service.Imp.AccountServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private AccountServiceImp accountService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    PaymentMethod method = new PaymentMethod(
            PaymentType.BANK, "R120000", "BA-1234445555",null, null, null);

    PaymentMethod method2 = new PaymentMethod(
            PaymentType.PAYPAL,null,null,null,null,"PP1234445555");

    List<PaymentMethod> methods = Arrays.asList(method, method2);

    List<Role> roles = Arrays.asList(
            new Role("admin"),  new Role("guest")
    );

    String encodedPassword = this.passwordEncoder.encode("1234");

    Address address = new Address("US","IA","FAIRFIELD", "52557", "1000 N Street");
    Account mockAccount = new Account("Selam", "yilma",  "selu@gmail.com", encodedPassword, roles, address, PaymentType.BANK, methods);

    @Test
    public void getAll() throws Exception {
      given(accountRepository.findAll()).willReturn(Arrays.asList(mockAccount));

      final List<Account> response = accountService.findAll();

      Assertions.assertEquals(response.size(), 1);
    }

    @Test
    public void findById() throws Exception {
        given(accountRepository.findAccountByEmail("")).willReturn(mockAccount);

        final Account response = accountService.findByEmail("");

        Assertions.assertNotNull(response);
    }

    @Test
    public void findAccount() throws Exception {
        given(accountRepository.findAccountByEmail(mockAccount.getEmail())).willReturn(mockAccount);

        final Account response = accountService.getAccount(mockAccount.getEmail(),"1234");

        Assertions.assertNotNull(response);
    }

    @Test
    public void createFailAccount() throws Exception {
        given(accountRepository.save(mockAccount)).willAnswer(invocation -> invocation.getArguments());

        Assertions.assertThrows(Exception.class, () -> {
            accountService.createAccount(mockAccount);
        });

    }

    @Test
    public void createSuccessAccount() throws Exception {
        Account newAccount = new Account();
        newAccount.setEmail("test@gmail.com");
        newAccount.setPassword("1234");

        given(accountRepository.save(newAccount)).willReturn(newAccount);
        final Account response = accountService.createAccount(newAccount);

        assertThat(response).isNotNull();
    }

    @Test
    public void updateAccount() throws Exception {
        Query query = new Query().addCriteria(Criteria.where("email").is(mockAccount.getEmail()));
        Update update = new Update();

//        given(mongoTemplate.findAndModify(query, update , Account.class)).willReturn(mockAccount);
        final Account response = accountService.updateAccount(mockAccount.getEmail(),mockAccount);

        assertThat(response).isNull();
    }

    @Test
    public void getPreferredPayment() throws Exception {
        given(accountRepository.findAccountByEmail(mockAccount.getEmail())).willReturn(mockAccount);

        String  response = accountService.getPreferredPayment(mockAccount.getEmail());

        Assertions.assertEquals(response, mockAccount.getPreferredPayment().toString());
    }

    @Test
    public void getPaymentDetail() throws Exception {
        given(accountRepository.findAccountByEmail(mockAccount.getEmail())).willReturn(mockAccount);

        PaymentMethod  response = accountService.getPaymentDetail(mockAccount.getEmail(), null);

        Assertions.assertEquals(response.getPaymentType().toString(), mockAccount.getPreferredPayment().toString());
    }
}
