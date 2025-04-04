package hr.algebra.springbackend.soap.model.wsdl;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static hr.algebra.springbackend.soap.config.WebServiceConfig.NAMESPACE_URI;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "GetUserNodeByUsernameRequest", namespace = NAMESPACE_URI)
@XmlAccessorType(FIELD)
public class GetUserNodeByUsernameRequest {

  @XmlElement(namespace = NAMESPACE_URI, required = true)
  private String username;
}
