package hu.mk.silverroad.commerce.service.product;

public class ProductNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(Long id) {
		super("Id = " + id);
	}
}
