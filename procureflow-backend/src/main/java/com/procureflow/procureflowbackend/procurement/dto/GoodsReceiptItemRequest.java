package com.procureflow.procureflowbackend.procurement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GoodsReceiptItemRequest {
    private UUID purchaseOrderItemId;
    private BigDecimal quantityReceived;
    private BigDecimal quantityAccepted;
    private BigDecimal quantityRejected;
    private String remarks;
}
