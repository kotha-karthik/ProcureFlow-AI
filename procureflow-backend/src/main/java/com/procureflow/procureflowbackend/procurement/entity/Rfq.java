package com.procureflow.procureflowbackend.procurement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rfqs", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rfq {
    @Id
    @Column(name = "rfq_id")
    private UUID rfqId;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "purchase_request_id")
    private UUID purchaseRequestId;

    @Column(name = "rfq_number")
    private String rfqNumber;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
