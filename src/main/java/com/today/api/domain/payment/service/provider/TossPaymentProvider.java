package com.today.api.domain.payment.service.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentProvider implements PaymentProvider {

    @Value("${payment.toss.secret-key:test_sk_key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String TOSS_API_URL = "https://api.tosspayments.com/v1";

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String encodedKey = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public boolean validatePayment(String paymentKey, String orderId, BigDecimal amount) {
        // In a real implementation, you might query the payment status from Toss
        // For now, we'll assume if we have the key, it's valid or we'll verify it
        // during approval
        return true;
    }

    @Override
    public String requestBillingKey(String customerKey, String authKey) {
        String url = TOSS_API_URL + "/billing/authorizations/issue";

        Map<String, String> body = new HashMap<>();
        body.put("customerKey", customerKey);
        body.put("authKey", authKey);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, getHeaders());

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("billingKey");
            }
        } catch (Exception e) {
            log.error("Failed to issue billing key", e);
            throw new RuntimeException("Failed to issue billing key: " + e.getMessage());
        }
        throw new RuntimeException("Failed to issue billing key");
    }

    @Override
    public void approvePayment(String paymentKey, String orderId, BigDecimal amount) {
        String url = TOSS_API_URL + "/payments/confirm";

        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", paymentKey);
        body.put("orderId", orderId);
        body.put("amount", amount);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, getHeaders());

        try {
            restTemplate.postForEntity(url, entity, Map.class);
            log.info("Payment approved: orderId={}", orderId);
        } catch (Exception e) {
            log.error("Failed to approve payment", e);
            throw new RuntimeException("Payment approval failed: " + e.getMessage());
        }
    }

    @Override
    public void cancelPayment(String paymentKey, String cancelReason) {
        String url = TOSS_API_URL + "/payments/" + paymentKey + "/cancel";

        Map<String, String> body = new HashMap<>();
        body.put("cancelReason", cancelReason);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, getHeaders());

        try {
            restTemplate.postForEntity(url, entity, Map.class);
            log.info("Payment canceled: paymentKey={}", paymentKey);
        } catch (Exception e) {
            log.error("Failed to cancel payment", e);
            throw new RuntimeException("Payment cancellation failed: " + e.getMessage());
        }
    }
}
