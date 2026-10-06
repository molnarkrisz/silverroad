package hu.mk.silverroad.commerce.adapter.persistence.partner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "partner")
@Getter
@Setter
public class PartnerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String name;

	@Column(nullable = false, length = 20)
	private String zip;

	@Column(nullable = false, length = 100)
	private String city;

	@Column(nullable = false, length = 300)
	private String address;

	@Column(length = 50)
	private String phone;

	protected PartnerEntity() {
	}

}