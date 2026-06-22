package com.procureflow.procureflowbackend.vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {
    private UUID vendorId;
    private String vendorCode;
    private String vendorName;
    private String email;
    private String phoneNumber;
    private String status;
}
