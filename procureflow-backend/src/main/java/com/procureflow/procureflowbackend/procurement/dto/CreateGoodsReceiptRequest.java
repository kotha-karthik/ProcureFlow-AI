package com.procureflow.procureflowbackend.procurement.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateGoodsReceiptRequest {
    private UUID purchaseOrderId;
    private String deliveryNoteNumber;
    private String remarks;

    private List<GoodsReceiptItemRequest> items;
}
