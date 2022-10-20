package edu.miu.paypal.service;

import edu.miu.paypal.entity.PaymentMethod;
import edu.miu.paypal.entity.PaymentType;
import edu.miu.paypal.entity.Paypal;
import edu.miu.paypal.repository.PaypalRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaypalServiceTest {
    @Mock
    private PaypalRepo paypalRepo;
    private PaypalService underTest;

    @BeforeEach
    void setUp(){
        underTest = new PaypalService(paypalRepo);
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
        Paypal paypal = new Paypal(
                "example@domain.com",
                "",
                "",
                500d,
                paymentMethod

        );

        // when
        underTest.pay(paypal);

        //then
        ArgumentCaptor<Paypal> paypalArgumentCaptor = ArgumentCaptor.forClass(Paypal.class);
        verify(paypalRepo).insert(paypalArgumentCaptor.capture());

        Paypal capturedValue = paypalArgumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(paypal);



    }
}