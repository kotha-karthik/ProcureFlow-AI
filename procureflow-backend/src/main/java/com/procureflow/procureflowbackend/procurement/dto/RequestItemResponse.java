package com.procureflow.procureflowbackend.procurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemResponse {

    private String itemName;
    private String itemDescription;
    private BigDecimal quantity;
    private String unitOfMeasure;
    private BigDecimal estimatedUnitPrice;
    private BigDecimal estimatedTotalPrice;
    private String category;
}
