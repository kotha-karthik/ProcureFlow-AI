package com.procureflow.procureflowbackend.procurement.service;


import com.procureflow.procureflowbackend.procurement.dto.CreateGoodsReceiptRequest;
import com.procureflow.procureflowbackend.procurement.dto.GoodsReceiptItemRequest;
import com.procureflow.procureflowbackend.procurement.dto.GoodsReceiptResponse;
import com.procureflow.procureflowbackend.procurement.entity.GoodsReceipt;
import com.procureflow.procureflowbackend.procurement.entity.GoodsReceiptItem;
import com.procureflow.procureflowbackend.procurement.entity.PurchaseOrder;
import com.procureflow.procureflowbackend.procurement.entity.PurchaseOrderItem;
import com.procureflow.procureflowbackend.procurement.repository.GoodsReceiptItemRepository;
import com.procureflow.procureflowbackend.procurement.repository.GoodsReceiptRepository;
import com.procureflow.procureflowbackend.procurement.repository.PurchaseOrderItemRepository;
import com.procureflow.procureflowbackend.procurement.repository.PurchaseOrderRepository;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiptService {
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final GoodsReceiptItemRepository goodsReceiptItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    public GoodsReceiptResponse createReceipt(CreateGoodsReceiptRequest request, CustomUserDetails user)
    {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(
                                request.getPurchaseOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order not found"));

        UUID grnId = UUID.randomUUID();

        String grnNumber = "GRN-" + System.currentTimeMillis();

        List<PurchaseOrderItem> poItems = purchaseOrderItemRepository.findByPurchaseOrderId(purchaseOrder.getPurchaseOrderId());

        String grnStatus = "RECEIVED";

// Status for Purchase Order table
        String poStatus = "FULLY_RECEIVED";

        GoodsReceipt goodsReceipt =
                GoodsReceipt.builder()
                        .goodsReceiptId(grnId)
                        .organizationId(user.getOrganizationId())
                        .grnNumber(grnNumber)
                        .purchaseOrderId(
                                purchaseOrder.getPurchaseOrderId())
                        .vendorId(purchaseOrder.getVendorId())
                        .receivedBy(user.getUserId())
                        .receiptDate(LocalDate.now())
                        .deliveryNoteNumber(
                                request.getDeliveryNoteNumber())
                        .overallStatus("RECEIVED")
                        .remarks(request.getRemarks())
                        .createdAt(LocalDateTime.now())
                        .createdBy(user.getUserId())
                        .build();

        goodsReceiptRepository.save(goodsReceipt);

        for (GoodsReceiptItemRequest item : request.getItems()) {

            PurchaseOrderItem poItem =
                    poItems.stream()
                            .filter(p ->
                                    p.getPurchaseOrderItemId()
                                            .equals(item.getPurchaseOrderItemId()))
                            .findFirst()
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Purchase Order Item not found"));

            if (item.getQuantityReceived()
                    .compareTo(poItem.getQuantity()) < 0) {

                grnStatus = "PARTIALLY_RECEIVED";
                poStatus = "PARTIALLY_RECEIVED";
            }

            GoodsReceiptItem grItem =
                    GoodsReceiptItem.builder()
                            .goodsReceiptItemId(
                                    UUID.randomUUID())
                            .goodsReceiptId(grnId)
                            .purchaseOrderItemId(
                                    poItem.getPurchaseOrderItemId())
                            .quantityOrdered(
                                    poItem.getQuantity())
                            .quantityReceived(
                                    item.getQuantityReceived())
                            .quantityAccepted(
                                    item.getQuantityAccepted())
                            .quantityRejected(
                                    item.getQuantityRejected())
                            .inspectionStatus(
                                    item.getQuantityRejected().compareTo(
                                            java.math.BigDecimal.ZERO) > 0
                                            ? "REJECTED"
                                            : "ACCEPTED")
                            .remarks(item.getRemarks())
                            .createdAt(LocalDateTime.now())
                            .build();

            goodsReceiptItemRepository.save(grItem);
        }

        // Update PO Status
        purchaseOrder.setStatus(poStatus);
        purchaseOrder.setUpdatedAt(LocalDateTime.now());
        purchaseOrder.setUpdatedBy(user.getUserId());

        purchaseOrderRepository.save(purchaseOrder);

        // Update GRN Overall Status
        goodsReceipt.setOverallStatus(grnStatus);
        goodsReceiptRepository.save(goodsReceipt);

        return GoodsReceiptResponse.builder()
                .goodsReceiptId(grnId)
                .grnNumber(grnNumber)
                .purchaseOrderId(
                        purchaseOrder.getPurchaseOrderId())
                .overallStatus(grnStatus)
                .build();
    }
    @Transactional(readOnly = true)
    public List<GoodsReceiptResponse> getAllReceipts(CustomUserDetails user) {

        return goodsReceiptRepository
                .findByOrganizationId(user.getOrganizationId())
                .stream()
                .map(grn ->
                        GoodsReceiptResponse.builder()
                                .goodsReceiptId(grn.getGoodsReceiptId())
                                .grnNumber(grn.getGrnNumber())
                                .purchaseOrderId(grn.getPurchaseOrderId())
                                .overallStatus(grn.getOverallStatus())
                                .build())
                .toList();
    }
    @Transactional(readOnly = true)
    public GoodsReceiptResponse getReceiptById(UUID id) {

        GoodsReceipt grn =
                goodsReceiptRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Goods Receipt not found"));

        return GoodsReceiptResponse.builder()
                .goodsReceiptId(grn.getGoodsReceiptId())
                .grnNumber(grn.getGrnNumber())
                .purchaseOrderId(grn.getPurchaseOrderId())
                .overallStatus(grn.getOverallStatus())
                .build();
    }
}
