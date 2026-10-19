package hu.mk.silverroad.commerce.adapter.rest.catalog;

import java.util.List;
import java.util.stream.Collectors;

import hu.mk.silverroad.commerce.service.catalog.domain.CatalogCategory;
import lombok.Data;

@Data
public class CatalogTreeCategory {
	private Long id;
	private String name;
	private List<CatalogTreeCategory> subCategories;
	private List<CatalogTreeProduct> products;

	public CatalogTreeCategory(CatalogCategory category) {
		id = category.id();
		name = category.name();
		subCategories = category.subCategories().stream().map(CatalogTreeCategory::new).collect(Collectors.toList());
		products = category.products().stream().map(CatalogTreeProduct::new).collect(Collectors.toList());
	}
}
