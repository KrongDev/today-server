package com.today.api.domain.payment.domain.service;

import com.today.api.domain.payment.domain.model.PaymentHistory;
import com.today.api.domain.payment.domain.model.Subscription;
import com.today.api.domain.payment.domain.repository.PaymentHistoryDomainRepository;
import com.today.api.domain.payment.domain.repository.SubscriptionDomainRepository;
import com.today.api.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentHistoryDomainRepository paymentHistoryDomainRepository;
    private final SubscriptionDomainRepository subscriptionDomainRepository;

    @Transactional
    public PaymentHistory confirmPayment(Long userId, String orderId, String paymentKey,
            BigDecimal amount, PaymentHistory.PaymentType paymentType) {
        PaymentHistory history = PaymentHistory.create(userId, orderId, paymentKey, amount, paymentType);
        return paymentHistoryDomainRepository.save(history);
    }

    public List<PaymentHistory> getPaymentHistory(Long userId) {
        return paymentHistoryDomainRepository.findAllByUserId(userId);
    }

    public Subscription getSubscription(Long userId) {
        return subscriptionDomainRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
    }

    @Transactional
    public Subscription createSubscription(Long userId) {
        Subscription subscription = Subscription.create(userId);
        return subscriptionDomainRepository.save(subscription);
    }

    @Transactional
    public Subscription cancelSubscription(Long userId) {
        Subscription subscription = getSubscription(userId);
        subscription.cancel();
        return subscriptionDomainRepository.save(subscription);
    }
}
