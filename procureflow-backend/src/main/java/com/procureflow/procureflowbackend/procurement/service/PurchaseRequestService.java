package com.procureflow.procureflowbackend.procurement.service;


import com.procureflow.procureflowbackend.procurement.dto.*;
import com.procureflow.procureflowbackend.procurement.entity.PurchaseRequest;
import com.procureflow.procureflowbackend.procurement.entity.RequestItem;
import com.procureflow.procureflowbackend.procurement.entity.RequestStatusHistory;
import com.procureflow.procureflowbackend.procurement.repository.PurchaseRequestRepository;
import com.procureflow.procureflowbackend.procurement.repository.RequestItemRepository;
import com.procureflow.procureflowbackend.procurement.repository.RequestStatusHistoryRepository;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseRequestService {
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final RequestItemRepository requestItemRepository;
    private final RequestStatusHistoryRepository historyRepository;

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
        saveStatusHistory(requestId, null, "DRAFT", user.getUserId(), "Purchase Request Created");

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

    public List<PurchaseRequestResponse> getAllPurchaseRequests() {

        return purchaseRequestRepository
                .findByIsDeletedFalse()
                .stream()
                .map(pr -> PurchaseRequestResponse.builder()
                        .requestId(pr.getRequestId())
                        .requestNumber(pr.getRequestNumber())
                        .title(pr.getTitle())
                        .requestStatus(pr.getRequestStatus())
                        .totalEstimatedAmount(pr.getTotalEstimatedAmount())
                        .build())
                .toList();
    }

    public PurchaseRequestResponse getPurchaseRequestById(UUID requestId) {

        PurchaseRequest purchaseRequest = purchaseRequestRepository
                .findById(requestId)
                .orElseThrow(() ->
                        new RuntimeException("Purchase Request not found"));

        List<RequestItemResponse> itemResponses =
                requestItemRepository.findByRequestId(requestId)
                        .stream()
                        .map(item -> RequestItemResponse.builder()
                                .itemName(item.getItemName())
                                .itemDescription(item.getItemDescription())
                                .quantity(item.getQuantity())
                                .unitOfMeasure(item.getUnitOfMeasure())
                                .estimatedUnitPrice(item.getEstimatedUnitPrice())
                                .estimatedTotalPrice(item.getEstimatedTotalPrice())
                                .category(item.getCategory())
                                .build())
                        .toList();

        return PurchaseRequestResponse.builder()
                .requestId(purchaseRequest.getRequestId())
                .requestNumber(purchaseRequest.getRequestNumber())
                .title(purchaseRequest.getTitle())
                .businessJustification(
                        purchaseRequest.getBusinessJustification())
                .priority(purchaseRequest.getPriority())
                .currencyCode(purchaseRequest.getCurrencyCode())
                .requestStatus(purchaseRequest.getRequestStatus())
                .requiredByDate(purchaseRequest.getRequiredByDate())
                .totalEstimatedAmount(
                        purchaseRequest.getTotalEstimatedAmount())
                .items(itemResponses)
                .build();
    }

    public PurchaseRequestResponse submitPurchaseRequest(UUID requestId,CustomUserDetails user) {

        PurchaseRequest purchaseRequest =
                purchaseRequestRepository
                        .findByRequestIdAndIsDeletedFalse(requestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Request not found"));

        if (!"DRAFT".equals(purchaseRequest.getRequestStatus())) {
            throw new RuntimeException(
                    "Only DRAFT requests can be submitted");
        }

        purchaseRequest.setRequestStatus("SUBMITTED");
        saveStatusHistory(requestId, "DRAFT", "SUBMITTED", user.getUserId(), "Purchase Request Submitted");
        purchaseRequest.setSubmittedAt(LocalDateTime.now());

        purchaseRequestRepository.save(purchaseRequest);

        return PurchaseRequestResponse.builder()
                .requestId(purchaseRequest.getRequestId())
                .requestNumber(purchaseRequest.getRequestNumber())
                .title(purchaseRequest.getTitle())
                .requestStatus(purchaseRequest.getRequestStatus())
                .totalEstimatedAmount(
                        purchaseRequest.getTotalEstimatedAmount())
                .build();
    }

    public PurchaseRequestResponse approvePurchaseRequest(UUID requestId,CustomUserDetails user) {

        PurchaseRequest purchaseRequest =
                purchaseRequestRepository
                        .findByRequestIdAndIsDeletedFalse(requestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Request not found"));

        if (!"SUBMITTED".equals(purchaseRequest.getRequestStatus())) {
            throw new RuntimeException(
                    "Only SUBMITTED requests can be approved");
        }

        purchaseRequest.setRequestStatus("APPROVED");
        saveStatusHistory(requestId, "SUBMITTED", "APPROVED", user.getUserId(), "Purchase Request Approved");
        purchaseRequest.setCompletedAt(LocalDateTime.now());

        purchaseRequestRepository.save(purchaseRequest);

        return PurchaseRequestResponse.builder()
                .requestId(purchaseRequest.getRequestId())
                .requestNumber(purchaseRequest.getRequestNumber())
                .title(purchaseRequest.getTitle())
                .requestStatus(purchaseRequest.getRequestStatus())
                .totalEstimatedAmount(
                        purchaseRequest.getTotalEstimatedAmount())
                .build();
    }

    private  void saveStatusHistory(UUID requestId, String oldStatus, String newStatus, UUID changedBy, String remarks)
    {
        RequestStatusHistory history =
                RequestStatusHistory.builder()
                        .historyId(UUID.randomUUID())
                        .requestId(requestId)
                        .oldStatus(oldStatus)
                        .newStatus(newStatus)
                        .changedBy(changedBy)
                        .changedAt(LocalDateTime.now())
                        .remarks(remarks)
                        .build();

        historyRepository.save(history);
    }

    public List<StatusHistoryResponse> getStatusHistory(UUID requestId) {

        return historyRepository
                .findByRequestIdOrderByChangedAtAsc(requestId)
                .stream()
                .map(history ->
                        StatusHistoryResponse.builder()
                                .oldStatus(history.getOldStatus())
                                .newStatus(history.getNewStatus())
                                .changedBy(history.getChangedBy())
                                .changedAt(history.getChangedAt())
                                .remarks(history.getRemarks())
                                .build())
                .toList();
    }
}
