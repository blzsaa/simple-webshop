package hu.blzsaa.simplewebshop.it.orders;

import static hu.blzsaa.simplewebshop.TestConstants.ACME_PRODUCT2CREATE;
import static hu.blzsaa.simplewebshop.TestConstants.SNAKE_OIL_PRODUCT;
import static hu.blzsaa.simplewebshop.it.ITTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simplewebshop.dbo.OrderDbo;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.repository.OrderRepository;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CreateOrderIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private OrderRepository orderRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
    orderRepository.deleteAll();
  }

  @Test
  public void shouldAddOrder() throws Exception {
    // given
    var id1 = getId(mockMvc.perform(createSnakeOilProductRequest()));
    var id2 = getId(mockMvc.perform(createProductRequestFrom(ACME_PRODUCT2CREATE)));
    var id3 = getId(mockMvc.perform(createProductRequestFrom(ACME_PRODUCT2CREATE)));

    var productIdList = List.of(id1, id2, id3);
    var order = new Order().emailAddress("a.b@c.d").productId(productIdList);

    // when
    var actual =
        mockMvc
            .perform(
                post("/orders")
                    .content(asJsonString(order))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON))
            .andReturn();

    // then
    var actualCreatedOrder =
        objectMapper.readValue(actual.getResponse().getContentAsString(), CreatedOrder.class);
    assertActualOrder(actualCreatedOrder, productIdList);

    assertThat(actual.getResponse().getStatus()).isEqualTo(200);
    assertThat(orderRepository.findAll()).hasSize(1);
    var actualOrder = orderRepository.findAll().get(0);
    assertActualOrderInDb(actualOrder, productIdList);
  }

  @Test
  public void shouldReturn404ForMissingNonExistingIds() throws Exception {
    // given
    var order = new Order().emailAddress("a.b@c.d").productId(List.of(1L));

    // when
    var actual =
        mockMvc.perform(
            post("/orders")
                .content(asJsonString(order))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

    // then
    actual.andExpect(status().isNotFound());
  }

  @Test
  public void shouldNotLetRegisterOrderWithInvalidEmailAddress() throws Exception {
    // given
    var id1 = getId(mockMvc.perform(createSnakeOilProductRequest()));
    var order = new Order().emailAddress("a.bc.d").productId(List.of(id1));

    // when
    var actual =
        mockMvc.perform(
            post("/orders")
                .content(asJsonString(order))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

    // then
    actual.andExpect(status().isBadRequest());
  }

  @Test
  public void shouldNotLetRegisterInvalidOrder() throws Exception {
    // given
    var order = new Order().productId(List.of(1L));

    // when
    var actual =
        mockMvc.perform(
            post("/orders")
                .content(asJsonString(order))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

    // then
    actual.andExpect(status().isBadRequest());
  }

  private void assertActualOrder(CreatedOrder actualCreatedOrder, List<Long> productIdList) {
    assertThat(actualCreatedOrder.getEmailAddress()).isEqualTo("a.b@c.d");
    assertThat(actualCreatedOrder.getProductId()).isEqualTo(productIdList);
    assertThat(actualCreatedOrder.getPrice())
        .isEqualTo(SNAKE_OIL_PRODUCT.getPrice() + 2 * ACME_PRODUCT2CREATE.getPrice());
    assertThat(actualCreatedOrder.getTimestamp())
        .isAfter(OffsetDateTime.now().minusSeconds(30))
        .isBeforeOrEqualTo(OffsetDateTime.now());
  }

  private void assertActualOrderInDb(OrderDbo actualCreatedOrder, List<Long> productIdList) {
    assertThat(actualCreatedOrder.getEmailAddress()).isEqualTo("a.b@c.d");
    assertThat(actualCreatedOrder.getProductDbos().stream().map(ProductDbo::getId))
        .isEqualTo(productIdList);
    assertThat(actualCreatedOrder.getPrice())
        .isEqualTo(SNAKE_OIL_PRODUCT.getPrice() + 2 * ACME_PRODUCT2CREATE.getPrice());
    assertThat(actualCreatedOrder.getTimestamp())
        .isAfter(OffsetDateTime.now().minusSeconds(30))
        .isBeforeOrEqualTo(OffsetDateTime.now());
  }
}
