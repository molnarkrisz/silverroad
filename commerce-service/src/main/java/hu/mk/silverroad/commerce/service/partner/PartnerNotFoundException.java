package hu.mk.silverroad.commerce.service.partner;

public class PartnerNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PartnerNotFoundException(Long id) {
		super("Id = " + id);
	}
}
