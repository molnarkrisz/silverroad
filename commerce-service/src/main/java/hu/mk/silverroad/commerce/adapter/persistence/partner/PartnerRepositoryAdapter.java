package hu.mk.silverroad.commerce.adapter.persistence.partner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import hu.mk.silverroad.commerce.service.partner.domain.Partner;
import hu.mk.silverroad.commerce.service.partner.domain.PartnerRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PartnerRepositoryAdapter implements PartnerRepository {

	private final SpringDataPartnerRepository repository;

	@Override
	public Optional<Partner> findById(long id) {
		return repository.findById(id).map(this::toDomain);
	}

	@Override
	public List<Partner> findAll() {
		return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
	}

	@Override
	public void deleteById(long id) {
		repository.deleteById(id);
	}

	@Override
	public void insert(Partner partner) {
		repository.save(toEntity(partner));
	}

	@Override
	public void update(Partner partner) {
		repository.save(toEntity(partner));
	}

	private Partner toDomain(PartnerEntity entity) {
		return new Partner(entity.getId(), entity.getName(), entity.getZip(), entity.getCity(), entity.getAddress(),
				entity.getPhone());
	}

	private PartnerEntity toEntity(Partner domain) {
		PartnerEntity entity = new PartnerEntity();
		entity.setAddress(domain.address());
		entity.setCity(domain.city());
		entity.setId(domain.id());
		entity.setName(domain.name());
		entity.setPhone(domain.phone());
		entity.setZip(domain.zip());
		return entity;
	}
}
