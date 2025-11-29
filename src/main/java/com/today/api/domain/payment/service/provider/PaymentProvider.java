package com.today.api.domain.payment.service.provider;


public interface PaymentProvider {
    boolean validatePayment(String paymentKey, String orderId, java.math.BigDecimal amount);

    String requestBillingKey(String customerKey, String authKey);

    void approvePayment(String paymentKey, String orderId, java.math.BigDecimal amount);

    void cancelPayment(String paymentKey, String cancelReason);
}
