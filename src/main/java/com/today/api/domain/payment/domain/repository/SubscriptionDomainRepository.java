package com.today.api.domain.payment.domain.repository;

import com.today.api.domain.payment.domain.model.Subscription;

import java.util.Optional;

public interface SubscriptionDomainRepository {
    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    Optional<Subscription> findByUserId(Long userId);
}
