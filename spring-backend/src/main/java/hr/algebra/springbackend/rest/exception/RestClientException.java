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
}
