package hu.mk.silverroad.commerce.service.product.domain;

import java.math.BigDecimal;

public record Product(Long id, Long categoryId, String name, String description, BigDecimal price) {

}
