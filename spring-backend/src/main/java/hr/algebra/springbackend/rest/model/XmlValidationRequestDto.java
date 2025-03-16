package hr.algebra.springbackend.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XmlValidationRequestDto {

  private String xml;
  private String validator;
}
