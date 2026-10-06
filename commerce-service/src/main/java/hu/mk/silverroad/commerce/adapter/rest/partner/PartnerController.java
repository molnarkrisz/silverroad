package hu.mk.silverroad.commerce.adapter.rest.partner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.mk.silverroad.commerce.service.partner.PartnerNotFoundException;
import hu.mk.silverroad.commerce.service.partner.PartnerService;
import hu.mk.silverroad.commerce.service.partner.domain.Partner;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/partner")
@RequiredArgsConstructor
public class PartnerController {

	private final PartnerService partnerService;

	@GetMapping("/result/{id}")
	public PartnerResponseItem findById(Long id) {
		Partner partner = partnerService.findOne(id);
		if (partner == null) {
			throw new PartnerNotFoundException(id);
		}
		return new PartnerResponseItem(partner);
	}

	@GetMapping("/result/all")
	public List<PartnerResponseItem> findAll() {
		return partnerService.findAll().stream().map(PartnerResponseItem::new).collect(Collectors.toList());

	}

	@PutMapping("/create")
	public void create(@RequestBody @NotNull PartnerResponseItem partner) {
		partnerService.create(partner.toDomain());
	}

	@DeleteMapping("/delete/{id}")
	public void delete(Long id) {
		partnerService.delete(id);
	}
}
