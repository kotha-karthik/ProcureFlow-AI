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
public class MonthlyReportResponse {
    private String month;

    private long purchaseRequests;
    private long rfqs;
    private long purchaseOrders;
    private long invoices;
    private long payments;

    private BigDecimal monthlySpend;
}
