package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.VendorQuotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorQuotationRepository extends JpaRepository<VendorQuotation, UUID> {

    List<VendorQuotation> findByRfqId(UUID rfqId);
    Optional<VendorQuotation> findById(UUID quotationId);

}
