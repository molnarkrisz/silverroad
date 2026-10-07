package hu.mk.silverroad.commerce.adapter.rest.category;

import hu.mk.silverroad.commerce.service.category.domain.Category;

public record CategoryResponseItem(Long id, String name, Long parentId) {

	public CategoryResponseItem(Category domain) {
		this(domain.id(), domain.name(), domain.parentId());
	}

	public Category toDomain() {
		return new Category(id, name, parentId);
	}
}
