package com.procureflow.procureflowbackend.procurement.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreatePurchaseRequestRequest {

    private String title;

    private String businessJustification;

    private String priority;

    private String currencyCode;

    private LocalDate requiredByDate;

    private List<RequestItemRequest> items;
}
