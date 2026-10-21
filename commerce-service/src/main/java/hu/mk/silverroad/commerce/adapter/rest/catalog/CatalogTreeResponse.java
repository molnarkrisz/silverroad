package hu.mk.silverroad.commerce.adapter.rest.catalog;

import hu.mk.silverroad.commerce.service.catalog.domain.CatalogTree;
import lombok.Data;

@Data
public class CatalogTreeResponse {

	private CatalogTreeCategory main;

	public CatalogTreeResponse(CatalogTree tree) {
		main = new CatalogTreeCategory(tree.main());
	}
}
