package com.procureflow.procureflowbackend.finance.repository;

import com.procureflow.procureflowbackend.finance.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByOrganizationId(UUID organizationId);
    long countByOrganizationId(UUID organizationId);
    @Query("""
       SELECT COALESCE(SUM(p.paymentAmount),0)
       FROM Payment p
       WHERE p.organizationId = :organizationId
       """)
    BigDecimal getTotalPaidAmount(UUID organizationId);
    @Query("""
       SELECT COALESCE(SUM(p.paymentAmount),0)
       FROM Payment p
       WHERE p.vendorId = :vendorId
       """)
    BigDecimal getVendorPayments(UUID vendorId);

    @Query("""
       SELECT COUNT(p)
       FROM Payment p
       WHERE YEAR(p.createdAt)=:year
       AND MONTH(p.createdAt)=:month
       AND p.organizationId=:organizationId
       """)
    long countMonthlyPayments(
            UUID organizationId,
            int year,
            int month);
}
