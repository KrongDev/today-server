package com.today.api.domain.payment.interfaces.dto;

import com.today.api.domain.payment.domain.model.Subscription;

import java.time.format.DateTimeFormatter;

public record SubscriptionResponse(
        Long id,
        String status,
        String nextPaymentDate,
        String createdAt) {
    public static SubscriptionResponse from(Subscription subscription) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getStatus().name(),
                subscription.getNextPaymentDate() != null
                        ? subscription.getNextPaymentDate().format(formatter)
                        : null,
                subscription.getCreatedAt().format(formatter));
    }
}
