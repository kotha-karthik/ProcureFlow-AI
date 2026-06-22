package com.procureflow.procureflowbackend.procurement.repository;

import com.procureflow.procureflowbackend.procurement.entity.RfqVendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RfqVendorRepository extends JpaRepository<RfqVendor, UUID> {
}
