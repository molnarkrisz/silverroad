package hu.mk.silverroad.commerce.adapter.rest.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderReceptionEndToEndTest {

	private static final String REQUEST_BODY_TEMPLATE = """
			  {
			    "clientOrderId": "order-%s",
			    "partnerId": "%s",
			    "price": 100,
			    "items": [
			      {
			        "productId": "%s",
			        "amount": 1,
			        "price": 100
			      }
			    ]
			  }
			""";

	@Container
	@ServiceConnection
	static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private WebTestClient webTestClient;

	private Long partnerId;
	private Long productId;

	@BeforeEach
	void setUp() {
		jdbcTemplate.execute("""
				TRUNCATE TABLE invoice_item, invoice, product, category, partner
				RESTART IDENTITY CASCADE
				""");

		partnerId = jdbcTemplate.queryForObject("""
				INSERT INTO partner (name, zip, city, address, phone)
				VALUES (?, ?, ?, ?, ?)
				RETURNING id
				""", Long.class, "Kovács János", "1111", "Budapest", "Teszt utca 1.", null);

		Long categoryId = jdbcTemplate.queryForObject("""
				INSERT INTO category (name)
				VALUES (?)
				RETURNING id
				""", Long.class, "E2E category");

		productId = jdbcTemplate.queryForObject("""
				INSERT INTO product (name, category_id, description, price)
				VALUES (?, ?, ?, ?)
				RETURNING id
				""", Long.class, "E2E product", categoryId, "E2E test product", 100.0);
	}

	@Test
	void receivesNdjsonOrdersAndReturnsAResultForEach() {
		final int requestCount = 10;
		List<OrderReceptionResponse> responses = webTestClient.post()
				.uri("/api/order/receive")
				.contentType(MediaType.APPLICATION_NDJSON)
				.accept(MediaType.APPLICATION_NDJSON)
				.bodyValue(generateRequestBody(requestCount))
				.exchange()
				.expectStatus()
				.isOk()
				.expectHeader()
				.contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
				.returnResult(OrderReceptionResponse.class)
				.getResponseBody()
				.collectList()
				.block(Duration.ofSeconds(10));

		assertThat(responses).hasSize(requestCount);
		assertThat(responses).extracting(OrderReceptionResponse::status).containsOnly(OrderStatus.ISSUED);
		
		Integer savedInvoices = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM invoice", Integer.class);
        assertThat(savedInvoices).isEqualTo(requestCount);
	}

	private String generateRequestBody(int requestCount) {
		return IntStream.range(0, requestCount)
				.mapToObj(i -> String.format(REQUEST_BODY_TEMPLATE, i, partnerId, productId))
				.collect(Collectors.joining(" "));
	}
}
