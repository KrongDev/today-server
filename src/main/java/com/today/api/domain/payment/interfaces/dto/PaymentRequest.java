package com.today.api.domain.payment.interfaces.dto;

import java.math.BigDecimal;

public record PaymentRequest(
        String paymentKey,
        String orderId,
        BigDecimal amount) {
}
