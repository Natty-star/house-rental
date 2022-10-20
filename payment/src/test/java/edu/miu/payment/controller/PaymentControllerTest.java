package edu.miu.payment.controller;

import edu.miu.payment.model.PaymentMethod;
import edu.miu.payment.model.PaymentRequest;
import edu.miu.payment.model.PaymentType;
import edu.miu.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {
    @Mock
    PaymentService paymentService;
    PaymentController underTest;

    @BeforeEach
    void setUp() {
        underTest = new PaymentController(paymentService);
    }

    @Test
    void canMakePayment() {
        //Given
        PaymentMethod paymentMethod = new PaymentMethod(
                PaymentType.CC,
                null,
                null,
                "CC123",
                "543",
                null
        );
        PaymentRequest paymentRequest = new PaymentRequest(
                "example@domain.com",
                "obectIdProp123",
                "objectIdReserve123",
                500d,
                PaymentType.CC,
                paymentMethod
        );

        //when
        underTest.makePayment(paymentRequest);
        when(paymentService.processPayment(paymentRequest)).thenReturn("Payment success");

        //then
        ArgumentCaptor<PaymentRequest> paymentRequestArgumentCaptor = ArgumentCaptor.forClass(PaymentRequest.class);
        verify(paymentService).processPayment(paymentRequestArgumentCaptor.capture());

        PaymentRequest captorValue = paymentRequestArgumentCaptor.getValue();
        assertThat(captorValue).isEqualTo(paymentRequest);
        assertEquals("Payment success",underTest.makePayment(paymentRequest));

    }
}