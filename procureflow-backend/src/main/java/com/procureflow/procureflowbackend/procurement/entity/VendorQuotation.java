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
@Table(name = "vendor_quotations", schema = "procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorQuotation {
    @Id
    @Column(name = "quotation_id")
    private UUID quotationId;

    @Column(name = "rfq_id")
    private UUID rfqId;

    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(name = "quotation_number")
    private String quotationNumber;

    @Column(name = "quotation_date")
    private LocalDate quotationDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "currency_code",columnDefinition = "CHAR(3)")
    private String currencyCode;

    @Column(name = "delivery_days")
    private Integer deliveryDays;

    @Column(name = "warranty_months")
    private Integer warrantyMonths;

    @Column(name = "validity_date")
    private LocalDate validityDate;

    @Column(name = "status")
    private String status;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
