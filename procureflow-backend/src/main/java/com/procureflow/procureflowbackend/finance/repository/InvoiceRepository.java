package com.procureflow.procureflowbackend.finance.repository;

import com.procureflow.procureflowbackend.finance.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    List<Invoice> findByOrganizationId(UUID organizationId);
    long countByOrganizationId(UUID organizationId);

    @Query("""
       SELECT COUNT(i)
       FROM Invoice i
       WHERE YEAR(i.createdAt)=:year
       AND MONTH(i.createdAt)=:month
       AND i.organizationId=:organizationId
       """)
    long countMonthlyInvoices(
            UUID organizationId,
            int year,
            int month);
}
