package hr.algebra.springbackend.soap.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class SoapClientException extends RuntimeException {

  private final List<String> errors;

  public SoapClientException(List<String> errors) {
    super("SOAP client exception: " + errors);
    this.errors = errors;
  }

  public SoapClientException(String message) {
    super("SOAP client exception: " + message);
    this.errors = List.of(message);
  }
}
