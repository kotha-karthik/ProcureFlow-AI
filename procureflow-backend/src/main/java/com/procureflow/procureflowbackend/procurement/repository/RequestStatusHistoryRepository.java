package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.RequestStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestStatusHistoryRepository extends JpaRepository<RequestStatusHistory, UUID> {
    List<RequestStatusHistory> findByRequestIdOrderByChangedAtAsc(UUID requestId);
}
