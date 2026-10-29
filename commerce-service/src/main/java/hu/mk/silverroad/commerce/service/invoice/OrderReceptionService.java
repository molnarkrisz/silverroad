package hu.mk.silverroad.commerce.service.invoice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import hu.mk.silverroad.commerce.service.invoice.domain.NewOrderRequest;
import hu.mk.silverroad.commerce.service.invoice.domain.NewOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderReceptionService {

	private final InvoiceService invoiceService;

	public Flux<NewOrderResponse> receive(Flux<NewOrderRequest> orders) {
		return orders.concatMap(order -> Mono.fromCallable(() -> invoiceService.processNewOrder(order))
				.subscribeOn(Schedulers.boundedElastic())
				.onErrorResume(exception -> {
					log.error("A rendelés feldolgozása nem sikerült: {}", order.clientOrderId(), exception);

					return Mono.just(new NewOrderResponse(null, order.clientOrderId(), null, false));
				}));
	}
}
