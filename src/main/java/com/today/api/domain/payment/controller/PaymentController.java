package com.today.api.domain.payment.controller;

import com.today.api.domain.payment.dto.MigrationData;
import com.today.api.domain.payment.dto.PaymentDto;
import com.today.api.domain.payment.dto.PaymentHistoryResponse;
import com.today.api.domain.payment.dto.PaymentRequest;
import com.today.api.domain.payment.dto.SubscriptionResponse;
import com.today.api.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PaymentRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());
        paymentService.confirmPayment(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentHistoryResponse>> getPaymentHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(paymentService.getPaymentHistory(userId));
    }

    @GetMapping("/subscription")
    public ResponseEntity<SubscriptionResponse> getSubscription(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(paymentService.getSubscription(userId));
    }

    @PostMapping("/migration")
    public ResponseEntity<Void> migrateData(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody List<MigrationData> data) {
        Long userId = Long.parseLong(userDetails.getUsername());
        paymentService.migrateData(userId, data);
        return ResponseEntity.ok().build();
    }
}
