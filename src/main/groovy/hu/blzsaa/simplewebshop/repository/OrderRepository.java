package hu.blzsaa.simplewebshop.repository;

import hu.blzsaa.simplewebshop.dbo.OrderDbo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDbo, Long> {}
