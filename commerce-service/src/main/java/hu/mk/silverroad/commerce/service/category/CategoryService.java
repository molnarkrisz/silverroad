package hu.mk.silverroad.commerce.service.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import hu.mk.silverroad.commerce.service.category.domain.Category;
import hu.mk.silverroad.commerce.service.category.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public void create(Category category) {
		categoryRepository.insert(category);
	}

	public void update(Category category) {
		categoryRepository.update(category);
	}

	public void delete(long id) {
		categoryRepository.deleteById(id);
	}

	public Category findOne(long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

}
