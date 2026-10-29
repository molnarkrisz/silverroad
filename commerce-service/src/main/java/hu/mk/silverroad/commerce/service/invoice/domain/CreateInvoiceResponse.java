package hu.mk.silverroad.commerce.service.invoice.domain;

import java.time.LocalDateTime;

public record CreateInvoiceResponse(Long id, String invoiceNumber, String clientOrderId, InvoiceStatus status,
		LocalDateTime orderDate) {

}
