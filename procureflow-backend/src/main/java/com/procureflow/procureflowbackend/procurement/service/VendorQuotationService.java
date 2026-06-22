package com.procureflow.procureflowbackend.procurement.service;


import com.procureflow.procureflowbackend.procurement.dto.CreateQuotationRequest;
import com.procureflow.procureflowbackend.procurement.dto.QuotationItemRequest;
import com.procureflow.procureflowbackend.procurement.dto.VendorQuotationResponse;
import com.procureflow.procureflowbackend.procurement.entity.QuotationItem;
import com.procureflow.procureflowbackend.procurement.entity.VendorQuotation;
import com.procureflow.procureflowbackend.procurement.repository.QuotationItemRepository;
import com.procureflow.procureflowbackend.procurement.repository.VendorQuotationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorQuotationService {
    private final VendorQuotationRepository quotationRepository;
    private final QuotationItemRepository quotationItemRepository;

    public VendorQuotationResponse createQuotation(CreateQuotationRequest request)
    {

        UUID quotationId = UUID.randomUUID();

        String quotationNumber =
                "QUO-" + System.currentTimeMillis();

        BigDecimal totalAmount =
                request.getItems()
                        .stream()
                        .map(item ->
                                item.getUnitPrice()
                                        .multiply(item.getQuantity()))
                        .reduce(BigDecimal.ZERO,
                                BigDecimal::add);

        VendorQuotation quotation =
                VendorQuotation.builder()
                        .quotationId(quotationId)
                        .rfqId(request.getRfqId())
                        .vendorId(request.getVendorId())
                        .quotationNumber(quotationNumber)
                        .quotationDate(LocalDate.now())
                        .totalAmount(totalAmount)
                        .currencyCode(request.getCurrencyCode())
                        .deliveryDays(request.getDeliveryDays())
                        .warrantyMonths(
                                request.getWarrantyMonths())
                        .validityDate(
                                request.getValidityDate())
                        .status("SUBMITTED")
                        .submittedAt(LocalDateTime.now())
                        .build();

        quotationRepository.save(quotation);

        for (QuotationItemRequest item :
                request.getItems()) {

            QuotationItem quotationItem =
                    QuotationItem.builder()
                            .quotationItemId(
                                    UUID.randomUUID())
                            .quotationId(quotationId)
                            .rfqItemId(item.getRfqItemId())
                            .unitPrice(item.getUnitPrice())
                            .quantity(item.getQuantity())
                            .totalPrice(
                                    item.getUnitPrice()
                                            .multiply(
                                                    item.getQuantity()))
                            .remarks(item.getRemarks())
                            .build();

            quotationItemRepository.save(
                    quotationItem);
        }

        return VendorQuotationResponse.builder()
                .quotationId(quotationId)
                .quotationNumber(quotationNumber)
                .totalAmount(totalAmount)
                .status("SUBMITTED")
                .build();
    }

    public List<VendorQuotationResponse> getQuotationsByRfq(UUID rfqId)
    {

        return quotationRepository.findByRfqId(rfqId)
                .stream()
                .map(quotation ->
                        VendorQuotationResponse.builder()
                                .quotationId(
                                        quotation.getQuotationId())
                                .quotationNumber(
                                        quotation.getQuotationNumber())
                                .totalAmount(
                                        quotation.getTotalAmount())
                                .status(
                                        quotation.getStatus())
                                .build())
                .toList();
    }

    public List<VendorQuotationResponse> compareQuotations(UUID rfqId) {

        return quotationRepository.findByRfqId(rfqId)
                .stream()
                .sorted(Comparator.comparing(
                        VendorQuotation::getTotalAmount))
                .map(quotation ->
                        VendorQuotationResponse.builder()
                                .quotationId(
                                        quotation.getQuotationId())
                                .quotationNumber(
                                        quotation.getQuotationNumber())
                                .totalAmount(
                                        quotation.getTotalAmount())
                                .status(
                                        quotation.getStatus())
                                .build())
                .toList();
    }
}
