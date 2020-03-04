package hu.blzsaa.simplewebshop.service;

import static hu.blzsaa.simplewebshop.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.exception.NoProductWasFoundException;
import hu.blzsaa.simplewebshop.mapper.ProductMapper;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class ProductServiceTest {
  @Mock private ProductMapper productMapper;
  @Mock private ProductRepository productRepository;
  private ProductService underTest;

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

  @Test
  void shouldGetProductByIdIfFound() {
    // given
    doReturn(Optional.of(PRODUCT_DBO)).when(productRepository).findById(123L);
    doReturn(SNAKE_OIL_PRODUCT).when(productMapper).transform(PRODUCT_DBO);

    // when
    var actual = underTest.getProduct(123L);

    // then
    assertThat(actual).isEqualTo(SNAKE_OIL_PRODUCT);
  }

  @Test
  void shouldThrowNoProductFoundExceptionWhenThereIsNoProductWithGivenId() {
    // given
    doReturn(Optional.empty()).when(productRepository).findById(123L);

    // when
    var actual = catchThrowable(() -> underTest.getProduct(123L));

    // then
    assertThat(actual).isInstanceOf(NoProductWasFoundException.class).hasMessageContaining("123");
  }

  @Test
  void shouldUpdateProductByIdIfFound() {
    // given
    var productDbo = new ProductDbo();
    productDbo.setName("something");
    doReturn(Optional.of(PRODUCT_DBO)).when(productRepository).findById(123L);
    doReturn(productDbo).when(productMapper).update(PRODUCT_DBO, SNAKE_OIL_PRODUCT2CREATE);
    doReturn(productDbo).when(productRepository).save(productDbo);
    doReturn(SNAKE_OIL_PRODUCT).when(productMapper).transform(productDbo);

    // when
    var actual = underTest.updateProduct(123L, SNAKE_OIL_PRODUCT2CREATE);

    // then
    assertThat(actual).isEqualTo(SNAKE_OIL_PRODUCT);
  }

  @Test
  void shouldThrowNoProductFoundExceptionWhenThereIsNoProductWithGivenIdForUpdate() {
    // given
    doReturn(Optional.empty()).when(productRepository).findById(123L);

    // when
    var actual = catchThrowable(() -> underTest.updateProduct(123L, SNAKE_OIL_PRODUCT2CREATE));

    // then
    assertThat(actual).isInstanceOf(NoProductWasFoundException.class).hasMessageContaining("123");
  }

  @Test
  void shouldGetProductDboByIdIfFound() {
    // given
    doReturn(Optional.of(PRODUCT_DBO)).when(productRepository).findById(123L);

    // when
    var actual = underTest.getProductDbo(123L);

    // then
    assertThat(actual).isEqualTo(PRODUCT_DBO);
  }

  @Test
  void shouldThrowNoProductDboFoundExceptionWhenThereIsNoProductWithGivenId() {
    // given
    doReturn(Optional.empty()).when(productRepository).findById(123L);

    // when
    var actual = catchThrowable(() -> underTest.getProductDbo(123L));

    // then
    assertThat(actual).isInstanceOf(NoProductWasFoundException.class).hasMessageContaining("123");
  }
}
