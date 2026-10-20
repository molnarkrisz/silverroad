package hu.mk.silverroad.commerce.service.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public record CatalogProduct(Long id, Long categoryId, String name, String description, BigDecimal price)
		implements Serializable {

}
