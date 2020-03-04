package hu.blzsaa.simplewebshop.mapper;

import hu.blzsaa.simple_webshop.model.CreatedOrder;
import hu.blzsaa.simple_webshop.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  @Mapping(target = "timestamp", expression = "java( java.time.OffsetDateTime.now() )")
  CreatedOrder transform(Order order, long price);
}
