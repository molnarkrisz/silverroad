package hu.mk.silverroad.commerce.service.catalog.domain;

import java.util.ArrayList;
import java.util.List;

public record CatalogCategory(Long id, String name, Long parentId, List<CatalogCategory> subCategories,
		List<CatalogProduct> products) {

	public CatalogCategory(Long id, String name, Long parentId) {
		this(id, name, parentId, new ArrayList<>(), new ArrayList<>());
	}
}
