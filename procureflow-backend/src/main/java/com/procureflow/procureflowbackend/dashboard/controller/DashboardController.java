package com.procureflow.procureflowbackend.dashboard.controller;

import com.procureflow.procureflowbackend.dashboard.dto.DashboardSummaryResponse;
import com.procureflow.procureflowbackend.dashboard.dto.MonthlyReportResponse;
import com.procureflow.procureflowbackend.dashboard.dto.SpendAnalysisResponse;
import com.procureflow.procureflowbackend.dashboard.dto.VendorPerformanceResponse;
import com.procureflow.procureflowbackend.dashboard.service.DashboardService;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService service;

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary(Authentication authentication) {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.getSummary(user);
    }
    @GetMapping("/spend-analysis")
    public SpendAnalysisResponse getSpendAnalysis(Authentication authentication) {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return service.getSpendAnalysis(user);
    }
    @GetMapping("/vendor-performance")
    public List<VendorPerformanceResponse> getVendorPerformance() {

        return service.getVendorPerformance();
    }

    @GetMapping("/monthly-report")
    public MonthlyReportResponse getMonthlyReport(
            Authentication authentication) {

        CustomUserDetails user =
                (CustomUserDetails)
                        authentication.getPrincipal();

        return service.getMonthlyReport(user);
    }
}
