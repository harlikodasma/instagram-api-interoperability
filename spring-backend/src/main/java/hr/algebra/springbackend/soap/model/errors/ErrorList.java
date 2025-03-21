package hr.algebra.springbackend.soap.model.errors;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static hr.algebra.springbackend.soap.config.WebServiceConfig.NAMESPACE_URI;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(FIELD)
public class ErrorList {

  @XmlElement(name = "error", namespace = NAMESPACE_URI)
  private List<String> errors;
}
