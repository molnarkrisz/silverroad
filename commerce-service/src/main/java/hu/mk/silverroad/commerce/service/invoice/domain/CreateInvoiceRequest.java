package hu.mk.silverroad.commerce.service.invoice.domain;

import java.math.BigDecimal;
import java.util.List;

public record CreateInvoiceRequest(String clientOrderId, Long partnerId, BigDecimal price, List<CreateInvoiceItem> items) {

}
