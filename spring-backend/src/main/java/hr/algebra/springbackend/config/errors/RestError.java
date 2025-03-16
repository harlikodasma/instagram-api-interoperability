package hr.algebra.springbackend.config.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RestError {

  private int status;
  private List<String> errors;
}
