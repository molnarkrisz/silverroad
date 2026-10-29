package hu.mk.silverroad.commerce.adapter.persistence.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

}
