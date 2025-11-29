package com.today.api.domain.payment.infrastructure;

import com.today.api.domain.payment.domain.model.Subscription;
import com.today.api.domain.payment.domain.repository.SubscriptionDomainRepository;
import com.today.api.domain.payment.domain.repository.SubscriptionJpaRepository;
import com.today.api.domain.payment.infrastructure.entity.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionDomainRepository {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    @Override
    public Subscription save(Subscription subscription) {
        SubscriptionEntity entity = new SubscriptionEntity(subscription);
        return subscriptionJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionJpaRepository.findById(id)
                .map(SubscriptionEntity::toDomain);
    }

    @Override
    public Optional<Subscription> findByUserId(Long userId) {
        return subscriptionJpaRepository.findByUserId(userId)
                .map(SubscriptionEntity::toDomain);
    }
}
