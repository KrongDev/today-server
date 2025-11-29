package com.today.api.domain.payment.repository;


import com.today.api.domain.payment.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserId(Long userId);

    Optional<Subscription> findByCustomerKey(String customerKey);
}
