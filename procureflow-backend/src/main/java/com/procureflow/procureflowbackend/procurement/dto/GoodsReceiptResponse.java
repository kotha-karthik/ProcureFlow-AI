package com.procureflow.procureflowbackend.procurement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReceiptResponse {
    private UUID goodsReceiptId;
    private String grnNumber;
    private UUID purchaseOrderId;
    private String overallStatus;
}
