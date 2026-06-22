package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.RfqItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RfqItemRepository extends JpaRepository<RfqItem, UUID> {
}
