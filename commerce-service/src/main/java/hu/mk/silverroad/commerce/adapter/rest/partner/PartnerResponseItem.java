package hu.mk.silverroad.commerce.adapter.rest.partner;

import hu.mk.silverroad.commerce.service.partner.domain.Partner;

public record PartnerResponseItem(Long id, String name, String zip, String city, String address, String phone) {

	public PartnerResponseItem(Partner item) {
		this(item.id(), item.name(), item.zip(), item.city(), item.address(), item.phone());
	}

	public Partner toDomain() {
		return new Partner(id, name, zip, city, address, phone);
	}
}
