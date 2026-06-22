package com.procureflow.procureflowbackend.procurement.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateQuotationRequest {
    private UUID rfqId;
    private UUID vendorId;
    private String currencyCode;
    private Integer deliveryDays;
    private Integer warrantyMonths;
    private LocalDate validityDate;

    private List<QuotationItemRequest> items;
}
