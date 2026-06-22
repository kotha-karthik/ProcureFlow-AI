package com.procureflow.procureflowbackend.procurement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rfq_vendors", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RfqVendor {
    @Id
    @Column(name = "rfq_vendor_id")
    private UUID rfqVendorId;

    @Column(name = "rfq_id")
    private UUID rfqId;

    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(name = "invitation_sent_at")
    private LocalDateTime invitationSentAt;

    @Column(name = "response_status")
    private String responseStatus;
}
