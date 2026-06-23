package com.procureflow.procureflowbackend.finance.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreatePaymentRequest {
    private UUID invoiceId;
    private UUID vendorId;
    private BigDecimal paymentAmount;
    private String paymentMethod;
    private String transactionReference;
    private String remarks;
}
