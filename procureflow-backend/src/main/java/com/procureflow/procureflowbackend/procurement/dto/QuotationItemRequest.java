package com.procureflow.procureflowbackend.procurement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class QuotationItemRequest {
    private UUID rfqItemId;
    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private String remarks;
}
