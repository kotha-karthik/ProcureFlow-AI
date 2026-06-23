package com.procureflow.procureflowbackend.finance.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateInvoiceRequest {
    private UUID vendorId;
    private UUID purchaseOrderId;
    private UUID goodsReceiptId;

    private LocalDate dueDate;

    private BigDecimal taxAmount;
    private BigDecimal discountAmount;

    private String currencyCode;
    private String remarks;

    private List<InvoiceItemRequest> items;
}
