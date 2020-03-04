package hu.blzsaa.simplewebshop.dbo

import groovy.transform.Canonical
import groovy.transform.builder.Builder

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.validation.constraints.Email
import java.time.OffsetDateTime

@Canonical
@Entity
@Builder
class OrderDbo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id
	@Email
	String emailAddress
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn
	List<ProductDbo> productDbos
	long price
	OffsetDateTime timestamp
}
