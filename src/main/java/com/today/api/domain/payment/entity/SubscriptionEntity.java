package com.today.api.domain.payment.entity;

import com.today.api.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "subscriptions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(name = "next_payment_date")
    private LocalDateTime nextPaymentDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public SubscriptionEntity(UserEntity user, SubscriptionStatus status) {
        this.user = user;
        this.status = status;
    }

    // Constructor: Domain Model -> Entity
    public SubscriptionEntity(com.today.api.domain.payment.model.Subscription subscription, UserEntity user) {
        this.id = subscription.getId();
        this.user = user;
        this.status = SubscriptionStatus.valueOf(subscription.getStatus().name());
        this.nextPaymentDate = subscription.getNextPaymentDate();
        this.createdAt = subscription.getCreatedAt();
    }

    // Method: Entity -> Domain Model
    public com.today.api.domain.payment.model.Subscription toDomain() {
        return new com.today.api.domain.payment.model.Subscription(
                this.id,
                this.user.getId(),
                com.today.api.domain.payment.model.Subscription.SubscriptionStatus.valueOf(this.status.name()),
                this.nextPaymentDate,
                this.createdAt);
    }

    public enum SubscriptionStatus {
        ACTIVE, INACTIVE, CANCELED
    }
}
