package hu.mk.silverroad.commerce.adapter.persistence.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import hu.mk.silverroad.commerce.service.category.domain.Category;
import hu.mk.silverroad.commerce.service.category.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {

	private final SpringDataCategoryRepository repository;

	@Override
	public Optional<Category> findById(long id) {
		return repository.findById(id).map(this::toDomain);
	}

	@Override
	public List<Category> findAll() {
		return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
	}

	@Override
	public void deleteById(long id) {
		repository.deleteById(id);
	}

	@Override
	public void insert(Category category) {
		repository.save(toEntity(category));
	}

	@Override
	public void update(Category category) {
		repository.save(toEntity(category));
	}

	private Category toDomain(CategoryEntity entity) {
		Optional<CategoryEntity> parent = Optional.ofNullable(entity.getParentCategory());
		return new Category(entity.getId(), entity.getName(), parent.isPresent() ? parent.get().getId() : null);
	}

	private CategoryEntity toEntity(Category domain) {
		CategoryEntity entity = new CategoryEntity();
		entity.setId(domain.id());
		entity.setName(domain.name());

		CategoryEntity parent = domain.id() == null ? null : repository.getReferenceById(domain.id());
		entity.setParentCategory(parent);
		return entity;
	}
}
