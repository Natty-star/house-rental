package cs590.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs590.accountservice.DTO.AuthRequest;
import cs590.accountservice.DTO.AuthResponse;
import cs590.accountservice.controller.AccountController;
import cs590.accountservice.entity.*;
import cs590.accountservice.repository.AccountRepository;
import cs590.accountservice.repository.AddressRepository;
import cs590.accountservice.repository.PaymentMethodRepository;
import cs590.accountservice.service.AccountService;
import cs590.accountservice.service.Imp.AccountServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AccountController.class)
@WithMockUser
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImp accountService;

    @MockBean
    private PaymentMethodRepository paymentMethodRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AddressRepository addressRepository;

    PaymentMethod method = new PaymentMethod(
            PaymentType.BANK, "R120000", "BA-1234445555",null, null, null);

    PaymentMethod method2 = new PaymentMethod(
            PaymentType.PAYPAL,null,null,null,null,"PP1234445555");

    List<PaymentMethod> methods = Arrays.asList(method, method2);

    List<Role> roles = Arrays.asList(
            new Role("admin"),  new Role("guest")
    );
    Address address = new Address("US","IA","FAIRFIELD", "52557", "1000 N Street");
    Account mockAccount = new Account("Selam", "yilma",  "selu@gmail.com", "1234", roles, address, PaymentType.BANK, methods);

    @Test
    public void getAll() throws Exception {

        Mockito.when(accountService.findAll()).thenReturn(Arrays.asList(mockAccount));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/accounts/getAll").accept(
                MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());;
    }

    @Test
    public void getByEmail() throws Exception {

        Mockito.when(accountService.findByEmail(Mockito.anyString())).thenReturn(mockAccount);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/accounts/" + mockAccount.getEmail()).accept(
                MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());;
    }

    @Test
    public void authenticate() throws Exception {
        AuthRequest login = new AuthRequest("selu@gmail.com", "1234");
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(login);
        AuthResponse authResponse = new AuthResponse(true, mockAccount.getFirstName(), mockAccount.getLastName(), mockAccount.getEmail(), mockAccount.getRoles());

        Mockito.when(accountService.getAccount(Mockito.anyString(),Mockito.anyString())).thenReturn(mockAccount);
        Mockito.when(accountService.getAuthResponse(Mockito.any(Account.class))).thenReturn(authResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/accounts/authenticate")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void register() throws Exception {
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(mockAccount);

        Mockito.when(accountService.createAccount(Mockito.any(Account.class))).thenReturn(mockAccount);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/accounts/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void update() throws Exception {
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(mockAccount);
        Mockito.when(accountService.updateAccount(Mockito.anyString(), Mockito.any(Account.class))).thenReturn(mockAccount);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/accounts/" + mockAccount.getEmail())
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    } //

    @Test
    public void addPayment() throws Exception {
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(mockAccount.getPaymentMethods().get(0));
        Mockito.when(accountService.addPaymentMethod(Mockito.anyString(), Mockito.any(PaymentMethod.class))).thenReturn(mockAccount.getPaymentMethods());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/accounts/addPaymentMethod/" + mockAccount.getEmail())
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void getPreferredPayment() throws Exception {

        Mockito.when(accountService.getPreferredPayment(Mockito.anyString())).thenReturn("BANK");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/accounts/preferredPaymentType/" + mockAccount.getEmail()).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), mockAccount.getPreferredPayment().toString());
    }

}
