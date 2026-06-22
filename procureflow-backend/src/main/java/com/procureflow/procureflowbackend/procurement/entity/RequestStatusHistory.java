package com.procureflow.procureflowbackend.procurement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "request_status_history", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestStatusHistory {

    @Id
    @Column(name = "history_id")
    private UUID historyId;

    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "old_status")
    private String oldStatus;

    @Column(name = "new_status")
    private String newStatus;

    @Column(name = "changed_by")
    private UUID changedBy;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @Column(name = "remarks")
    private String remarks;
}
