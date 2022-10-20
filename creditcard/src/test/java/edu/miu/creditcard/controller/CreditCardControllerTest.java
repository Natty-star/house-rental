package edu.miu.creditcard.controller;

import edu.miu.creditcard.entity.CreditCard;
import edu.miu.creditcard.entity.PaymentMethod;
import edu.miu.creditcard.entity.PaymentType;
import edu.miu.creditcard.service.CreditCardService;
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
class CreditCardControllerTest {
    @Mock
    CreditCardService creditCardService;
    CreditCardController underTest;

    @BeforeEach
    void setUp() {
        underTest = new CreditCardController(creditCardService);
    }

    @Test
    void canPay() {
        //given
        PaymentMethod paymentMethod = new PaymentMethod(
                PaymentType.CC,
                null,
                null,
                "CC123",
                "543",
                null
        );
        CreditCard creditCard = new CreditCard(
                "example@domain.com",
                "objectId18329832",
                "objectIDD2342315",
                500d,
                paymentMethod

        );

        // when
        underTest.pay(creditCard);

        //then
        ArgumentCaptor<CreditCard> creditCardArgumentCaptor = ArgumentCaptor.forClass(CreditCard.class);
        verify(creditCardService).pay(creditCardArgumentCaptor.capture());

        CreditCard capturedValue = creditCardArgumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(creditCard);

    }
}