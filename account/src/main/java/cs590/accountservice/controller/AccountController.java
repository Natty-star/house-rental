package cs590.accountservice.controller;

import cs590.accountservice.DTO.AuthResponse;
import cs590.accountservice.entity.*;
import cs590.accountservice.DTO.AuthRequest;
import cs590.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        Account account = accountService.getAccount(authRequest.getUsername(), authRequest.getPassword());
        AuthResponse authResponse = account == null ? new AuthResponse() : account.getAuthResponse();
        authResponse.setStatus(account != null);

        //new AuthResponse(account != null, account.getEmail(), account.getRoles());
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> findAllUsers() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        Account account = accountService.findByEmail(email);
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.createAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{email}")
    public ResponseEntity<?> update(@PathVariable String email, @RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(email, account);
        return ResponseEntity.ok(updatedAccount);
    }

//    @DeleteMapping(path = "/{accountId}")
//    public ResponseEntity<?> deleteAccount(@PathVariable String accountId) {
//        if (!accountService.deleteAccount(accountId)) {
//            return new ResponseEntity<>("Account not found!", HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>("Successful", HttpStatus.OK);
//    }

//    @GetMapping(path = "/address/{email}")
//    public ResponseEntity<?> getAddress(@PathVariable String email) {
//        Address address = accountService.getAddress(email);
//        return ResponseEntity.ok(address);
//    }

    @PostMapping("/addPaymentMethod/{email}")
    public ResponseEntity<?> addPaymentMethod(@PathVariable String email, @RequestBody PaymentMethod payment) {
        List<PaymentMethod> paymentMethod= accountService.addPaymentMethod(email, payment);
        return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
    }

    @GetMapping(path = "/preferredPaymentType/{email}")
    public ResponseEntity<String> getPreferredPaymentType(@PathVariable String email) {
        String type = accountService.getPreferredPayment(email);
        return ResponseEntity.ok(type);
    }

//    @PutMapping(path = "/updatePreferredPaymentType/{email}/{paymentType}")
//    public ResponseEntity<Boolean> getPreferredPaymentType(@PathVariable String email,  @PathVariable  PaymentType paymentType) {
//        Boolean response = accountService.updatePreferredPayment(email,paymentType );
//        return ResponseEntity.ok(response);
//    }


    @GetMapping(path = "/preferredPaymentMethod/{email}")
    public ResponseEntity<?> getPreferredPaymentMethod(@PathVariable String email) {
        PaymentMethod method = accountService.getPaymentDetail(email, null );
        return ResponseEntity.ok(method);
    }

    @GetMapping(path = "/preferredPaymentMethod/{email}/{paymentType}")
    public ResponseEntity<?> getPreferredPaymentMethod(@PathVariable String email, @PathVariable PaymentType paymentType) {
        PaymentMethod method = accountService.getPaymentDetail(email, paymentType );
        return ResponseEntity.ok(method);
    }

}