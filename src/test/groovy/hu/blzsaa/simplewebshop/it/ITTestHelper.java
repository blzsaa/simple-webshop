package hu.blzsaa.simplewebshop.it;

import static hu.blzsaa.simplewebshop.TestConstants.SNAKE_OIL_PRODUCT2CREATE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class ITTestHelper {
  static MockHttpServletRequestBuilder createProductRequestFrom(String json) {
    return post("/products").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON);
  }

  static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static MockHttpServletRequestBuilder createSnakeOilProductRequest() {
    return createProductRequestFrom(SNAKE_OIL_PRODUCT2CREATE);
  }

  public static MockHttpServletRequestBuilder createProductRequestFrom(
      Product2Create product2Create) {
    return createProductRequestFrom(asJsonString(product2Create));
  }

  public static Long getId(final ResultActions obj) {
    try {
      return new ObjectMapper()
          .readValue(obj.andReturn().getResponse().getContentAsString(), Product.class)
          .getId();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
