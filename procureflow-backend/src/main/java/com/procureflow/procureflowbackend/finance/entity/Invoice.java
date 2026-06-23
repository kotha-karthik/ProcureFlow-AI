package com.procureflow.procureflowbackend.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoices", schema = "finance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @Column(name = "invoice_id")
    private UUID invoiceId;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(name = "purchase_order_id")
    private UUID purchaseOrderId;

    @Column(name = "goods_receipt_id")
    private UUID goodsReceiptId;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "invoice_amount")
    private BigDecimal invoiceAmount;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Column(name = "currency_code", columnDefinition = "CHAR(3)")
    private String currencyCode;

    @Column(name = "matching_status")
    private String matchingStatus;

    @Column(name = "invoice_status")
    private String invoiceStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private UUID updatedBy;
}
