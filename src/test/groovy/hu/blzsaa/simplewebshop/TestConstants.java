package hu.blzsaa.simplewebshop;

import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class TestConstants {
  public static final Product2Create SNAKE_OIL_PRODUCT2CREATE =
      new Product2Create().name("snake oil").price(135L);

  public static final Product2Create ACME_PRODUCT2CREATE =
      new Product2Create().name("ACME").price(1000L);

  public static final Product SNAKE_OIL_PRODUCT =
      new Product().id(3L).name("snake oil").price(135L);

  public static final ProductDbo PRODUCT_DBO = new ProductDbo(3L, "snake oil", 135L);
  public static final OffsetDateTime OFFSET_DATE_TIME =
      OffsetDateTime.of(2000, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);

  public static final OffsetDateTime FROM = OFFSET_DATE_TIME.minusSeconds(2);
  public static final OffsetDateTime TO = OFFSET_DATE_TIME.plusSeconds(2);
}
