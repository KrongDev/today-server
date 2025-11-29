package com.today.api.domain.payment.service.provider;

import com.today.api.domain.payment.dto.PaymentDto;
import com.today.api.domain.user.entity.UserEntity;

public interface PaymentProvider {
    boolean validatePayment(String paymentKey, String orderId, java.math.BigDecimal amount);

    String requestBillingKey(String customerKey, String authKey);

    void approvePayment(String paymentKey, String orderId, java.math.BigDecimal amount);

    void cancelPayment(String paymentKey, String cancelReason);
}
