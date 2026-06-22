package com.procureflow.procureflowbackend.procurement.controller;

import com.procureflow.procureflowbackend.procurement.dto.CreateQuotationRequest;
import com.procureflow.procureflowbackend.procurement.dto.VendorQuotationResponse;
import com.procureflow.procureflowbackend.procurement.service.VendorQuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/quotations")
@RequiredArgsConstructor
public class VendorQuotationController {
    private final VendorQuotationService service;

    @PostMapping
    public VendorQuotationResponse createQuotation(@RequestBody CreateQuotationRequest request)
    {

        return service.createQuotation(request);
    }

    @GetMapping("/rfq/{rfqId}")
    public List<VendorQuotationResponse>
    getQuotationsByRfq(@PathVariable UUID rfqId)
    {

        return service.getQuotationsByRfq(rfqId);
    }

    @GetMapping("/rfq/{rfqId}/compare")
    public List<VendorQuotationResponse>
    compareQuotations(
            @PathVariable UUID rfqId
    ) {

        return service.compareQuotations(rfqId);
    }
}
