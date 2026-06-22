package com.procureflow.procureflowbackend.procurement.service;

import com.procureflow.procureflowbackend.procurement.dto.PurchaseOrderResponse;
import com.procureflow.procureflowbackend.procurement.entity.PurchaseOrder;
import com.procureflow.procureflowbackend.procurement.repository.PurchaseOrderRepository;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseOrderService {
    private final PurchaseOrderRepository repository;

    public List<PurchaseOrderResponse> getAllPurchaseOrders(CustomUserDetails user)
    {

        return repository
                .findByOrganizationId(
                        user.getOrganizationId())
                .stream()
                .map(po ->
                        PurchaseOrderResponse.builder()
                                .purchaseOrderId(
                                        po.getPurchaseOrderId())
                                .poNumber(
                                        po.getPoNumber())
                                .vendorId(
                                        po.getVendorId())
                                .totalAmount(
                                        po.getTotalAmount())
                                .currencyCode(
                                        po.getCurrencyCode())
                                .status(
                                        po.getStatus())
                                .issueDate(
                                        po.getIssueDate())
                                .expectedDeliveryDate(
                                        po.getExpectedDeliveryDate())
                                .build())
                .toList();
    }

    public PurchaseOrderResponse getPurchaseOrderById(UUID id)
    {

        PurchaseOrder po = repository.findById(id)
                    .orElseThrow(() ->
                                new RuntimeException("Purchase Order not found"));

        return PurchaseOrderResponse.builder()
                .purchaseOrderId(
                        po.getPurchaseOrderId())
                .poNumber(
                        po.getPoNumber())
                .vendorId(
                        po.getVendorId())
                .totalAmount(
                        po.getTotalAmount())
                .currencyCode(
                        po.getCurrencyCode())
                .status(
                        po.getStatus())
                .issueDate(
                        po.getIssueDate())
                .expectedDeliveryDate(
                        po.getExpectedDeliveryDate())
                .build();
    }
}
