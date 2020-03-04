package hu.blzsaa.simplewebshop.service;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simplewebshop.dbo.OrderDbo;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.mapper.OrderMapper;
import hu.blzsaa.simplewebshop.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  private final ProductService productService;
  private final OrderMapper orderMapper;
  private final OrderRepository orderRepository;

  public OrderService(
      ProductService productService, OrderMapper orderMapper, OrderRepository orderRepository) {
    this.productService = productService;
    this.orderMapper = orderMapper;
    this.orderRepository = orderRepository;
  }

  public CreatedOrder takeOrder(Order order) {
    List<ProductDbo> productDboList =
        order.getProductId().stream()
            .map(productService::getProductDbo)
            .collect(Collectors.toList());
    long price = productDboList.stream().mapToLong(ProductDbo::getPrice).sum();
    OrderDbo orderDbo = orderMapper.transform(order, productDboList, price);
    var saved = orderRepository.save(orderDbo);
    return orderMapper.transform(saved);
  }
}
