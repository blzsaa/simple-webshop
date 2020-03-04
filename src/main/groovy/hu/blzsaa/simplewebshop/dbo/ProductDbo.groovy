package hu.blzsaa.simplewebshop.dbo

import groovy.transform.Canonical

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Canonical
@Entity
class ProductDbo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id
	String name
	long price
}
