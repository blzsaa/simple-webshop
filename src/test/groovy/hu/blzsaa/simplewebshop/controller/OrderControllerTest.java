package hu.blzsaa.simplewebshop.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simplewebshop.service.OrderService;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

class OrderControllerTest {
  private OrderController underTest;
  @Mock private OrderService service;

  @BeforeEach
  void setUp() {
    initMocks(this);
    underTest = new OrderController(service);
  }

  @Test
  void createNewOrderShouldReturnWhatServiceReturns() {
    // given
    var order = new Order().emailAddress("a");
    var createdOrder = new CreatedOrder().price(12L);
    doReturn(createdOrder).when(service).takeOrder(order);

    // when
    var actual = underTest.createNewOrder(order);

    // then
    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(createdOrder);
  }

  @Test
  void getAllOrdersShouldReturnWhatServiceReturns() {
    // given
    var createdOrder = new CreatedOrder().price(12L);
    OffsetDateTime from = OffsetDateTime.MIN;
    OffsetDateTime to = OffsetDateTime.MAX;
    doReturn(List.of(createdOrder)).when(service).getAllOrdersBetween(from, to);

    // when
    var actual = underTest.getAllOrderBetweenTimeFrames(from, to);

    // then
    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(List.of(createdOrder));
  }
}
