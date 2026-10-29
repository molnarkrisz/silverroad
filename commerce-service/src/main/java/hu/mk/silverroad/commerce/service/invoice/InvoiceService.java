package hu.mk.silverroad.commerce.service.invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import hu.mk.silverroad.commerce.service.invoice.domain.CreateInvoiceItem;
import hu.mk.silverroad.commerce.service.invoice.domain.CreateInvoiceRequest;
import hu.mk.silverroad.commerce.service.invoice.domain.CreateInvoiceResponse;
import hu.mk.silverroad.commerce.service.invoice.domain.InvoiceRepository;
import hu.mk.silverroad.commerce.service.invoice.domain.IssueInvoiceRequest;
import hu.mk.silverroad.commerce.service.invoice.domain.IssueInvoiceResponse;
import hu.mk.silverroad.commerce.service.invoice.domain.NewOrderRequest;
import hu.mk.silverroad.commerce.service.invoice.domain.NewOrderResponse;
import hu.mk.silverroad.commerce.service.invoice.domain.OrderItem;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

	private static final BigDecimal TAX = BigDecimal.valueOf(27.0);
	private static final String PREFIX = "INV";
	private static final DateTimeFormatter INVOICE_NUMBER_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM");

	private final InvoiceRepository invoiceRepository;

	public NewOrderResponse processNewOrder(NewOrderRequest order) {
		CreateInvoiceResponse insertResponse = invoiceRepository.insertInvoice(createInvoiceRequest(order));
		IssueInvoiceResponse issueResponse = invoiceRepository.issue(createIssueRequest(insertResponse.id()));
		return new NewOrderResponse(issueResponse.invoiceNumber(), insertResponse.clientOrderId(),
				issueResponse.issueDate(), true);
	}

	private CreateInvoiceRequest createInvoiceRequest(NewOrderRequest order) {
		List<CreateInvoiceItem> items = order.items().stream().map(this::mapItem).collect(Collectors.toList());
		return new CreateInvoiceRequest(order.clientOrderId(), order.partnerId(), order.price(), items);
	}

	private CreateInvoiceItem mapItem(OrderItem item) {
		return new CreateInvoiceItem(item.productId(), item.amount(), item.price(), TAX);
	}

	private IssueInvoiceRequest createIssueRequest(Long id) {
		LocalDateTime now = LocalDateTime.now();
		String formatted = String.format("%010d", id);
		if (formatted.length() > 10) {
			formatted = formatted.substring(formatted.length() - 10);
		}
		String invoiceNumber = PREFIX + "-" + INVOICE_NUMBER_FORMATTER.format(now) + "-" + formatted;
		return new IssueInvoiceRequest(id, invoiceNumber, now);
	}
}
