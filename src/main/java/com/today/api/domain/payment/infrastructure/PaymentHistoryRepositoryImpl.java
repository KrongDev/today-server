package com.today.api.domain.payment.infrastructure;

import com.today.api.domain.payment.domain.model.PaymentHistory;
import com.today.api.domain.payment.domain.repository.PaymentHistoryDomainRepository;
import com.today.api.domain.payment.domain.repository.PaymentHistoryJpaRepository;
import com.today.api.domain.payment.infrastructure.entity.PaymentHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PaymentHistoryRepositoryImpl implements PaymentHistoryDomainRepository {

    private final PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    @Override
    public PaymentHistory save(PaymentHistory paymentHistory) {
        PaymentHistoryEntity entity = new PaymentHistoryEntity(paymentHistory);
        return paymentHistoryJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<PaymentHistory> findById(Long id) {
        return paymentHistoryJpaRepository.findById(id)
                .map(PaymentHistoryEntity::toDomain);
    }

    @Override
    public List<PaymentHistory> findAllByUserId(Long userId) {
        return paymentHistoryJpaRepository.findAllByUserIdOrderByPaidAtDesc(userId).stream()
                .map(PaymentHistoryEntity::toDomain)
                .collect(Collectors.toList());
    }
}
