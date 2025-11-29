package com.today.api.domain.payment.domain.repository;

import com.today.api.domain.payment.domain.model.PaymentHistory;

import java.util.List;
import java.util.Optional;

public interface PaymentHistoryDomainRepository {
    PaymentHistory save(PaymentHistory paymentHistory);

    Optional<PaymentHistory> findById(Long id);

    List<PaymentHistory> findAllByUserId(Long userId);
}
