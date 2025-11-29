package com.today.api.domain.payment.repository;


import com.today.api.domain.payment.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<PaymentHistory> findByOrderId(String orderId);
}
