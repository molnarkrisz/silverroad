package hu.mk.silverroad.commerce.adapter.persistence.invoice;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvoiceItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceEntity invoice;

	@Setter
	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Setter
	@Column(name = "amount", nullable = false, precision = 12, scale = 3)
	private BigDecimal amount;

	@Setter
	@Column(name = "tax", nullable = false, precision = 5, scale = 2)
	private BigDecimal tax;

	@Setter
	@Column(name = "price", nullable = false, precision = 19, scale = 2)
	private BigDecimal price;

	public InvoiceItemEntity(Long productId, BigDecimal amount, BigDecimal tax, BigDecimal price) {
		this.productId = productId;
		this.amount = amount;
		this.tax = tax;
		this.price = price;
	}

	void setInvoice(InvoiceEntity invoice) {
		this.invoice = invoice;
	}
}