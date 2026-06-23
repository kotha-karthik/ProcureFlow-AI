package com.procureflow.procureflowbackend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {
    private long purchaseRequests;
    private long vendors;
    private long rfqs;
    private long purchaseOrders;
    private long goodsReceipts;
    private long invoices;
    private long payments;
}
