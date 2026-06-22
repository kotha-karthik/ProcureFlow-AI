package com.procureflow.procureflowbackend.procurement.controller;

import com.procureflow.procureflowbackend.procurement.dto.CreatePurchaseRequestRequest;
import com.procureflow.procureflowbackend.procurement.dto.PurchaseRequestResponse;
import com.procureflow.procureflowbackend.procurement.service.PurchaseRequestService;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procurement/purchase-requests")
@RequiredArgsConstructor
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    @PostMapping
    @PreAuthorize("hasAuthority('PR_CREATE')")
    public PurchaseRequestResponse createPurchaseRequest(@RequestBody CreatePurchaseRequestRequest request, Authentication authentication) {
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        return purchaseRequestService
                .createPurchaseRequest(request, user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PR_VIEW')")
    public List<PurchaseRequestResponse> getAllPurchaseRequests() {

        return purchaseRequestService.getAllPurchaseRequests();
    }
}
