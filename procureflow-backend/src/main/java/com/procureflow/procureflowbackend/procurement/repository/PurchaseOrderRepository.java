package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {
    List<PurchaseOrder> findByOrganizationId(UUID organizationId);
    long countByOrganizationId(UUID organizationId);
    @Query("""
       SELECT COALESCE(SUM(p.totalAmount),0)
       FROM PurchaseOrder p
       WHERE p.organizationId = :organizationId
       """)
    BigDecimal getTotalSpend(UUID organizationId);
    long countByVendorId(UUID vendorId);

    @Query("""
       SELECT COUNT(p)
       FROM PurchaseOrder p
       WHERE YEAR(p.createdAt) = :year
       AND MONTH(p.createdAt) = :month
       AND p.organizationId = :organizationId
       """)
    long countMonthlyPurchaseOrders(
            UUID organizationId,
            int year,
            int month);

    @Query("""
       SELECT COALESCE(SUM(p.totalAmount),0)
       FROM PurchaseOrder p
       WHERE YEAR(p.createdAt) = :year
       AND MONTH(p.createdAt) = :month
       AND p.organizationId = :organizationId
       """)
    BigDecimal getMonthlySpend(
            UUID organizationId,
            int year,
            int month);
}
