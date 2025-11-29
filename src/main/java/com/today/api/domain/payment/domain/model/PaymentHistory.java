package com.today.api.domain.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentHistory {
    private final Long id;
    private final Long userId;
    private final String orderId;
    private final String paymentKey;
    private final BigDecimal amount;
    private final PaymentType paymentType;
    private PaymentStatus status;
    private final LocalDateTime paidAt;

    // Factory method
    public static PaymentHistory create(Long userId, String orderId, String paymentKey,
            BigDecimal amount, PaymentType paymentType) {
        return new PaymentHistory(null, userId, orderId, paymentKey, amount,
                paymentType, PaymentStatus.SUCCESS, LocalDateTime.now());
    }

    // Business Logic
    public void cancel() {
        if (this.status == PaymentStatus.CANCELED) {
            throw new IllegalStateException("Payment is already canceled");
        }
        this.status = PaymentStatus.CANCELED;
    }

    public void markAsFailed() {
        this.status = PaymentStatus.FAIL;
    }

    public boolean isSuccessful() {
        return this.status == PaymentStatus.SUCCESS;
    }

    public enum PaymentType {
        GENERAL, SUBSCRIPTION
    }

    public enum PaymentStatus {
        SUCCESS, FAIL, CANCELED
    }
}
