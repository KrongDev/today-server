package com.today.api.domain.payment.domain.repository;

import com.today.api.domain.payment.infrastructure.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentHistoryJpaRepository extends JpaRepository<PaymentHistoryEntity, Long> {
    List<PaymentHistoryEntity> findAllByUserIdOrderByPaidAtDesc(Long userId);
}
