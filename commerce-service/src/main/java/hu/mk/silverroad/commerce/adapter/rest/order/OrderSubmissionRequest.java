package hu.mk.silverroad.commerce.adapter.rest.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderSubmissionRequest(String clientOrderId, Long partnerId, BigDecimal price,
		List<OrderSubmissionItem> items) {
}
