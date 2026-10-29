package hu.mk.silverroad.commerce.service.invoice.domain;

public interface InvoiceRepository {
	CreateInvoiceResponse insertInvoice(CreateInvoiceRequest order);

	IssueInvoiceResponse issue(IssueInvoiceRequest request);

	void putOnError(Long id, String errorMessage);
}
