package hu.blzsaa.simplewebshop.it;

import static hu.blzsaa.simplewebshop.TestConstants.PRODUCT_DBO;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.blzsaa.simplewebshop.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class GetProductIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }

  @Test
  public void shouldReturnProduct() throws Exception {
    // given
    var saved = productRepository.save(PRODUCT_DBO);

    // when
    ResultActions perform =
        this.mockMvc.perform(get("/products/{id}", saved.getId()).accept(APPLICATION_JSON));

    // then
    perform
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("snake oil"))
        .andExpect(jsonPath("$.price").value("135"))
        .andExpect(jsonPath("$.id").value(saved.getId()));
  }

  @Test
  public void shouldNotLetSearchWithInvalidId() throws Exception {
    // when
    ResultActions perform = this.mockMvc.perform(get("/products/asd").accept(APPLICATION_JSON));

    // then
    perform.andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturn404ForMissingProducts() throws Exception {
    // when
    ResultActions perform =
        this.mockMvc.perform(get("/products/{id}", 1111L).accept(APPLICATION_JSON));

    // then
    perform.andExpect(status().isNotFound());
  }
}
