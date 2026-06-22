package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.Rfq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RfqRepository extends JpaRepository<Rfq, UUID> {
    List<Rfq> findByOrganizationId(UUID organizationId);
}
