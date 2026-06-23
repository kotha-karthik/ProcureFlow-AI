package com.procureflow.procureflowbackend.finance.service;

import com.procureflow.procureflowbackend.finance.dto.CreatePaymentRequest;
import com.procureflow.procureflowbackend.finance.dto.PaymentResponse;
import com.procureflow.procureflowbackend.finance.entity.Invoice;
import com.procureflow.procureflowbackend.finance.entity.Payment;
import com.procureflow.procureflowbackend.finance.repository.InvoiceRepository;
import com.procureflow.procureflowbackend.finance.repository.PaymentRepository;
import com.procureflow.procureflowbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public PaymentResponse createPayment(CreatePaymentRequest request, CustomUserDetails user)
    {

        Invoice invoice = invoiceRepository.findById(
                        request.getInvoiceId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invoice not found"));

        if (!"APPROVED".equals(invoice.getInvoiceStatus())) {

            throw new RuntimeException(
                    "Only APPROVED invoices can be paid");
        }

        UUID paymentId = UUID.randomUUID();

        String paymentNumber = "PAY-" + System.currentTimeMillis();

        Payment payment =
                Payment.builder()
                        .paymentId(paymentId)
                        .organizationId(
                                user.getOrganizationId())
                        .paymentNumber(paymentNumber)
                        .invoiceId(
                                request.getInvoiceId())
                        .vendorId(
                                request.getVendorId())
                        .paymentDate(LocalDate.now())
                        .paymentAmount(
                                request.getPaymentAmount())
                        .paymentMethod(
                                request.getPaymentMethod())
                        .transactionReference(
                                request.getTransactionReference())
                        .paymentStatus("PAID")
                        .remarks(request.getRemarks())
                        .createdAt(LocalDateTime.now())
                        .createdBy(user.getUserId())
                        .build();

        paymentRepository.save(payment);

        // Update Invoice Status
        invoice.setInvoiceStatus("PAID");
        invoice.setUpdatedAt(LocalDateTime.now());
        invoice.setUpdatedBy(user.getUserId());

        invoiceRepository.save(invoice);

        return PaymentResponse.builder()
                .paymentId(paymentId)
                .paymentNumber(paymentNumber)
                .paymentAmount(
                        payment.getPaymentAmount())
                .paymentStatus(
                        payment.getPaymentStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments(
            CustomUserDetails user) {

        return paymentRepository
                .findByOrganizationId(
                        user.getOrganizationId())
                .stream()
                .map(payment ->
                        PaymentResponse.builder()
                                .paymentId(
                                        payment.getPaymentId())
                                .paymentNumber(
                                        payment.getPaymentNumber())
                                .paymentAmount(
                                        payment.getPaymentAmount())
                                .paymentStatus(
                                        payment.getPaymentStatus())
                                .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(
            UUID paymentId) {

        Payment payment =
                paymentRepository.findById(paymentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"));

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .paymentNumber(payment.getPaymentNumber())
                .paymentAmount(payment.getPaymentAmount())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }
}
