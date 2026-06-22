package com.procureflow.procureflowbackend.procurement.service;


import com.procureflow.procureflowbackend.procurement.dto.CreatePurchaseRequestRequest;
import com.procureflow.procureflowbackend.procurement.dto.PurchaseRequestResponse;
import com.procureflow.procureflowbackend.procurement.dto.RequestItemRequest;
import com.procureflow.procureflowbackend.procurement.entity.PurchaseRequest;
import com.procureflow.procureflowbackend.procurement.entity.RequestItem;
import com.procureflow.procureflowbackend.procurement.repository.PurchaseRequestRepository;
import com.procureflow.procureflowbackend.procurement.repository.RequestItemRepository;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseRequestService {
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final RequestItemRepository requestItemRepository;

    public PurchaseRequestResponse createPurchaseRequest(
            CreatePurchaseRequestRequest request,
            CustomUserDetails user
    ) {

        UUID requestId = UUID.randomUUID();

        String requestNumber =
                "PR-" + System.currentTimeMillis();

        BigDecimal totalAmount = request.getItems()
                .stream()
                .map(item ->
                        item.getEstimatedUnitPrice()
                                .multiply(item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PurchaseRequest purchaseRequest =
                PurchaseRequest.builder()
                        .requestId(requestId)
                        .organizationId(user.getOrganizationId())
                        .requestNumber(requestNumber)
                        .requestedBy(user.getUserId())
                        .departmentId(UUID.fromString("A7888D24-5F65-F111-9392-00410E66DCC2"))
                        .workflowVersionId(
                                UUID.fromString("8A499C75-6065-F111-9392-00410E66DCC2")
                        )
                        .title(request.getTitle())
                        .businessJustification(
                                request.getBusinessJustification())
                        .priority(request.getPriority())
                        .currencyCode(request.getCurrencyCode())
                        .requestStatus("DRAFT")
                        .requiredByDate(request.getRequiredByDate())
                        .totalEstimatedAmount(totalAmount)
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .build();

        purchaseRequestRepository.save(purchaseRequest);

        for (RequestItemRequest item : request.getItems()) {

            RequestItem requestItem =
                    RequestItem.builder()
                            .requestItemId(UUID.randomUUID())
                            .requestId(requestId)
                            .itemName(item.getItemName())
                            .itemDescription(item.getItemDescription())
                            .quantity(item.getQuantity())
                            .unitOfMeasure(item.getUnitOfMeasure())
                            .estimatedUnitPrice(
                                    item.getEstimatedUnitPrice())
                            .estimatedTotalPrice(
                                    item.getEstimatedUnitPrice()
                                            .multiply(item.getQuantity()))
                            .category(item.getCategory())
                            .createdAt(LocalDateTime.now())
                            .build();

            requestItemRepository.save(requestItem);
        }

        return PurchaseRequestResponse.builder()
                .requestId(requestId)
                .requestNumber(requestNumber)
                .title(request.getTitle())
                .requestStatus("DRAFT")
                .totalEstimatedAmount(totalAmount)
                .build();
    }
}
