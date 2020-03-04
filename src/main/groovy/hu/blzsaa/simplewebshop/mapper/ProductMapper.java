package hu.blzsaa.simplewebshop.mapper;

import hu.blzsaa.simple_webshop.model.Product;
import hu.blzsaa.simple_webshop.model.Product2Create;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "metaClass", ignore = true)
  ProductDbo transform(Product2Create product2Create);

  Product transform(ProductDbo productDbo);

  @Mapping(target = "name", source = "product2Create.name")
  @Mapping(target = "price", source = "product2Create.price")
  ProductDbo update(ProductDbo productDbo, Product2Create product2Create);
}
