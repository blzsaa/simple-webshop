package hu.blzsaa.simplewebshop.service;

import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.exception.NoProductWasFoundException;
import hu.blzsaa.simplewebshop.mapper.ProductMapper;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductMapper productMapper;
  private final ProductRepository productRepository;

  public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
    this.productMapper = productMapper;
    this.productRepository = productRepository;
  }

  public Product createProduct(Product2Create body) {
    var transformed = productMapper.transform(body);
    ProductDbo saved = productRepository.save(transformed);
    return productMapper.transform(saved);
  }

  public Product getProduct(Long productId) {
    return productRepository
        .findById(productId)
        .map(productMapper::transform)
        .orElseThrow(() -> new NoProductWasFoundException(productId));
  }

  public ProductDbo getProductDbo(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new NoProductWasFoundException(productId));
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll().stream()
        .map(productMapper::transform)
        .collect(Collectors.toList());
  }

  public Product updateProduct(Long productId, Product2Create body) {
    return productRepository
        .findById(productId)
        .map(p -> productMapper.update(p, body))
        .map(productRepository::save)
        .map(productMapper::transform)
        .orElseThrow(() -> new NoProductWasFoundException(productId));
  }
}
