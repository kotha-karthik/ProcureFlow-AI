package com.procureflow.procureflowbackend.procurement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "rfq_items", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RfqItem {

    @Id
    @Column(name = "rfq_item_id")
    private UUID rfqItemId;

    @Column(name = "rfq_id")
    private UUID rfqId;

    @Column(name = "request_item_id")
    private UUID requestItemId;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "remarks")
    private String remarks;
}
