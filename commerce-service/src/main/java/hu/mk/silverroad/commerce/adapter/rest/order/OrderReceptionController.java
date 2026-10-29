package hu.mk.silverroad.commerce.adapter.rest.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.mk.silverroad.commerce.service.invoice.OrderReceptionService;
import hu.mk.silverroad.commerce.service.invoice.domain.NewOrderRequest;
import hu.mk.silverroad.commerce.service.invoice.domain.NewOrderResponse;
import hu.mk.silverroad.commerce.service.invoice.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderReceptionController {

	private final OrderReceptionService orderReceptionService;

	@PostMapping(value = "/receive", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<OrderReceptionResponse> receive(@RequestBody Flux<OrderSubmissionRequest> orders) {
		Flux<NewOrderResponse> orederResponses = orderReceptionService.receive(orders.map(this::toService));
		return orederResponses.map(this::toRestResponse);
	}

	private NewOrderRequest toService(OrderSubmissionRequest request) {
		List<OrderItem> items = request.items().stream().map(this::mapItem).collect(Collectors.toList());
		return new NewOrderRequest(request.clientOrderId(), request.partnerId(), request.price(), items);
	}

	private OrderItem mapItem(OrderSubmissionItem restItem) {
		return new OrderItem(restItem.productId(), restItem.amount(), restItem.price());
	}

	private OrderReceptionResponse toRestResponse(NewOrderResponse dto) {
		return new OrderReceptionResponse(dto.clientOrderId(), dto.invoiceNumber(), dto.issueDate(),
				dto.success() ? OrderStatus.ISSUED : OrderStatus.ERROR);
	}
}
