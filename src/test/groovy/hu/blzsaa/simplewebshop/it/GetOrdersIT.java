package hu.blzsaa.simplewebshop.it;

import static hu.blzsaa.simplewebshop.TestConstants.OFFSET_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simplewebshop.dbo.OrderDbo;
import hu.blzsaa.simplewebshop.repository.OrderRepository;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GetOrdersIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private OrderRepository orderRepository;

  @BeforeEach
  void setUp() {
    orderRepository.save(
        OrderDbo.builder().timestamp(OFFSET_DATE_TIME.minusMinutes(3)).price(1L).build());
    orderRepository.save(
        OrderDbo.builder().timestamp(OFFSET_DATE_TIME.minusMinutes(1)).price(2L).build());
    orderRepository.save(OrderDbo.builder().timestamp(OFFSET_DATE_TIME).price(3L).build());
    orderRepository.save(
        OrderDbo.builder().timestamp(OFFSET_DATE_TIME.plusMinutes(1)).price(4L).build());
    orderRepository.save(
        OrderDbo.builder().timestamp(OFFSET_DATE_TIME.plusMinutes(3)).price(5L).build());
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
    orderRepository.deleteAll();
  }

  @Test
  public void shouldOrdersShouldReturnEverything() throws Exception {
    // when
    var actual = mockMvc.perform(get("/orders").accept(APPLICATION_JSON)).andReturn();

    // then
    assertThat(actual.getResponse().getStatus()).isEqualTo(200);
    List<CreatedOrder> actualCreatedOrder =
        objectMapper.readValue(actual.getResponse().getContentAsString(), new TypeReference<>() {});
    assertThat(actualCreatedOrder)
        .hasSize(5)
        .extracting("price")
        .containsExactly(1L, 2L, 3L, 4L, 5L);
  }

  @Test
  public void shouldOrdersShouldReturnOnlyTheOnesThatAreBetweenFromAndTo() throws Exception {
    // when
    var actual =
        mockMvc
            .perform(
                get("/orders")
                    .param("from", OFFSET_DATE_TIME.minusMinutes(2).toString())
                    .param("to", OFFSET_DATE_TIME.plusMinutes(2).toString())
                    .accept(APPLICATION_JSON))
            .andReturn();

    // then
    assertThat(actual.getResponse().getStatus()).isEqualTo(200);
    List<CreatedOrder> actualCreatedOrder =
        objectMapper.readValue(actual.getResponse().getContentAsString(), new TypeReference<>() {});
    assertThat(actualCreatedOrder).hasSize(3).extracting("price").containsExactly(2L, 3L, 4L);
  }
}
