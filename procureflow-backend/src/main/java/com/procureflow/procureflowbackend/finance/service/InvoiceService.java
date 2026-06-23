package com.procureflow.procureflowbackend.finance.service;

import com.procureflow.procureflowbackend.finance.dto.CreateInvoiceRequest;
import com.procureflow.procureflowbackend.finance.dto.InvoiceItemRequest;
import com.procureflow.procureflowbackend.finance.dto.InvoiceResponse;
import com.procureflow.procureflowbackend.finance.entity.Invoice;
import com.procureflow.procureflowbackend.finance.entity.InvoiceItem;
import com.procureflow.procureflowbackend.finance.repository.InvoiceItemRepository;
import com.procureflow.procureflowbackend.finance.repository.InvoiceRepository;
import com.procureflow.procureflowbackend.procurement.entity.PurchaseOrder;
import com.procureflow.procureflowbackend.procurement.repository.PurchaseOrderRepository;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public InvoiceResponse createInvoice(CreateInvoiceRequest request, CustomUserDetails user)
    {

        // Load Purchase Order
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(
                                request.getPurchaseOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order not found"));

        UUID invoiceId = UUID.randomUUID();

        String invoiceNumber = "INV-" + System.currentTimeMillis();

        // Calculate Invoice Amount

        BigDecimal invoiceAmount = request.getItems()
                        .stream()
                        .map(item ->
                                item.getUnitPrice()
                                        .multiply(item.getQuantity()))
                        .reduce(BigDecimal.ZERO,
                                BigDecimal::add);

        BigDecimal taxAmount = request.getTaxAmount() == null
                        ? BigDecimal.ZERO
                        : request.getTaxAmount();

        BigDecimal discountAmount = request.getDiscountAmount() == null
                        ? BigDecimal.ZERO
                        : request.getDiscountAmount();

        BigDecimal netAmount = invoiceAmount
                        .add(taxAmount)
                        .subtract(discountAmount);

        // Basic 3-Way Matching

        String matchingStatus = "MATCHED";
        String invoiceStatus = "APPROVED";

        if (netAmount.compareTo(
                purchaseOrder.getTotalAmount()) != 0) {

            matchingStatus = "MISMATCH";
            invoiceStatus = "REJECTED";
        }

        // Create Invoice

        Invoice invoice = Invoice.builder()
                        .invoiceId(invoiceId)
                        .organizationId(
                                user.getOrganizationId())
                        .invoiceNumber(invoiceNumber)
                        .vendorId(request.getVendorId())
                        .purchaseOrderId(
                                request.getPurchaseOrderId())
                        .goodsReceiptId(
                                request.getGoodsReceiptId())
                        .invoiceDate(LocalDate.now())
                        .dueDate(request.getDueDate())
                        .invoiceAmount(invoiceAmount)
                        .taxAmount(taxAmount)
                        .discountAmount(discountAmount)
                        .netAmount(netAmount)
                        .currencyCode(
                                request.getCurrencyCode())
                        .matchingStatus(
                                matchingStatus)
                        .invoiceStatus(
                                invoiceStatus)
                        .remarks(request.getRemarks())
                        .createdAt(LocalDateTime.now())
                        .createdBy(user.getUserId())
                        .build();

        invoiceRepository.save(invoice);

        // Save Invoice Items

        for (InvoiceItemRequest item : request.getItems()) {

            BigDecimal lineAmount =
                    item.getUnitPrice()
                            .multiply(item.getQuantity());

            InvoiceItem invoiceItem =
                    InvoiceItem.builder()
                            .invoiceItemId(
                                    UUID.randomUUID())
                            .invoiceId(invoiceId)
                            .purchaseOrderItemId(
                                    item.getPurchaseOrderItemId())
                            .description(
                                    item.getDescription())
                            .quantity(
                                    item.getQuantity())
                            .unitPrice(
                                    item.getUnitPrice())
                            .lineAmount(
                                    lineAmount)
                            .taxAmount(
                                    item.getTaxAmount())
                            .createdAt(
                                    LocalDateTime.now())
                            .build();

            invoiceItemRepository.save(invoiceItem);
        }

        return InvoiceResponse.builder()
                .invoiceId(invoiceId)
                .invoiceNumber(invoiceNumber)
                .netAmount(netAmount)
                .matchingStatus(matchingStatus)
                .invoiceStatus(invoiceStatus)
                .build();
    }

    @Transactional(readOnly = true)
    public java.util.List<InvoiceResponse> getAllInvoices(CustomUserDetails user)
    {

        return invoiceRepository
                .findByOrganizationId(
                        user.getOrganizationId())
                .stream()
                .map(invoice ->
                        InvoiceResponse.builder()
                                .invoiceId(
                                        invoice.getInvoiceId())
                                .invoiceNumber(
                                        invoice.getInvoiceNumber())
                                .netAmount(
                                        invoice.getNetAmount())
                                .matchingStatus(
                                        invoice.getMatchingStatus())
                                .invoiceStatus(
                                        invoice.getInvoiceStatus())
                                .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public InvoiceResponse getInvoiceById(UUID invoiceId)
    {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice not found"));

        return InvoiceResponse.builder()
                .invoiceId(invoice.getInvoiceId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .netAmount(invoice.getNetAmount())
                .matchingStatus(invoice.getMatchingStatus())
                .invoiceStatus(invoice.getInvoiceStatus())
                .build();
    }
}
