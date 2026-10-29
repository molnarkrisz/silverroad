package hu.mk.silverroad.commerce.service.invoice.domain;

import java.math.BigDecimal;

public record OrderItem(Long productId, BigDecimal amount, BigDecimal price) {

}
