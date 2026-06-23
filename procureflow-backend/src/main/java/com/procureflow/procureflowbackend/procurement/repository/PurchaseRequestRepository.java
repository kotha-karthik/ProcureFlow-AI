package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, UUID> {

    List<PurchaseRequest> findByIsDeletedFalse();

    Optional<PurchaseRequest> findByRequestIdAndIsDeletedFalse(UUID requestId);
    long countByOrganizationId(UUID organizationId);

    @Query("""
       SELECT COUNT(pr)
       FROM PurchaseRequest pr
       WHERE YEAR(pr.createdAt)=:year
       AND MONTH(pr.createdAt)=:month
       AND pr.organizationId=:organizationId
       """)
    long countMonthlyPurchaseRequests(
            UUID organizationId,
            int year,
            int month);
}
