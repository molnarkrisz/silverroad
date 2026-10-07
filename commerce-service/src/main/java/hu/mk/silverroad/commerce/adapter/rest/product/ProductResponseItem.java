package hu.mk.silverroad.commerce.adapter.rest.product;

import java.math.BigDecimal;

import hu.mk.silverroad.commerce.service.product.domain.Product;

public record ProductResponseItem(Long id, Long categoryId, String name, String description, BigDecimal price) {

	public ProductResponseItem(Product domain) {
		this(domain.id(), domain.categoryId(), domain.name(), domain.description(), domain.price());
	}

	public Product toDomain() {
		return new Product(id, categoryId, name, description, price);
	}
}
