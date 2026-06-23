package com.procureflow.procureflowbackend.finance.controller;

import com.procureflow.procureflowbackend.finance.dto.CreatePaymentRequest;
import com.procureflow.procureflowbackend.finance.dto.PaymentResponse;
import com.procureflow.procureflowbackend.finance.service.PaymentService;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @PostMapping
    public PaymentResponse createPayment(@RequestBody CreatePaymentRequest request, Authentication authentication) {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.createPayment(request, user);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments(Authentication authentication) {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.getAllPayments(user);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable UUID id) {

        return service.getPaymentById(id);
    }
}
