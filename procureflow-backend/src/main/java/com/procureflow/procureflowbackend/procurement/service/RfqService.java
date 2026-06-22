package com.procureflow.procureflowbackend.procurement.service;

import com.procureflow.procureflowbackend.procurement.dto.CreateRfqRequest;
import com.procureflow.procureflowbackend.procurement.dto.RfqResponse;
import com.procureflow.procureflowbackend.procurement.entity.*;
import com.procureflow.procureflowbackend.procurement.repository.*;
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
public class RfqService {
    private final RfqRepository rfqRepository;
    private final RfqItemRepository rfqItemRepository;
    private final RfqVendorRepository rfqVendorRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final RequestItemRepository requestItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final VendorQuotationRepository quotationRepository;
    private final QuotationItemRepository quotationItemRepository;

    public RfqResponse createRfq(CreateRfqRequest request, CustomUserDetails user)
    {

        PurchaseRequest purchaseRequest =
                purchaseRequestRepository
                        .findById(request.getPurchaseRequestId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Request not found"));

        if (!"APPROVED".equals(
                purchaseRequest.getRequestStatus())) {

            throw new RuntimeException(
                    "RFQ can only be created for APPROVED Purchase Requests");
        }

        UUID rfqId = UUID.randomUUID();

        String rfqNumber = "RFQ-" + System.currentTimeMillis();

        Rfq rfq = Rfq.builder()
                .rfqId(rfqId)
                .organizationId(user.getOrganizationId())
                .purchaseRequestId(
                        request.getPurchaseRequestId())
                .rfqNumber(rfqNumber)
                .title(purchaseRequest.getTitle())
                .description(request.getDescription())
                .issueDate(LocalDate.now())
                .closingDate(request.getClosingDate())
                .status("DRAFT")
                .createdBy(user.getUserId())
                .createdAt(LocalDateTime.now())
                .build();

        rfqRepository.save(rfq);

        // Copy PR Items to RFQ Items
        requestItemRepository
                .findByRequestId(
                        request.getPurchaseRequestId())
                .forEach(item -> {

                    RfqItem rfqItem = RfqItem.builder()
                            .rfqItemId(UUID.randomUUID())
                            .rfqId(rfqId)
                            .requestItemId(
                                    item.getRequestItemId())
                            .quantity(item.getQuantity())
                            .remarks(null)
                            .build();

                    rfqItemRepository.save(rfqItem);
                });

        // Assign Vendors
        request.getVendorIds()
                .forEach(vendorId -> {

                    RfqVendor rfqVendor =
                            RfqVendor.builder()
                                    .rfqVendorId(
                                            UUID.randomUUID())
                                    .rfqId(rfqId)
                                    .vendorId(vendorId)
                                    .responseStatus("INVITED")
                                    .build();

                    rfqVendorRepository.save(rfqVendor);
                });

        return RfqResponse.builder()
                .rfqId(rfqId)
                .rfqNumber(rfqNumber)
                .title(rfq.getTitle())
                .status(rfq.getStatus())
                .issueDate(rfq.getIssueDate())
                .closingDate(rfq.getClosingDate())
                .build();
    }

    public List<RfqResponse> getAllRfqs(CustomUserDetails user)
    {

        return rfqRepository
                .findByOrganizationId(
                        user.getOrganizationId())
                .stream()
                .map(rfq ->
                        RfqResponse.builder()
                                .rfqId(rfq.getRfqId())
                                .rfqNumber(rfq.getRfqNumber())
                                .title(rfq.getTitle())
                                .status(rfq.getStatus())
                                .issueDate(rfq.getIssueDate())
                                .closingDate(rfq.getClosingDate())
                                .build())
                .toList();
    }

    public RfqResponse getRfqById(UUID rfqId) {

        Rfq rfq = rfqRepository.findById(rfqId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "RFQ not found"));

        return RfqResponse.builder()
                .rfqId(rfq.getRfqId())
                .rfqNumber(rfq.getRfqNumber())
                .title(rfq.getTitle())
                .status(rfq.getStatus())
                .issueDate(rfq.getIssueDate())
                .closingDate(rfq.getClosingDate())
                .build();
    }
    public RfqResponse sendRfq(UUID rfqId) {

        Rfq rfq = rfqRepository.findById(rfqId)
                .orElseThrow(() ->
                        new RuntimeException("RFQ not found"));

        if (!"DRAFT".equals(rfq.getStatus())) {

            throw new RuntimeException("Only DRAFT RFQs can be published");
        }

        rfq.setStatus("PUBLISHED");

        rfqRepository.save(rfq);

        return RfqResponse.builder()
                .rfqId(rfq.getRfqId())
                .rfqNumber(rfq.getRfqNumber())
                .title(rfq.getTitle())
                .status(rfq.getStatus())
                .issueDate(rfq.getIssueDate())
                .closingDate(rfq.getClosingDate())
                .build();
    }

    public String awardRfq(UUID rfqId, UUID quotationId, CustomUserDetails user)
    {
        Rfq rfq = rfqRepository.findById(rfqId)
                .orElseThrow(() ->
                        new RuntimeException("RFQ not found"));

        if (!"PUBLISHED".equals(rfq.getStatus())) {

            throw new RuntimeException(
                    "Only PUBLISHED RFQs can be awarded");
        }

        VendorQuotation quotation =
                quotationRepository.findById(quotationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Quotation not found"));

        // Update RFQ Status
        rfq.setStatus("AWARDED");
        rfqRepository.save(rfq);

        // Create Purchase Order

        UUID poId = UUID.randomUUID();

        PurchaseOrder po =
                PurchaseOrder.builder()
                        .purchaseOrderId(poId)
                        .organizationId(
                                user.getOrganizationId())
                        .poNumber(
                                "PO-" + System.currentTimeMillis())
                        .quotationId(
                                quotation.getQuotationId())
                        .vendorId(
                                quotation.getVendorId())
                        .purchaseRequestId(
                                rfq.getPurchaseRequestId())
                        .totalAmount(
                                quotation.getTotalAmount())
                        .currencyCode(
                                quotation.getCurrencyCode())
                        .issueDate(LocalDate.now())
                        .expectedDeliveryDate(
                                LocalDate.now()
                                        .plusDays(
                                                quotation.getDeliveryDays()))
                        .status("CREATED")
                        .createdBy(user.getUserId())
                        .createdAt(LocalDateTime.now())
                        .remarks("Auto-generated from RFQ")
                        .build();

        purchaseOrderRepository.save(po);

        // Copy quotation items to PO items

        quotationItemRepository
                .findByQuotationId(quotationId)
                .forEach(item -> {

                    PurchaseOrderItem poItem =
                            PurchaseOrderItem.builder()
                                    .purchaseOrderItemId(
                                            UUID.randomUUID())
                                    .purchaseOrderId(poId)
                                    .quotationItemId(
                                            item.getQuotationItemId())
                                    .itemDescription(
                                            item.getRemarks())
                                    .quantity(item.getQuantity())
                                    .unitPrice(item.getUnitPrice())
                                    .totalPrice(item.getTotalPrice())
                                    .createdAt(LocalDateTime.now())
                                    .build();

                    purchaseOrderItemRepository.save(poItem);
                });

        return "RFQ Awarded Successfully. Purchase Order Generated.";
    }
}
