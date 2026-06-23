package com.procureflow.procureflowbackend.finance.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class InvoiceItemRequest {
    private UUID purchaseOrderItemId;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal taxAmount;
}
