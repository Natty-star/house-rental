package edu.miu.paypal.controller;

import edu.miu.paypal.entity.PaymentMethod;
import edu.miu.paypal.entity.PaymentType;
import edu.miu.paypal.entity.Paypal;
import edu.miu.paypal.service.PaypalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaypalControllerTest {
    @Mock
    PaypalService paypalService;
    PaypalController underTest;

    @BeforeEach
    void setUp() {
        underTest = new PaypalController(paypalService);
    }

    @Test
    void canPay() {
        //given
        PaymentMethod paymentMethod = new PaymentMethod(
                PaymentType.PAYPAL,
                null,
                null,
                null,
                null,
                "paymentmethod@paypal.com"
        );
        Paypal paypal = new Paypal(
                "example@domain.com",
                "propertyId234242",
                "reserve234313",
                500d,
                paymentMethod

        );

        // when
        underTest.pay(paypal);

        //then
        ArgumentCaptor<Paypal> paypalArgumentCaptor = ArgumentCaptor.forClass(Paypal.class);
        verify(paypalService).pay(paypalArgumentCaptor.capture());

        Paypal capturedValue = paypalArgumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(paypal);

    }
}