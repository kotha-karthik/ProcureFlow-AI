package com.procureflow.procureflowbackend.vendor.dto;

import lombok.Data;

@Data
public class CreateVendorRequest {
    private String vendorName;
    private String taxIdentifier;
    private String gstNumber;
    private String email;
    private String phoneNumber;
    private String website;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
