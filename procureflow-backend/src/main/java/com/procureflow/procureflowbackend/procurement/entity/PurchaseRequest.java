package com.procureflow.procureflowbackend.procurement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_requests", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {
    @Id
    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "request_number")
    private String requestNumber;

    @Column(name = "requested_by")
    private UUID requestedBy;

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "title")
    private String title;

    @Column(name = "business_justification")
    private String businessJustification;

    @Column(name = "priority")
    private String priority;

    @Column(name = "total_estimated_amount")
    private BigDecimal totalEstimatedAmount;

    @Column(name = "currency_code",columnDefinition = "char(3)")
    private String currencyCode;

    @Column(name = "request_status")
    private String requestStatus;

    @Column(name = "required_by_date")
    private LocalDate requiredByDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "workflow_version_id")
    private UUID workflowVersionId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
