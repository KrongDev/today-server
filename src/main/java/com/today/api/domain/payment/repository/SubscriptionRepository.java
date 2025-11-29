package com.today.api.domain.payment.repository;


import com.today.api.domain.payment.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findByUserId(Long userId);
}
