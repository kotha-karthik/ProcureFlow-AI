package com.procureflow.procureflowbackend.procurement.controller;

import com.procureflow.procureflowbackend.procurement.dto.PurchaseOrderResponse;
import com.procureflow.procureflowbackend.procurement.service.PurchaseOrderService;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService service;

    @GetMapping
    public List<PurchaseOrderResponse> getAllPurchaseOrders(Authentication authentication)
    {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.getAllPurchaseOrders(user);
    }

    @GetMapping("/{id}")
    public PurchaseOrderResponse
    getPurchaseOrderById(@PathVariable UUID id)
    {
        return service.getPurchaseOrderById(id);
    }
}
