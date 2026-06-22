package com.procureflow.procureflowbackend.vendor.repository;

import com.procureflow.procureflowbackend.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    List<Vendor> findByIsDeletedFalse();
}
