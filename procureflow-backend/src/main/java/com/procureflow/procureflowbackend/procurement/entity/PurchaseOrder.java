package com.procureflow.procureflowbackend.procurement.entity;


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
@Table(name = "purchase_orders", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    @Column(name = "purchase_order_id")
    private UUID purchaseOrderId;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "quotation_id")
    private UUID quotationId;

    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(name = "purchase_request_id")
    private UUID purchaseRequestId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "currency_code", columnDefinition = "CHAR(3)")
    private String currencyCode;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "remarks")
    private String remarks;
}
