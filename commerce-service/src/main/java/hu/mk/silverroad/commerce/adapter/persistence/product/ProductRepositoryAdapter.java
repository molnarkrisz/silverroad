package hu.mk.silverroad.commerce.adapter.persistence.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import hu.mk.silverroad.commerce.service.product.domain.Product;
import hu.mk.silverroad.commerce.service.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

	private final SpringDataProductRepository repository;

	@Override
	public Optional<Product> findById(long id) {
		return repository.findById(id).map(this::toDomain);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
	}

	@Override
	public void deleteById(long id) {
		repository.deleteById(id);
	}

	@Override
	public void insert(Product product) {
		repository.save(toEntity(product));
	}

	@Override
	public void update(Product product) {
		repository.save(toEntity(product));
	}

	private Product toDomain(ProductEntity entity) {
		return new Product(entity.getId(), entity.getCategory() == null ? null : entity.getCategory().getId(),
				entity.getName(), entity.getDescription(), entity.getPrice());
	}

	private ProductEntity toEntity(Product domain) {
		ProductEntity entity = new ProductEntity();
		entity.setId(domain.id());
		entity.setCategoryId(domain.categoryId());
		entity.setName(domain.name());
		entity.setDescription(domain.description());
		entity.setPrice(domain.price());
		return entity;
	}
}
