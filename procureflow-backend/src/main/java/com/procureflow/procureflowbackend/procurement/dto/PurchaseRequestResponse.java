package com.procureflow.procureflowbackend.procurement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestResponse {
    private UUID requestId;
    private String requestNumber;
    private String title;
    private String requestStatus;
    private BigDecimal totalEstimatedAmount;
    private String businessJustification;
    private String priority;
    private String currencyCode;
    private LocalDate requiredByDate;
    private List<RequestItemResponse> items;
}
