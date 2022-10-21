package edu.miu.payment.service;

import edu.miu.payment.model.PaymentMethod;
import edu.miu.payment.model.PaymentRequest;
import edu.miu.payment.model.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    PaymentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PaymentService();
    }

    @Test
    void processPayment() {
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
          String response =  underTest.processPayment(paymentRequest);
          assertEquals("Payment in Process",response);
//        when(underTest.processPayment(paymentRequest)).thenReturn("Payment success");
//        assertEquals("Payment success", underTest.processPayment(paymentRequest));




    }
}