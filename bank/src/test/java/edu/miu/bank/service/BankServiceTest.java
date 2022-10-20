package edu.miu.bank.service;

import edu.miu.bank.entity.Bank;
import edu.miu.bank.entity.PaymentMethod;
import edu.miu.bank.entity.PaymentType;
import edu.miu.bank.repository.BankRepo;
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
class BankServiceTest {

    @Mock
    BankRepo bankRepo;
    BankService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BankService(bankRepo);
    }

    @Test
    void canPay() {
        //Given
        PaymentMethod paymentMethod = new PaymentMethod(
                PaymentType.BANK,
                "routingnumber123423",
                "bankaccount1234232",
                null,
                null,
                null
        );
        Bank bank = new Bank(
                "example@domain.com",
                "propertyId214313",
                "reservationId987924",
                500d,
                paymentMethod

        );

        //When
        underTest.pay(bank);


        //Then
        ArgumentCaptor<Bank> bankArgumentCaptor = ArgumentCaptor.forClass(Bank.class);
        verify(bankRepo).insert(bankArgumentCaptor.capture());

        Bank capturedValue = bankArgumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(bank);


    }
}