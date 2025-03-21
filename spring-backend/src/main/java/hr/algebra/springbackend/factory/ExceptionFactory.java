package hr.algebra.springbackend.factory;

import java.util.List;

@FunctionalInterface
public interface ExceptionFactory {

  RuntimeException create(List<String> errors);
}
