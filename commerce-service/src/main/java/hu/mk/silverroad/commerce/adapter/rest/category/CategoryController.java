package hu.mk.silverroad.commerce.adapter.rest.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.mk.silverroad.commerce.service.category.CategoryNotFoundException;
import hu.mk.silverroad.commerce.service.category.CategoryService;
import hu.mk.silverroad.commerce.service.category.domain.Category;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/result/{id}")
	public CategoryResponseItem findById(Long id) {
		Category category = categoryService.findOne(id);
		if (category == null) {
			throw new CategoryNotFoundException(id);
		}
		return new CategoryResponseItem(category);
	}

	@GetMapping("/result/all")
	public List<CategoryResponseItem> findAll() {
		return categoryService.findAll().stream().map(CategoryResponseItem::new).collect(Collectors.toList());

	}

	@PutMapping("/create")
	public void create(@RequestBody @NotNull CategoryResponseItem partner) {
		categoryService.create(partner.toDomain());
	}

	@PostMapping("/update")
	public void update(@RequestBody @NotNull CategoryResponseItem partner) {
		categoryService.update(partner.toDomain());
	}

	@DeleteMapping("/delete/{id}")
	public void delete(Long id) {
		categoryService.delete(id);
	}
}
