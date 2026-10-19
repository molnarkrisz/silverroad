package hu.mk.silverroad.commerce.service.catalog.domain;

import java.math.BigDecimal;

public record CatalogProduct(Long id, Long categoryId, String name, String description, BigDecimal price) {

}
