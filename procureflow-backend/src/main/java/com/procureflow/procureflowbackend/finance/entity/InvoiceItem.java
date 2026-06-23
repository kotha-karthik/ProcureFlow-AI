package com.procureflow.procureflowbackend.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "invoice_items", schema = "finance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItem {
    @Id
    @Column(name = "invoice_item_id")
    private UUID invoiceItemId;

    @Column(name = "invoice_id")
    private UUID invoiceId;

    @Column(name = "purchase_order_item_id")
    private UUID purchaseOrderItemId;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "line_amount")
    private BigDecimal lineAmount;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
