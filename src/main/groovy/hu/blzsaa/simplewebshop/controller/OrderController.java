package hu.blzsaa.simplewebshop.controller;

import hu.blzsaa.simple_webshop.api.OrdersApi;
import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simplewebshop.service.OrderService;
import java.time.OffsetDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController implements OrdersApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<CreatedOrder> createNewOrder(Order order) {
    LOGGER.info("incoming request order: {}", order);
    CreatedOrder createdOrder = service.takeOrder(order);
    LOGGER.info("Order was successful: {}", createdOrder);
    return ResponseEntity.ok(createdOrder);
  }

  @Override
  public ResponseEntity<List<CreatedOrder>> getAllOrderBetweenTimeFrames(
      OffsetDateTime from, OffsetDateTime to) {
    LOGGER.info("incoming request for orders between: {} and {}", from, to);
    List<CreatedOrder> createdOrders = service.getAllOrdersBetween(from, to);
    LOGGER.info("Returning {} order", createdOrders.size());
    return ResponseEntity.ok(createdOrders);
  }
}
