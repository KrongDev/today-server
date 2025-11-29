package com.today.api.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class PaymentHistoryResponse {
    private Long id;
    private String orderId;
    private BigDecimal amount;
    private String status;
    private String type;
    private String paidAt;
}
