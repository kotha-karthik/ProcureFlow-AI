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
@Table(name = "request_items", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestItem {
    @Id
    @Column(name = "request_item_id")
    private UUID requestItemId;

    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "estimated_unit_price")
    private BigDecimal estimatedUnitPrice;

    @Column(name = "estimated_total_price")
    private BigDecimal estimatedTotalPrice;

    @Column(name = "category")
    private String category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
