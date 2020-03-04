package hu.blzsaa.simplewebshop.service;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simplewebshop.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  private final ProductService productService;
  private final OrderMapper orderMapper;

  public OrderService(ProductService productService, OrderMapper orderMapper) {
    this.productService = productService;
    this.orderMapper = orderMapper;
  }

  public CreatedOrder takeOrder(Order order) {
    var price =
        order.getProductId().stream()
            .map(productService::getProduct)
            .mapToLong(Product::getPrice)
            .sum();
    return orderMapper.transform(order, price);
  }
}
