package com.procureflow.procureflowbackend.finance.controller;

import com.procureflow.procureflowbackend.finance.dto.CreateInvoiceRequest;
import com.procureflow.procureflowbackend.finance.dto.InvoiceResponse;
import com.procureflow.procureflowbackend.finance.service.InvoiceService;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService service;

    @PostMapping
    public InvoiceResponse createInvoice(@RequestBody CreateInvoiceRequest request, Authentication authentication)
    {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.createInvoice(request, user);
    }
}
