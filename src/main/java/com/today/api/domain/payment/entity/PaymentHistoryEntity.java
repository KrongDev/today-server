package com.today.api.domain.payment.entity;

import com.today.api.domain.payment.model.PaymentHistory;
import com.today.api.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payment_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PaymentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @CreatedDate
    @Column(name = "paid_at", nullable = false, updatable = false)
    private LocalDateTime paidAt;

    public PaymentHistoryEntity(UserEntity user, String orderId, String paymentKey, BigDecimal amount,
            PaymentType paymentType, PaymentStatus status) {
        this.user = user;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.amount = amount;
        this.paymentType = paymentType;
        this.status = status;
    }

    // Constructor: Domain Model -> Entity
    public PaymentHistoryEntity(PaymentHistory paymentHistory, UserEntity user) {
        this.id = paymentHistory.getId();
        this.user = user;
        this.orderId = paymentHistory.getOrderId();
        this.paymentKey = paymentHistory.getPaymentKey();
        this.amount = paymentHistory.getAmount();
        this.paymentType = PaymentType.valueOf(paymentHistory.getPaymentType().name());
        this.status = PaymentStatus.valueOf(paymentHistory.getStatus().name());
        this.paidAt = paymentHistory.getPaidAt();
    }

    // Method: Entity -> Domain Model
    public PaymentHistory toDomain() {
        return new PaymentHistory(
                this.id,
                this.user.getId(),
                this.orderId,
                this.paymentKey,
                this.amount,
                PaymentHistory.PaymentType.valueOf(this.paymentType.name()),
                PaymentHistory.PaymentStatus.valueOf(this.status.name()),
                this.paidAt);
    }

    public enum PaymentType {
        GENERAL, SUBSCRIPTION
    }

    public enum PaymentStatus {
        SUCCESS, FAIL, CANCELED
    }
}
