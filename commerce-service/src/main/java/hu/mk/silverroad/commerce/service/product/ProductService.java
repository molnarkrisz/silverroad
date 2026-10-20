package hu.mk.silverroad.commerce.service.product;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import hu.mk.silverroad.commerce.service.product.domain.Product;
import hu.mk.silverroad.commerce.service.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	@CacheEvict(cacheNames = "catalog-tree", allEntries = true)
	public void create(Product product) {
		productRepository.insert(product);
	}

	@CacheEvict(cacheNames = "catalog-tree", allEntries = true)
	public void update(Product product) {
		productRepository.update(product);
	}

	@CacheEvict(cacheNames = "catalog-tree", allEntries = true)
	public void delete(long id) {
		productRepository.deleteById(id);
	}

	public Product findOne(long id) {
		return productRepository.findById(id).orElse(null);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

}
