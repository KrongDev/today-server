package com.today.api.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String paymentKey;
    private String orderId;
    private BigDecimal amount;
}
