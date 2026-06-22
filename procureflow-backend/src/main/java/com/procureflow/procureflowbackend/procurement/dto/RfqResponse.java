package com.procureflow.procureflowbackend.procurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfqResponse {
    private UUID rfqId;
    private String rfqNumber;
    private String title;
    private String status;
    private LocalDate issueDate;
    private LocalDate closingDate;
}
