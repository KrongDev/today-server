package com.today.api.domain.payment.interfaces.dto;

import com.today.api.domain.payment.domain.model.PaymentHistory;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public record PaymentHistoryResponse(
        Long id,
        String orderId,
        BigDecimal amount,
        String status,
        String type,
        String paidAt) {
    public static PaymentHistoryResponse from(PaymentHistory history) {
        return new PaymentHistoryResponse(
                history.getId(),
                history.getOrderId(),
                history.getAmount(),
                history.getStatus().name(),
                history.getPaymentType().name(),
                history.getPaidAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
