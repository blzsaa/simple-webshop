package hu.blzsaa.simplewebshop.service;

import static hu.blzsaa.simplewebshop.TestConstants.SNAKE_OIL_PRODUCT;
import static hu.blzsaa.simplewebshop.TestConstants.SNAKE_OIL_PRODUCT2CREATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.mapper.ProductMapper;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class ProductServiceTest {
  @Mock private ProductMapper productMapper;
  @Mock private ProductRepository productRepository;
  private ProductService underTest;
  private static final ProductDbo PRODUCT_DBO = new ProductDbo(12L, "name", 123L);

  @BeforeEach
  void setUp() {
    initMocks(this);
    underTest = new ProductService(productMapper, productRepository);
  }

  @Test
  void shouldTransformAndSave() {
    // given
    doReturn(PRODUCT_DBO).when(productMapper).transform(SNAKE_OIL_PRODUCT2CREATE);
    doReturn(PRODUCT_DBO).when(productRepository).save(PRODUCT_DBO);
    doReturn(SNAKE_OIL_PRODUCT).when(productMapper).transform(PRODUCT_DBO);

    // when
    var actual = underTest.createProduct(SNAKE_OIL_PRODUCT2CREATE);

    // then
    assertThat(actual).isEqualTo(SNAKE_OIL_PRODUCT);
  }
}
