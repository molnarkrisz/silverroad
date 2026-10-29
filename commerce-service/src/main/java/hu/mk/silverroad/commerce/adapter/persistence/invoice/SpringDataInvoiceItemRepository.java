package hu.mk.silverroad.commerce.adapter.persistence.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInvoiceItemRepository extends JpaRepository<InvoiceItemEntity, Long> {

}
