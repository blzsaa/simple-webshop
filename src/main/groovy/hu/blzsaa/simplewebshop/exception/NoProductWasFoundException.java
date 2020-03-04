package hu.blzsaa.simplewebshop.exception;

public class NoProductWasFoundException extends RuntimeException {
  public NoProductWasFoundException(Long productId) {
    super("No product was found with id: " + productId);
  }
}
