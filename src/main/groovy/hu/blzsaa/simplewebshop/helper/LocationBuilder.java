package hu.blzsaa.simplewebshop.helper;

import hu.blzsaa.simple_webshop.model.Product;
import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class LocationBuilder {
  public URI getLocation(Product product) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(product.getId())
        .toUri();
  }
}
