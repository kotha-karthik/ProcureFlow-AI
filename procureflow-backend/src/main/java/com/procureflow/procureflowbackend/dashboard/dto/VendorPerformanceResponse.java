package com.procureflow.procureflowbackend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorPerformanceResponse {
    private UUID vendorId;
    private String vendorName;

    private long totalQuotations;
    private long totalPurchaseOrders;

    private BigDecimal totalPaymentsReceived;

    private BigDecimal performanceRating;
}
