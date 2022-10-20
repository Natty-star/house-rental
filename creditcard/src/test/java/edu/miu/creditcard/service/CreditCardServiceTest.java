package edu.miu.creditcard.service;

import edu.miu.creditcard.entity.CreditCard;
import edu.miu.creditcard.entity.PaymentMethod;
import edu.miu.creditcard.entity.PaymentType;
import edu.miu.creditcard.repository.CreditCardRepo;
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
class CreditCardServiceTest {
    @Mock
    CreditCardRepo cardRepo;
    CreditCardService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CreditCardService(cardRepo);
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
        ArgumentCaptor<CreditCard> paypalArgumentCaptor = ArgumentCaptor.forClass(CreditCard.class);
        verify(cardRepo).insert(paypalArgumentCaptor.capture());

        CreditCard capturedValue = paypalArgumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(creditCard);

    }
}