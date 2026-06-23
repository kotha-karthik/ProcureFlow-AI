package com.procureflow.procureflowbackend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpendAnalysisResponse {
    private long totalPurchaseOrders;
    private BigDecimal totalSpend;
    private long totalInvoices;
    private long totalPayments;
    private BigDecimal totalPaidAmount;
}
