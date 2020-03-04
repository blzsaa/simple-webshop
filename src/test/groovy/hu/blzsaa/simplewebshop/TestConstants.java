package hu.blzsaa.simplewebshop;

import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;

public class TestConstants {
  public static final Product2Create SNAKE_OIL_PRODUCT2CREATE =
      new Product2Create().name("snake oil").price(135L);

  public static final Product SNAKE_OIL_PRODUCT =
      new Product().id(3L).name("snake oil").price(135L);

  public static final ProductDbo PRODUCT_DBO = new ProductDbo(3L, "snake oil", 135L);
}
