package com.procureflow.procureflowbackend.vendor.service;

import com.procureflow.procureflowbackend.security.CustomUserDetails;
import com.procureflow.procureflowbackend.vendor.dto.CreateVendorRequest;
import com.procureflow.procureflowbackend.vendor.dto.VendorResponse;
import com.procureflow.procureflowbackend.vendor.entity.Vendor;
import com.procureflow.procureflowbackend.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorService {
    private final VendorRepository vendorRepository;

    public VendorResponse createVendor(CreateVendorRequest request, CustomUserDetails user)
    {

        UUID vendorId = UUID.randomUUID();

        String vendorCode =
                "VEND-" + System.currentTimeMillis();

        Vendor vendor = Vendor.builder()
                .vendorId(vendorId)
                .organizationId(user.getOrganizationId())
                .vendorCode(vendorCode)
                .vendorName(request.getVendorName())
                .taxIdentifier(request.getTaxIdentifier())
                .gstNumber(request.getGstNumber())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .website(request.getWebsite())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .performanceRating(BigDecimal.ZERO)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .createdBy(user.getUserId())
                .isDeleted(false)
                .build();

        vendorRepository.save(vendor);

        return VendorResponse.builder()
                .vendorId(vendorId)
                .vendorCode(vendorCode)
                .vendorName(vendor.getVendorName())
                .email(vendor.getEmail())
                .phoneNumber(vendor.getPhoneNumber())
                .status(vendor.getStatus())
                .build();
    }

    public List<VendorResponse> getAllVendors() {

        return vendorRepository.findByIsDeletedFalse()
                .stream()
                .map(vendor -> VendorResponse.builder()
                        .vendorId(vendor.getVendorId())
                        .vendorCode(vendor.getVendorCode())
                        .vendorName(vendor.getVendorName())
                        .email(vendor.getEmail())
                        .phoneNumber(vendor.getPhoneNumber())
                        .status(vendor.getStatus())
                        .build())
                .toList();
    }

    public VendorResponse getVendorById(UUID vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        return VendorResponse.builder()
                .vendorId(vendor.getVendorId())
                .vendorCode(vendor.getVendorCode())
                .vendorName(vendor.getVendorName())
                .email(vendor.getEmail())
                .phoneNumber(vendor.getPhoneNumber())
                .status(vendor.getStatus())
                .build();
    }
}
