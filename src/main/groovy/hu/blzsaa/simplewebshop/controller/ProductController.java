package hu.blzsaa.simplewebshop.controller;

import hu.blzsaa.simple_webshop.api.ProductApi;
import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.helper.LocationBuilder;
import hu.blzsaa.simplewebshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController implements ProductApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

  private final ProductService productService;
  private final LocationBuilder locationBuilder;

  public ProductController(ProductService productService, LocationBuilder locationBuilder) {
    this.productService = productService;
    this.locationBuilder = locationBuilder;
  }

  @Override
  public ResponseEntity<Product> addProduct(Product2Create body) {
    LOGGER.info("incoming product to register: {}", body);
    Product product = productService.createProduct(body);
    LOGGER.info("Product saved: {}", product);
    return ResponseEntity.created(locationBuilder.getLocation(product)).body(product);
  }
}
