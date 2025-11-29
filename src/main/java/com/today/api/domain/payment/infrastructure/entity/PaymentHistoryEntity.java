package com.today.api.domain.payment.infrastructure.entity;

import com.today.api.domain.payment.domain.model.PaymentHistory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "payment_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PaymentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

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

    // Constructor: Domain Model -> Entity
    public PaymentHistoryEntity(PaymentHistory paymentHistory) {
        BeanUtils.copyProperties(paymentHistory, this);
        this.paymentType = PaymentType.valueOf(paymentHistory.getPaymentType().name());
        this.status = PaymentStatus.valueOf(paymentHistory.getStatus().name());
    }

    // Method: Entity -> Domain Model
    public PaymentHistory toDomain() {
        return new PaymentHistory(
                this.id,
                this.userId,
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
