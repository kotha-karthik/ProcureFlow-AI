package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.Rfq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RfqRepository extends JpaRepository<Rfq, UUID> {
    List<Rfq> findByOrganizationId(UUID organizationId);
    long countByOrganizationId(UUID organizationId);

    @Query("""
       SELECT COUNT(r)
       FROM Rfq r
       WHERE YEAR(r.createdAt)=:year
       AND MONTH(r.createdAt)=:month
       AND r.organizationId=:organizationId
       """)
    long countMonthlyRfqs(
            UUID organizationId,
            int year,
            int month);
}
