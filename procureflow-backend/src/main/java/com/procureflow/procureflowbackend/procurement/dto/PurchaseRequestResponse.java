package com.procureflow.procureflowbackend.procurement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
}
