package hu.blzsaa.simplewebshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simplewebshop.dbo.OrderDbo;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.mapper.OrderMapper;
import hu.blzsaa.simplewebshop.repository.OrderRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class OrderServiceTest {
  private OrderService underTest;
  @Mock private OrderMapper mapper;
  @Mock private ProductService productService;
  @Mock private OrderRepository orderRepository;

  @BeforeEach
  void setUp() {
    initMocks(this);
    underTest = new OrderService(productService, mapper, orderRepository);
  }

  @Test
  void shouldCheckOrdersAndSummarizeTheirPrices() {
    // given
    Order order = new Order().emailAddress("a").addProductIdItem(1L).addProductIdItem(2L);
    var productDbo1 = new ProductDbo(1L, "n1", 1L);
    doReturn(productDbo1).when(productService).getProductDbo(1L);
    var productDbo2 = new ProductDbo(2L, "n2", 2L);
    doReturn(productDbo2).when(productService).getProductDbo(2L);

    var orderDbo1 = OrderDbo.builder().id(1L).price(3L).build();
    doReturn(orderDbo1).when(mapper).transform(order, List.of(productDbo1, productDbo2), 3L);
    var orderDbo2 = OrderDbo.builder().id(2L).price(3L).build();
    doReturn(orderDbo2).when(orderRepository).save(orderDbo1);
    CreatedOrder createdOrder = new CreatedOrder().price(3L);
    doReturn(createdOrder).when(mapper).transform(orderDbo2);

    // when
    var actual = underTest.takeOrder(order);

    // then
    assertThat(actual).isEqualTo(createdOrder);
  }
}
