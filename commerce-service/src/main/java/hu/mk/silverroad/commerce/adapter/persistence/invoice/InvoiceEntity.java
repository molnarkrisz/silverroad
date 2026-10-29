package hu.mk.silverroad.commerce.adapter.persistence.invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(name = "invoice_number", nullable = true, length = 100, unique = true)
	private String invoiceNumber;

	@Setter
	@Column(name = "client_order_id", nullable = false, length = 200, unique = true)
	private String clientOrderId;

	@Setter
	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@Setter
	@Column(name = "error", length = 200)
	private String error;

	@Setter
	@Column(name = "partner_id", nullable = false)
	private Long partnerId;

	@Setter
	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;

	@Setter
	@Column(name = "issue_date", nullable = false)
	private LocalDateTime issueDate;

	@Setter
	@Column(name = "price", nullable = false, precision = 19, scale = 2)
	private BigDecimal price;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<InvoiceItemEntity> items = new ArrayList<>();

	public InvoiceEntity(String invoiceNumber, String clientOrderId, String status, String error, Long partnerId,
			LocalDateTime orderDate, LocalDateTime issueDate, BigDecimal price) {
		this.invoiceNumber = invoiceNumber;
		this.clientOrderId = clientOrderId;
		this.status = status;
		this.error = error;
		this.partnerId = partnerId;
		this.orderDate = orderDate;
		this.issueDate = issueDate;
		this.price = price;
	}

	public void addItem(InvoiceItemEntity item) {
		items.add(item);
		item.setInvoice(this);
	}

	public void removeItem(InvoiceItemEntity item) {
		items.remove(item);
		item.setInvoice(null);
	}
}