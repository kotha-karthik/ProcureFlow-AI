package com.procureflow.procureflowbackend.procurement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestItemRequest {
    private String itemName;

    private String itemDescription;

    private BigDecimal quantity;

    private String unitOfMeasure;

    private BigDecimal estimatedUnitPrice;

    private String category;
}
