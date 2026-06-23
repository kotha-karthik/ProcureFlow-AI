package com.procureflow.procureflowbackend.dashboard.service;

import com.procureflow.procureflowbackend.dashboard.dto.DashboardSummaryResponse;
import com.procureflow.procureflowbackend.dashboard.dto.MonthlyReportResponse;
import com.procureflow.procureflowbackend.dashboard.dto.SpendAnalysisResponse;
import com.procureflow.procureflowbackend.dashboard.dto.VendorPerformanceResponse;
import com.procureflow.procureflowbackend.finance.repository.InvoiceRepository;
import com.procureflow.procureflowbackend.finance.repository.PaymentRepository;
import com.procureflow.procureflowbackend.procurement.repository.*;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import com.procureflow.procureflowbackend.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final VendorRepository vendorRepository;
    private final RfqRepository rfqRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final VendorQuotationRepository quotationRepository;

    public DashboardSummaryResponse getSummary(CustomUserDetails user) {

        return DashboardSummaryResponse.builder()
                .purchaseRequests(
                        purchaseRequestRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .vendors(
                        vendorRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .rfqs(
                        rfqRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .purchaseOrders(
                        purchaseOrderRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .goodsReceipts(
                        goodsReceiptRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .invoices(
                        invoiceRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .payments(
                        paymentRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .build();
    }
    public SpendAnalysisResponse getSpendAnalysis(CustomUserDetails user) {

        return SpendAnalysisResponse.builder()
                .totalPurchaseOrders(
                        purchaseOrderRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .totalSpend(
                        purchaseOrderRepository
                                .getTotalSpend(
                                        user.getOrganizationId()))
                .totalInvoices(
                        invoiceRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .totalPayments(
                        paymentRepository
                                .countByOrganizationId(
                                        user.getOrganizationId()))
                .totalPaidAmount(
                        paymentRepository
                                .getTotalPaidAmount(
                                        user.getOrganizationId()))
                .build();
    }

    public List<VendorPerformanceResponse> getVendorPerformance() {

        return vendorRepository.findAll()
                .stream()
                .map(vendor ->

                        VendorPerformanceResponse.builder()
                                .vendorId(
                                        vendor.getVendorId())
                                .vendorName(
                                        vendor.getVendorName())
                                .totalQuotations(
                                        quotationRepository
                                                .countByVendorId(
                                                        vendor.getVendorId()))
                                .totalPurchaseOrders(
                                        purchaseOrderRepository
                                                .countByVendorId(
                                                        vendor.getVendorId()))
                                .totalPaymentsReceived(
                                        paymentRepository
                                                .getVendorPayments(
                                                        vendor.getVendorId()))
                                .performanceRating(
                                        vendor.getPerformanceRating())
                                .build()

                )
                .toList();
    }

    public MonthlyReportResponse getMonthlyReport(CustomUserDetails user) {

        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();

        return MonthlyReportResponse.builder()
                .month(today.getMonth().toString()
                        + "-" + year)

                .purchaseRequests(
                        purchaseRequestRepository
                                .countMonthlyPurchaseRequests(
                                        user.getOrganizationId(),
                                        year,
                                        month))

                .rfqs(
                        rfqRepository
                                .countMonthlyRfqs(
                                        user.getOrganizationId(),
                                        year,
                                        month))

                .purchaseOrders(
                        purchaseOrderRepository
                                .countMonthlyPurchaseOrders(
                                        user.getOrganizationId(),
                                        year,
                                        month))

                .invoices(
                        invoiceRepository
                                .countMonthlyInvoices(
                                        user.getOrganizationId(),
                                        year,
                                        month))

                .payments(
                        paymentRepository
                                .countMonthlyPayments(
                                        user.getOrganizationId(),
                                        year,
                                        month))

                .monthlySpend(
                        purchaseOrderRepository
                                .getMonthlySpend(
                                        user.getOrganizationId(),
                                        year,
                                        month))

                .build();
    }
}
