package hu.blzsaa.simplewebshop.it.products;

import static hu.blzsaa.simplewebshop.it.ITTestHelper.asJsonString;
import static hu.blzsaa.simplewebshop.it.ITTestHelper.createSnakeOilProductRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class UpdateProductIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;

  @BeforeEach
  void setUp() throws Exception {
    this.mockMvc.perform(createSnakeOilProductRequest());
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }

  @Test
  public void shouldAddProduct() throws Exception {
    // given
    var productId = productRepository.findAll().get(0).getId();

    // when
    ResultActions perform =
        this.mockMvc.perform(
            put("/products/{id}", productId)
                .content(asJsonString(new Product2Create().name("modified snake oil").price(1355L)))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

    // then
    perform
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("modified snake oil"))
        .andExpect(jsonPath("$.price").value("1355"))
        .andExpect(jsonPath("$.id").exists());

    assertThat(productRepository.findAll()).hasSize(1);
    assertThat(productRepository.findAll().get(0))
        .satisfies(
            actual -> {
              assertThat(actual.getName()).isEqualTo("modified snake oil");
              assertThat(actual.getPrice()).isEqualTo(1355);
              assertThat(actual.getId()).isEqualTo(productId);
            });
  }

  @Test
  public void shouldNotLetUpdateWithMissingInformation() throws Exception {
    // given
    var productId = productRepository.findAll().get(0).getId();

    // when
    ResultActions perform =
        this.mockMvc.perform(
            put("/products/{id}", productId)
                .content(asJsonString(new Product2Create().name("modified snake oil").price(null)))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

    // then
    perform.andExpect(status().isBadRequest());

    assertThat(productRepository.findAll().get(0).getName()).isEqualTo("snake oil");
  }

  @Test
  public void shouldReturn404ForMissingProducts() throws Exception {
    // when
    ResultActions perform =
        this.mockMvc.perform(
            put("/products/{id}", 1L)
                .content(asJsonString(new Product2Create().name("modified snake oil").price(1355L)))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

    // then
    perform.andExpect(status().isNotFound());
  }
}
