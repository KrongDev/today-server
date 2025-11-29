package com.today.api.domain.payment.dto;

import com.today.api.domain.payment.model.PaymentHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.BeanUtils;

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

    public PaymentHistoryResponse(PaymentHistory paymentHistory) {
        BeanUtils.copyProperties(paymentHistory, this);
    }
}
