package cs590.accountservice.entity;

import lombok.Data;

@Data
public class PaymentMethod {
    private PaymentType paymentType;

    private String routingNumber;

    private String bankAccount;

    private String ccNumber;

    private String CCV;

    private String payPalEmail;

    public PaymentMethod(PaymentType paymentType, String routingNumber, String bankAccount, String ccNumber, String CCV, String payPalEmail) {
        this.paymentType = paymentType;
        this.routingNumber = routingNumber;
        this.bankAccount = bankAccount;
        this.ccNumber = ccNumber;
        this.CCV = CCV;
        this.payPalEmail = payPalEmail;
    }
}