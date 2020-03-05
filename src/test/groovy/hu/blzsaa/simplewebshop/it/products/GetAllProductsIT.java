package hu.blzsaa.simplewebshop.it.products;

import static hu.blzsaa.simplewebshop.TestConstants.PRODUCT_DBO;
import static hu.blzsaa.simplewebshop.TestConstants.SNAKE_OIL_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class GetAllProductsIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }

  @Test
  public void shouldReturnProduct() throws Exception {
    // given
    var otherProduct = new ProductDbo(null, "asd", 111L);
    productRepository.save(PRODUCT_DBO);
    productRepository.save(otherProduct);

    // when
    ResultActions actual = this.mockMvc.perform(get("/products").accept(APPLICATION_JSON));

    // then
    actual.andExpect(status().isOk());
    var actualProducts = convert(actual.andReturn().getResponse().getContentAsString());
    assertThat(actualProducts)
        .hasSize(2)
        .usingElementComparatorIgnoringFields("id")
        .containsOnly(SNAKE_OIL_PRODUCT, new Product().name("asd").price(111L));
  }

  public static List<Product> convert(final String obj) {
    try {
      return new ObjectMapper().readValue(obj, new TypeReference<>() {});
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
