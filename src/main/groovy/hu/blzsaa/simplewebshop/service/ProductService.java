package hu.blzsaa.simplewebshop.service;

import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import hu.blzsaa.simplewebshop.mapper.ProductMapper;
import hu.blzsaa.simplewebshop.repository.ProductRepository;
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
}
