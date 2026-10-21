package hu.mk.silverroad.commerce.adapter.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
}
