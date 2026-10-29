package hu.mk.silverroad.commerce.service.invoice.domain;

import java.time.LocalDateTime;

public record IssueInvoiceRequest(Long id, String invoiceNumber, LocalDateTime issueDate) {

}
