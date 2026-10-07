package hu.mk.silverroad.commerce.service.partner.domain;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository {

	Optional<Partner> findById(long id);

	List<Partner> findAll();

	void deleteById(long id);

	void insert(Partner partner);

	void update(Partner partner);
}
