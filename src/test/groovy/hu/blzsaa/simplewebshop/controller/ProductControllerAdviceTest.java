package hu.blzsaa.simplewebshop.controller;

import static org.assertj.core.api.Assertions.assertThat;

import hu.blzsaa.simplewebshop.exception.NoProductWasFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ProductControllerAdviceTest {
  private ProductControllerAdvice underTest;

  @BeforeEach
  void setUp() {
    underTest = new ProductControllerAdvice();
  }

  @Test
  void shouldReturn404WhenNoProductWasFoundExceptionOccurs() {
    // given
    var exception = new NoProductWasFoundException(1L);

    // when
    var actual = underTest.handleConflict(exception);

    // then
    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
