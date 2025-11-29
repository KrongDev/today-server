package com.today.api.domain.payment.interfaces.controller;

import com.today.api.domain.payment.application.service.PaymentFacade;
import com.today.api.domain.payment.interfaces.dto.PaymentHistoryResponse;
import com.today.api.domain.payment.interfaces.dto.PaymentRequest;
import com.today.api.domain.payment.interfaces.dto.SubscriptionResponse;
import com.today.api.global.security.oauth2.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmPayment(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody PaymentRequest request) {
        paymentFacade.confirmPayment(userDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentHistoryResponse>> getPaymentHistory(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(paymentFacade.getPaymentHistory(userDetails.getId()));
    }

    @GetMapping("/subscription")
    public ResponseEntity<SubscriptionResponse> getSubscription(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(paymentFacade.getSubscription(userDetails.getId()));
    }

    @PostMapping("/subscription")
    public ResponseEntity<SubscriptionResponse> createSubscription(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(paymentFacade.createSubscription(userDetails.getId()));
    }

    @DeleteMapping("/subscription")
    public ResponseEntity<Void> cancelSubscription(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        paymentFacade.cancelSubscription(userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
