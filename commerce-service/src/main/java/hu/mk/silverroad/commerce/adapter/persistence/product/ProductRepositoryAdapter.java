package hu.mk.silverroad.commerce.adapter.persistence.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import hu.mk.silverroad.commerce.adapter.persistence.category.SpringDataCategoryRepository;
import hu.mk.silverroad.commerce.service.product.domain.Product;
import hu.mk.silverroad.commerce.service.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

	private final SpringDataProductRepository productRepository;

	private final SpringDataCategoryRepository categoryRepository;

	@Override
	public Optional<Product> findById(long id) {
		return productRepository.findById(id).map(this::toDomain);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
	}

	@Override
	public void deleteById(long id) {
		productRepository.deleteById(id);
	}

	@Override
	public void insert(Product product) {
		productRepository.save(toEntity(product));
	}

	@Override
	public void update(Product product) {
		productRepository.save(toEntity(product));
	}

	private Product toDomain(ProductEntity entity) {
		return new Product(entity.getId(), entity.getCategory() == null ? null : entity.getCategory().getId(),
				entity.getName(), entity.getDescription(), entity.getPrice());
	}

	private ProductEntity toEntity(Product domain) {
		ProductEntity entity = domain.id() == null ? new ProductEntity()
				: productRepository.getReferenceById(domain.id());
		entity.setId(domain.id());
		entity.setCategory(categoryRepository.getReferenceById(domain.categoryId()));
		entity.setName(domain.name());
		entity.setDescription(domain.description());
		entity.setPrice(domain.price());
		return entity;
	}
}
