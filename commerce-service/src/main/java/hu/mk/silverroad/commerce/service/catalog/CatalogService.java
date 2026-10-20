package hu.mk.silverroad.commerce.service.catalog;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import hu.mk.silverroad.commerce.service.catalog.domain.CatalogCategory;
import hu.mk.silverroad.commerce.service.catalog.domain.CatalogProduct;
import hu.mk.silverroad.commerce.service.catalog.domain.CatalogTree;
import hu.mk.silverroad.commerce.service.category.domain.Category;
import hu.mk.silverroad.commerce.service.category.domain.CategoryRepository;
import hu.mk.silverroad.commerce.service.product.domain.Product;
import hu.mk.silverroad.commerce.service.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class CatalogService {

	private static final Long MAIN = -1l;

	private final CategoryRepository categoryRepository;

	private final ProductRepository productRepository;

	@Cacheable(cacheNames = "catalog-tree", key = "'all'")
	public CatalogTree getCatalog() {
		List<Category> categories = categoryRepository.findAll();
		List<Product> products = productRepository.findAll();

		Map<Long, CatalogCategory> categoriesById = getCategoriesById(categories);
		buildTree(categoriesById);
		buildProductLists(categoriesById, products);

		return new CatalogTree(categoriesById.get(MAIN));
	}

	private Map<Long, CatalogCategory> getCategoriesById(List<Category> categories) {
		Map<Long, CatalogCategory> map = categories.stream().map(this::mapCategory)
				.collect(Collectors.toMap(c -> c.id(), Function.identity()));
		map.put(MAIN, new CatalogCategory(MAIN, null, null));
		return map;
	}

	private CatalogCategory mapCategory(Category category) {
		return new CatalogCategory(category.id(), category.name(), category.parentId());
	}

	private CatalogProduct mapProduct(Product product) {
		return new CatalogProduct(product.id(), product.categoryId(), product.name(), product.description(),
				product.price());
	}

	private void buildTree(Map<Long, CatalogCategory> categoriesById) {
		CatalogCategory main = categoriesById.get(MAIN);
		categoriesById.values().forEach(category -> {
			if (category.id() == MAIN) {
				return;
			}

			if (category.parentId() == null || categoriesById.get(category.parentId()) == null) {
				main.subCategories().add(category);
				return;
			}
			categoriesById.get(category.parentId()).subCategories().add(category);
		});
	}

	private void buildProductLists(Map<Long, CatalogCategory> categoriesById, List<Product> products) {
		CatalogCategory main = categoriesById.get(MAIN);

		products.stream().map(this::mapProduct).forEach(p -> {
			if (p.categoryId() == null || categoriesById.get(p.categoryId()) == null) {
				main.products().add(p);
			}
			categoriesById.get(p.categoryId()).products().add(p);
		});
	}
}
