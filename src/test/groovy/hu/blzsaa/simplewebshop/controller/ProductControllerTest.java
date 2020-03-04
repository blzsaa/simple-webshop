package hu.blzsaa.simplewebshop.controller;

import static hu.blzsaa.simplewebshop.TestConstants.SNAKE_OIL_PRODUCT;
import static hu.blzsaa.simplewebshop.TestConstants.SNAKE_OIL_PRODUCT2CREATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import hu.blzsaa.simplewebshop.helper.LocationBuilder;
import hu.blzsaa.simplewebshop.service.ProductService;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

class ProductControllerTest {
  private ProductController underTest;
  @Mock private ProductService productService;
  @Mock private LocationBuilder locationBuilder;

  @BeforeEach
  void setUp() {
    initMocks(this);
    underTest = new ProductController(productService, locationBuilder);
  }

  @Test
  void addProductShouldCallServiceAndReturnWithCreatedProductAndItsLocation() {
    // given
    doReturn(SNAKE_OIL_PRODUCT).when(productService).createProduct(SNAKE_OIL_PRODUCT2CREATE);
    var uri = URI.create("uri");
    doReturn(uri).when(locationBuilder).getLocation(SNAKE_OIL_PRODUCT);

    // when
    var actual = underTest.addProduct(SNAKE_OIL_PRODUCT2CREATE);

    // then
    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(actual.getHeaders().get("Location")).containsOnly("uri");
    assertThat(actual.getBody()).isEqualTo(SNAKE_OIL_PRODUCT);
  }
}
