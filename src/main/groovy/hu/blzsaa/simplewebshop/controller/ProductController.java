package hu.blzsaa.simplewebshop.controller;

import hu.blzsaa.simple_webshop.api.ProductsApi;
import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.helper.LocationBuilder;
import hu.blzsaa.simplewebshop.service.ProductService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController implements ProductsApi {
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

  @Override
  public ResponseEntity<Product> getProductById(Long productId) {
    LOGGER.info("getting product by id: {}", productId);
    Product product = productService.getProduct(productId);
    LOGGER.info("Returning product: {}", product);
    return ResponseEntity.ok(product);
  }

  @Override
  public ResponseEntity<List<Product>> getAllProducts() {
    LOGGER.info("getting all products");
    List<Product> product = productService.getAllProducts();
    LOGGER.info("Returning {} products", product.size());
    return ResponseEntity.ok(product);
  }

  @Override
  public ResponseEntity<Product> updateProductById(Long productId, Product2Create body) {
    LOGGER.info("incoming request to update product with id: {} to value: {}", productId, body);
    Product product = productService.updateProduct(productId, body);
    LOGGER.info("Update was successful");
    return ResponseEntity.ok(product);
  }
}
