package com.today.api.domain.payment.repository;


import com.today.api.domain.payment.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {
    List<PaymentHistoryEntity> findAllByUserIdOrderByPaidAtDesc(Long userId);
}
