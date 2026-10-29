package hu.mk.silverroad.commerce.adapter.rest.order;

import java.math.BigDecimal;

public record OrderSubmissionItem(Long productId, BigDecimal amount, BigDecimal price) {

}
