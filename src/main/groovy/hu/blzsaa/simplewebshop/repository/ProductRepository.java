package hu.blzsaa.simplewebshop.repository;

import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDbo, Long> {}
