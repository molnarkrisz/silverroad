package hu.mk.silverroad.commerce.service.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

	Optional<Product> findById(long id);

	List<Product> findAll();

	void deleteById(long id);

	void insert(Product Product);

	void update(Product Product);
}
