package hu.mk.silverroad.commerce.adapter.rest.order;

import java.time.LocalDateTime;

public record OrderReceptionResponse(String clientOrderId, String invoiceNumber, LocalDateTime issueDate,
		OrderStatus status) {

}
