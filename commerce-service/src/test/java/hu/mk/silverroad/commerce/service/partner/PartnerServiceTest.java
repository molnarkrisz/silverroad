package hu.mk.silverroad.commerce.service.partner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.mk.silverroad.commerce.service.partner.domain.Partner;
import hu.mk.silverroad.commerce.service.partner.domain.PartnerRepository;

@ExtendWith(MockitoExtension.class)
public class PartnerServiceTest {

	@Mock
	private PartnerRepository repository;

	@InjectMocks
	private PartnerService service;
	
	@Test
	void testExisting() {
		Partner partner = new Partner(1l, "Test", "1111", "Budapest", "Fő út 1.", null);
		when(repository.findById(anyLong())).thenReturn(Optional.of(partner));

		Partner result = service.findOne(1l);

		assertNotNull(result);
		assertAll(
				() -> assertEquals(1l, result.id()),
				() -> assertEquals("Test", result.name()),
				() -> assertEquals("1111", result.zip()),
				() -> assertEquals("Budapest", result.city()),
				() -> assertEquals("Fő út 1.", result.address()),
				() -> assertNull(result.phone()));
	}
}
