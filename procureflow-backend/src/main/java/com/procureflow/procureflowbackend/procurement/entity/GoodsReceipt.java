package com.procureflow.procureflowbackend.procurement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "goods_receipts", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsReceipt {
    @Id
    @Column(name = "goods_receipt_id")
    private UUID goodsReceiptId;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "grn_number")
    private String grnNumber;

    @Column(name = "purchase_order_id")
    private UUID purchaseOrderId;

    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(name = "received_by")
    private UUID receivedBy;

    @Column(name = "receipt_date")
    private LocalDate receiptDate;

    @Column(name = "delivery_note_number")
    private String deliveryNoteNumber;

    @Column(name = "overall_status")
    private String overallStatus;

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
