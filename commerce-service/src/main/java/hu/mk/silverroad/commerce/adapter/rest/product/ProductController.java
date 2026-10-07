package hu.mk.silverroad.commerce.adapter.rest.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.mk.silverroad.commerce.service.product.ProductNotFoundException;
import hu.mk.silverroad.commerce.service.product.ProductService;
import hu.mk.silverroad.commerce.service.product.domain.Product;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/result/{id}")
	public ProductResponseItem findById(Long id) {
		Product product = productService.findOne(id);
		if (product == null) {
			throw new ProductNotFoundException(id);
		}
		return new ProductResponseItem(product);
	}

	@GetMapping("/result/all")
	public List<ProductResponseItem> findAll() {
		return productService.findAll().stream().map(ProductResponseItem::new).collect(Collectors.toList());

	}

	@PutMapping("/create")
	public void create(@RequestBody @NotNull ProductResponseItem partner) {
		productService.create(partner.toDomain());
	}

	@PostMapping("/update")
	public void update(@RequestBody @NotNull ProductResponseItem partner) {
		productService.update(partner.toDomain());
	}

	@DeleteMapping("/delete/{id}")
	public void delete(Long id) {
		productService.delete(id);
	}
}
