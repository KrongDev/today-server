package com.today.api.domain.payment.application.service;

import com.today.api.domain.payment.domain.model.PaymentHistory;
import com.today.api.domain.payment.domain.model.Subscription;
import com.today.api.domain.payment.domain.service.PaymentService;
import com.today.api.domain.payment.interfaces.dto.PaymentHistoryResponse;
import com.today.api.domain.payment.interfaces.dto.PaymentRequest;
import com.today.api.domain.payment.interfaces.dto.SubscriptionResponse;
import com.today.api.domain.payment.service.provider.PaymentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final PaymentProvider paymentProvider;

    @Transactional
    public void confirmPayment(Long userId, PaymentRequest request) {
        // 1. Verify payment with provider
        paymentProvider.approvePayment(request.paymentKey(), request.orderId(), request.amount());

        // 2. Save payment history
        paymentService.confirmPayment(
                userId,
                request.orderId(),
                request.paymentKey(),
                request.amount(),
                PaymentHistory.PaymentType.GENERAL);
    }

    @Transactional(readOnly = true)
    public List<PaymentHistoryResponse> getPaymentHistory(Long userId) {
        return paymentService.getPaymentHistory(userId).stream()
                .map(PaymentHistoryResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubscriptionResponse getSubscription(Long userId) {
        Subscription subscription = paymentService.getSubscription(userId);
        return SubscriptionResponse.from(subscription);
    }

    @Transactional
    public SubscriptionResponse createSubscription(Long userId) {
        Subscription subscription = paymentService.createSubscription(userId);
        return SubscriptionResponse.from(subscription);
    }

    @Transactional
    public void cancelSubscription(Long userId) {
        paymentService.cancelSubscription(userId);
    }
}
