package hu.mk.silverroad.commerce.service.invoice.domain;

import java.math.BigDecimal;

public record CreateInvoiceItem(Long productId, BigDecimal amount, BigDecimal price, BigDecimal tax) {
}
