package hu.blzsaa.simplewebshop.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simplewebshop.service.OrderService;
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
  void shouldReturnWhatServiceReturns() {
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
}
