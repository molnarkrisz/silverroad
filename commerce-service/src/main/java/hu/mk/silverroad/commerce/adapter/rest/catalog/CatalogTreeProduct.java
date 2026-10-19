package hu.mk.silverroad.commerce.adapter.rest.catalog;

import java.math.BigDecimal;

import hu.mk.silverroad.commerce.service.catalog.domain.CatalogProduct;
import lombok.Data;

@Data
public class CatalogTreeProduct {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;

	public CatalogTreeProduct(CatalogProduct product) {
		id = product.id();
		name = product.name();
		description = product.description();
		price = product.price();
	}
}
