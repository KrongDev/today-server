package com.today.api.domain.payment.infrastructure.entity;

import com.today.api.domain.payment.domain.model.Subscription;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "subscriptions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(name = "next_payment_date")
    private LocalDateTime nextPaymentDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructor: Domain Model -> Entity
    public SubscriptionEntity(Subscription subscription) {
        BeanUtils.copyProperties(subscription, this);
        this.status = SubscriptionStatus.valueOf(subscription.getStatus().name());
    }

    // Method: Entity -> Domain Model
    public Subscription toDomain() {
        return new Subscription(
                this.id,
                this.userId,
                Subscription.SubscriptionStatus.valueOf(this.status.name()),
                this.nextPaymentDate,
                this.createdAt);
    }

    public enum SubscriptionStatus {
        ACTIVE, INACTIVE, CANCELED
    }
}
