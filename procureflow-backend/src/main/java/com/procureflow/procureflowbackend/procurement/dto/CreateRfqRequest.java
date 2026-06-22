package com.procureflow.procureflowbackend.procurement.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateRfqRequest {
    private UUID purchaseRequestId;

    private String description;

    private LocalDate closingDate;

    private List<UUID> vendorIds;
}
