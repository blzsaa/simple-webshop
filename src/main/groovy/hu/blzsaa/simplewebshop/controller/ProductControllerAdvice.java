package hu.blzsaa.simplewebshop.controller;

import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simplewebshop.exception.NoProductWasFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvice {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductControllerAdvice.class);

  @ExceptionHandler(NoProductWasFoundException.class)
  protected ResponseEntity<Product> handleConflict(NoProductWasFoundException ex) {
    LOGGER.info("No product was found", ex);
    return ResponseEntity.notFound().build();
  }
}
