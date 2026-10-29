package hu.mk.silverroad.commerce.adapter.persistence.invoice;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import hu.mk.silverroad.commerce.service.invoice.domain.CreateInvoiceItem;
import hu.mk.silverroad.commerce.service.invoice.domain.CreateInvoiceRequest;
import hu.mk.silverroad.commerce.service.invoice.domain.CreateInvoiceResponse;
import hu.mk.silverroad.commerce.service.invoice.domain.InvoiceRepository;
import hu.mk.silverroad.commerce.service.invoice.domain.InvoiceStatus;
import hu.mk.silverroad.commerce.service.invoice.domain.IssueInvoiceRequest;
import hu.mk.silverroad.commerce.service.invoice.domain.IssueInvoiceResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InvoiceRepositoryAdapter implements InvoiceRepository {

	private final SpringDataInvoiceRepository invoiceRepository;

	@Override
	public CreateInvoiceResponse insertInvoice(CreateInvoiceRequest request) {
		InvoiceEntity entity = new InvoiceEntity();
		entity.setClientOrderId(request.clientOrderId());
		entity.setStatus(InvoiceStatus.CREATED.toString());
		entity.setPartnerId(request.partnerId());
		LocalDateTime orderDate = LocalDateTime.now();
		entity.setOrderDate(orderDate);
		entity.setPrice(request.price());
		request.items().stream().map(this::createItemEntity).forEach(entity::addItem);
		entity = invoiceRepository.save(entity);
		return new CreateInvoiceResponse(entity.getId(), entity.getInvoiceNumber(), request.clientOrderId(),
				InvoiceStatus.CREATED, orderDate);
	}

	private InvoiceItemEntity createItemEntity(CreateInvoiceItem item) {
		InvoiceItemEntity entity = new InvoiceItemEntity();
		entity.setAmount(item.amount());
		entity.setProductId(item.productId());
		entity.setTax(item.tax());
		entity.setPrice(item.price());
		return entity;
	}

	@Override
	public IssueInvoiceResponse issue(IssueInvoiceRequest request) {
		InvoiceEntity entity = invoiceRepository.getReferenceById(request.id());
		entity.setIssueDate(request.issueDate());
		entity.setInvoiceNumber(request.invoiceNumber());
		entity.setStatus(InvoiceStatus.ISSUED.toString());
		entity.setError(null);
		entity = invoiceRepository.save(entity);
		return new IssueInvoiceResponse(entity.getId(), entity.getIssueDate(), entity.getInvoiceNumber());
	}

	@Override
	public void putOnError(Long id, String errorMessage) {
		InvoiceEntity entity = invoiceRepository.getReferenceById(id);
		if (entity == null) {
			return;
		}

		entity.setStatus(InvoiceStatus.ERROR.toString());
		entity.setError(errorMessage);
		invoiceRepository.save(entity);
	}
}
