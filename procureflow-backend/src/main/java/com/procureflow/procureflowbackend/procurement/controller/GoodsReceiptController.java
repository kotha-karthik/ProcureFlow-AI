package com.procureflow.procureflowbackend.procurement.controller;

import com.procureflow.procureflowbackend.procurement.dto.CreateGoodsReceiptRequest;
import com.procureflow.procureflowbackend.procurement.dto.GoodsReceiptResponse;
import com.procureflow.procureflowbackend.procurement.service.GoodsReceiptService;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goods-receipts")
@RequiredArgsConstructor
public class GoodsReceiptController {
    private final GoodsReceiptService service;

    @PostMapping
    public GoodsReceiptResponse createReceipt(@RequestBody CreateGoodsReceiptRequest request, Authentication authentication)
    {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.createReceipt(request, user);
    }

    @GetMapping
    public List<GoodsReceiptResponse> getAllReceipts(Authentication authentication) {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.getAllReceipts(user);
    }

    @GetMapping("/{id}")
    public GoodsReceiptResponse getReceiptById(@PathVariable UUID id) {

        return service.getReceiptById(id);
    }
}
