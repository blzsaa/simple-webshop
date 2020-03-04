package hu.blzsaa.simplewebshop.mapper;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import hu.blzsaa.simplewebshop.dbo.OrderDbo;
import hu.blzsaa.simplewebshop.dbo.ProductDbo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "metaClass", ignore = true)
  @Mapping(target = "timestamp", expression = "java( java.time.OffsetDateTime.now() )")
  @Mapping(target = "productDbos", source = "productDboList")
  OrderDbo transform(Order order, List<ProductDbo> productDboList, long price);

  @Mapping(
      target = "productId",
      expression =
          "java( saved.getProductDbos().stream().map(hu.blzsaa.simplewebshop.dbo.ProductDbo::getId).collect(java.util.stream.Collectors.toList()) )")
  CreatedOrder transform(OrderDbo saved);
}
