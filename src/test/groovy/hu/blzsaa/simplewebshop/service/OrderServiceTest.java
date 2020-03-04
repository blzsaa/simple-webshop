package hu.blzsaa.simplewebshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simplewebshop.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class OrderServiceTest {
  @Mock private OrderMapper mapper;
  @Mock private ProductService productService;
  private OrderService underTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
    underTest = new OrderService(productService, mapper);
  }

  @Test
  void shouldCheckOrdersAndSummarizeTheirPrices() {
    // given
    Order order = new Order().emailAddress("a").addProductIdItem(1L).addProductIdItem(2L);
    doReturn(new Product().id(1L).price(1L)).when(productService).getProduct(1L);
    doReturn(new Product().id(2L).price(2L)).when(productService).getProduct(2L);

    CreatedOrder createdOrder = new CreatedOrder().price(3L);
    doReturn(createdOrder).when(mapper).transform(order, 3L);

    // when
    var actual = underTest.takeOrder(order);

    // then
    assertThat(actual).isEqualTo(createdOrder);
  }
}
