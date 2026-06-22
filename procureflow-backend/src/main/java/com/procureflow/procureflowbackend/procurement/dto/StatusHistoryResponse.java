package com.procureflow.procureflowbackend.procurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusHistoryResponse {
    private String oldStatus;
    private String newStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String remarks;
}
