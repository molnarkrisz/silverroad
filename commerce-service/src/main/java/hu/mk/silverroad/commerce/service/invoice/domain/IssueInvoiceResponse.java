package hu.mk.silverroad.commerce.service.invoice.domain;

import java.time.LocalDateTime;

public record IssueInvoiceResponse(Long id, LocalDateTime issueDate, String invoiceNumber) {

}
