package com.today.api.domain.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Subscription {
    private final Long id;
    private final Long userId;
    private SubscriptionStatus status;
    private LocalDateTime nextPaymentDate;
    private final LocalDateTime createdAt;

    // Factory method
    public static Subscription create(Long userId) {
        return new Subscription(null, userId, SubscriptionStatus.ACTIVE,
                LocalDateTime.now().plusMonths(1), LocalDateTime.now());
    }

    // Business Logic
    public void activate() {
        this.status = SubscriptionStatus.ACTIVE;
        this.nextPaymentDate = LocalDateTime.now().plusMonths(1);
    }

    public void cancel() {
        this.status = SubscriptionStatus.CANCELED;
        this.nextPaymentDate = null;
    }

    public void deactivate() {
        this.status = SubscriptionStatus.INACTIVE;
    }

    public void renewPaymentDate() {
        if (this.status == SubscriptionStatus.ACTIVE) {
            this.nextPaymentDate = LocalDateTime.now().plusMonths(1);
        }
    }

    public boolean isActive() {
        return this.status == SubscriptionStatus.ACTIVE;
    }

    public enum SubscriptionStatus {
        ACTIVE, INACTIVE, CANCELED
    }
}
