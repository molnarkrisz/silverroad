package hu.mk.silverroad.commerce.service.category.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

	Optional<Category> findById(long id);

	List<Category> findAll();

	void deleteById(long id);

	void insert(Category category);

	void update(Category category);
}
