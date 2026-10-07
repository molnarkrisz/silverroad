package hu.mk.silverroad.commerce.service.category;

public class CategoryNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(Long id) {
		super("Id = " + id);
	}
}
