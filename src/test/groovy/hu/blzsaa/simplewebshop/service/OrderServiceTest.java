package hu.blzsaa.simplewebshop.service;

import static hu.blzsaa.simplewebshop.TestConstants.*;
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
import org.junit.jupiter.api.Nested;
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

  @Nested
  class getAllOrdersTest {
    private final CreatedOrder createdOrder1 =
        new CreatedOrder().timestamp(OFFSET_DATE_TIME.minusSeconds(2));
    private final CreatedOrder createdOrder2 =
        new CreatedOrder().timestamp(OFFSET_DATE_TIME.minusSeconds(1));
    private final CreatedOrder createdOrder3 = new CreatedOrder().timestamp(OFFSET_DATE_TIME);
    private final CreatedOrder createdOrder4 =
        new CreatedOrder().timestamp(OFFSET_DATE_TIME.plusSeconds(1));
    private final CreatedOrder createdOrder5 =
        new CreatedOrder().timestamp(OFFSET_DATE_TIME.plusSeconds(2));

    @BeforeEach
    void setUp() {
      var orderDbo1 = OrderDbo.builder().timestamp(OFFSET_DATE_TIME.minusSeconds(2)).build();
      var orderDbo2 = OrderDbo.builder().timestamp(OFFSET_DATE_TIME.minusSeconds(1)).build();
      var orderDbo3 = OrderDbo.builder().timestamp(OFFSET_DATE_TIME).build();
      var orderDbo4 = OrderDbo.builder().timestamp(OFFSET_DATE_TIME.plusSeconds(1)).build();
      var orderDbo5 = OrderDbo.builder().timestamp(OFFSET_DATE_TIME.plusSeconds(2)).build();

      doReturn(createdOrder1).when(mapper).transform(orderDbo1);
      doReturn(createdOrder2).when(mapper).transform(orderDbo2);
      doReturn(createdOrder3).when(mapper).transform(orderDbo3);
      doReturn(createdOrder4).when(mapper).transform(orderDbo4);
      doReturn(createdOrder5).when(mapper).transform(orderDbo5);
      doReturn(List.of(orderDbo1, orderDbo2, orderDbo3, orderDbo4, orderDbo5))
          .when(orderRepository)
          .findAll();
    }

    @Test
    void getAllOrdersBetweenShouldReturnOnlyDatesBetweenFromAndTo() {
      // when
      var actual = underTest.getAllOrdersBetween(FROM, TO);

      // then
      assertThat(actual).containsOnly(createdOrder2, createdOrder3, createdOrder4);
    }

    @Test
    void getAllOrdersBetweenShouldReturnOnlyDatesBeforeToWhenFromIsNull() {
      // when
      var actual = underTest.getAllOrdersBetween(null, TO);

      // then
      assertThat(actual).containsOnly(createdOrder1, createdOrder2, createdOrder3, createdOrder4);
    }

    @Test
    void getAllOrdersBetweenShouldReturnOnlyDatesAfterFromWhenToIsNull() {
      // when
      var actual = underTest.getAllOrdersBetween(FROM, null);

      // then
      assertThat(actual).containsOnly(createdOrder2, createdOrder3, createdOrder4, createdOrder5);
    }
  }
}
