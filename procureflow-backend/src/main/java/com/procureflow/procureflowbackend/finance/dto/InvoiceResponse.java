package com.procureflow.procureflowbackend.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private UUID invoiceId;
    private String invoiceNumber;
    private BigDecimal netAmount;
    private String matchingStatus;
    private String invoiceStatus;
}
