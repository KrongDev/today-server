package com.today.api.domain.payment.service;

import com.today.api.domain.payment.dto.*;
import com.today.api.domain.payment.model.PaymentHistory;
import com.today.api.domain.payment.model.Subscription;
import com.today.api.domain.payment.repository.PaymentHistoryRepository;
import com.today.api.domain.payment.repository.SubscriptionRepository;
import com.today.api.domain.payment.service.provider.PaymentProvider;
import com.today.api.domain.user.entity.UserEntity;
import com.today.api.domain.user.repository.UserJpaRepository;
import com.today.api.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final SubscriptionRepository subscriptionRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final UserJpaRepository userRepository;
    private final PaymentProvider paymentProvider;

    @Transactional
    public PaymentResponse confirmPayment(Long userId, PaymentRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // 1. Approve payment via provider
        paymentProvider.approvePayment(request.getPaymentKey(), request.getOrderId(), request.getAmount());

        // 2. Save payment history
        PaymentHistory history = new PaymentHistory(
                null,
                user.getId(),
                request.getOrderId(),
                request.getPaymentKey(),
                request.getAmount(),
                PaymentHistory.PaymentType.GENERAL,
                PaymentHistory.PaymentStatus.SUCCESS,
                null);
        paymentHistoryRepository.save(history);

        return new PaymentResponse(
                history.getId(),
                history.getStatus().name(),
                history.getPaidAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public List<PaymentHistoryResponse> getPaymentHistory(Long userId) {
        return paymentHistoryRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toPaymentHistoryResponse)
                .collect(Collectors.toList());
    }

    public SubscriptionResponse getSubscription(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        return toSubscriptionResponse(subscription);
    }

    private PaymentHistoryResponse toPaymentHistoryResponse(PaymentHistory history) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return PaymentHistoryResponse.builder()
                .id(history.getId())
                .orderId(history.getOrderId())
                .amount(history.getAmount())
                .status(history.getStatus().name())
                .type(history.getPaymentType().name())
                .paidAt(history.getPaidAt() != null ? history.getPaidAt().format(formatter) : null)
                .build();
    }

    @Transactional
    public void migrateData(Long userId, List<MigrationData> data) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // In a real implementation, this would parse the migration data (schedules,
        // etc.)
        // and save them to the database, mapping local IDs to server IDs.
        // For now, we'll just log the count.
        System.out.println("Migrating " + data.size() + " records for user " + userId);
    }

    private SubscriptionResponse toSubscriptionResponse(Subscription subscription) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getStatus().name(),
                subscription.getNextPaymentDate() != null ? subscription.getNextPaymentDate().format(formatter) : null,
                subscription.getCreatedAt().format(formatter));
    }
}
