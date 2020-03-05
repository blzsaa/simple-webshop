package hu.blzsaa.simplewebshop.it.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.blzsaa.simplewebshop.it.ITTestHelper;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class CreateProductIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }

  @Test
  public void shouldAddProduct() throws Exception {
    // when
    ResultActions perform = mockMvc.perform(ITTestHelper.createSnakeOilProductRequest());

    // then
    perform
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("snake oil"))
        .andExpect(jsonPath("$.price").value("135"))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(redirectedUrlPattern("http://localhost/products/*"));

    assertThat(productRepository.findAll()).hasSize(1);
    assertThat(productRepository.findAll().get(0))
        .satisfies(
            actual -> {
              assertThat(actual.getName()).isEqualTo("snake oil");
              assertThat(actual.getPrice()).isEqualTo(135);
              assertThat(actual.getId()).isNotNull();
            });
  }

  @Test
  public void locationShouldPointToCorrectProduct() throws Exception {
    // given
    ResultActions perform = mockMvc.perform(ITTestHelper.createSnakeOilProductRequest());

    // when
    var mvcResult =
        mockMvc
            .perform(
                get(Objects.requireNonNull(perform.andReturn().getResponse().getRedirectedUrl()))
                    .accept(APPLICATION_JSON))
            .andExpect(status().isOk());

    // then
    mvcResult
        .andExpect(jsonPath("$.name").value("snake oil"))
        .andExpect(jsonPath("$.price").value("135"))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void shouldNotLetRegisterInvalidProducts() throws Exception {
    // when
    ResultActions perform =
        mockMvc.perform(ITTestHelper.createProductRequestFrom("{\"name\":\"name\"}"));

    // then
    perform.andExpect(status().isBadRequest());

    assertThat(productRepository.findAll()).isEmpty();
  }
}
