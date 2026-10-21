package hu.mk.silverroad.commerce.service.partner;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import hu.mk.silverroad.commerce.service.partner.domain.Partner;
import hu.mk.silverroad.commerce.service.partner.domain.PartnerRepository;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class PartnerService {

	private final PartnerRepository partnerRepository;

	public void create(Partner partner) {
		partnerRepository.insert(partner);
	}

	public void update(Partner partner) {
		partnerRepository.update(partner);
	}

	public void delete(long id) {
		partnerRepository.deleteById(id);
	}

	public Partner findOne(long id) {
		return partnerRepository.findById(id).orElse(null);
	}

	public List<Partner> findAll() {
		return partnerRepository.findAll();
	}

}
