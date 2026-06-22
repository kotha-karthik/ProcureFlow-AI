package com.procureflow.procureflowbackend.vendor.controller;

import com.procureflow.procureflowbackend.security.CustomUserDetails;
import com.procureflow.procureflowbackend.vendor.dto.CreateVendorRequest;
import com.procureflow.procureflowbackend.vendor.dto.VendorResponse;
import com.procureflow.procureflowbackend.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @PostMapping
    public VendorResponse createVendor(@RequestBody CreateVendorRequest request, Authentication authentication)
    {
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        return vendorService.createVendor(request, user);
    }

    @GetMapping
    public List<VendorResponse> getAllVendors() {

        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public VendorResponse getVendorById(@PathVariable UUID id) {

        return vendorService.getVendorById(id);
    }
}
