package com.procureflow.procureflowbackend.procurement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "goods_receipt_items", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsReceiptItem {
    @Id
    @Column(name = "goods_receipt_item_id")
    private UUID goodsReceiptItemId;

    @Column(name = "goods_receipt_id")
    private UUID goodsReceiptId;

    @Column(name = "purchase_order_item_id")
    private UUID purchaseOrderItemId;

    @Column(name = "quantity_ordered")
    private BigDecimal quantityOrdered;

    @Column(name = "quantity_received")
    private BigDecimal quantityReceived;

    @Column(name = "quantity_accepted")
    private BigDecimal quantityAccepted;

    @Column(name = "quantity_rejected")
    private BigDecimal quantityRejected;

    @Column(name = "inspection_status")
    private String inspectionStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
