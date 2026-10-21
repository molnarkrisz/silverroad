package hu.mk.silverroad.commerce.adapter.rest.catalog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.mk.silverroad.commerce.service.catalog.CatalogService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

	private final CatalogService catalogService;

	@GetMapping("/result")
	public CatalogTreeResponse getcatalog() {
		return new CatalogTreeResponse(catalogService.getCatalog());
	}
}
