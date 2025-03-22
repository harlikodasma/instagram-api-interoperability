package hr.algebra.springbackend.rest.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class RestClientException extends RuntimeException {

  private final List<String> errors;

  public RestClientException(List<String> errors) {
    super("REST client exception: " + errors);
    this.errors = errors;
  }

  public RestClientException(String message) {
    super("REST client exception: " + message);
    this.errors = List.of(message);
  }
}
