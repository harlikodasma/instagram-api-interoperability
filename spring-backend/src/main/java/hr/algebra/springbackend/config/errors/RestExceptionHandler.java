package hr.algebra.springbackend.config.errors;

import hr.algebra.springbackend.exception.RestClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(RestClientException.class)
  public ResponseEntity<Object> restClientException(RestClientException ex) {
    RestError restError = new RestError(BAD_REQUEST.value(), ex.getErrors());
    return new ResponseEntity<>(restError, BAD_REQUEST);
  }
}
