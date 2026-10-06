package hu.mk.silverroad.commerce.adapter.persistence.partner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPartnerRepository extends JpaRepository<PartnerEntity, Long> {
}