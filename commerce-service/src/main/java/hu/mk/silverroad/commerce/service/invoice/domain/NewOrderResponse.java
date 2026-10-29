package hu.mk.silverroad.commerce.service.invoice.domain;

import java.time.LocalDateTime;

public record NewOrderResponse(String invoiceNumber, String clientOrderId, LocalDateTime issueDate, boolean success) {

}
