package com.procureflow.procureflowbackend.procurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponse {
    private UUID purchaseOrderId;
    private String poNumber;
    private UUID vendorId;
    private BigDecimal totalAmount;
    private String currencyCode;
    private String status;
    private LocalDate issueDate;
    private LocalDate expectedDeliveryDate;
}
