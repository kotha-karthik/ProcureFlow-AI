package com.procureflow.procureflowbackend.procurement.controller;

import com.procureflow.procureflowbackend.procurement.dto.CreateRfqRequest;
import com.procureflow.procureflowbackend.procurement.dto.RfqResponse;
import com.procureflow.procureflowbackend.procurement.service.RfqService;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rfqs")
@RequiredArgsConstructor
public class RfqController {
    private final RfqService rfqService;

    @PostMapping
    public RfqResponse createRfq(@RequestBody CreateRfqRequest request, Authentication authentication)
    {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return rfqService.createRfq(request, user);
    }

    @GetMapping
    public List<RfqResponse> getAllRfqs(Authentication authentication)
    {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return rfqService.getAllRfqs(user);
    }
    @GetMapping("/{id}")
    public RfqResponse getRfqById(@PathVariable UUID id)
    {

        return rfqService.getRfqById(id);
    }

    @PostMapping("/{id}/send")
    public RfqResponse sendRfq(@PathVariable UUID id)
    {

        return rfqService.sendRfq(id);
    }
}
