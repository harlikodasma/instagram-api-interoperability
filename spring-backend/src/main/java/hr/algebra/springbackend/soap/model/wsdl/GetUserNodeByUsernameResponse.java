package hr.algebra.springbackend.soap.model.wsdl;

import hr.algebra.springbackend.soap.model.xml.User;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import static hr.algebra.springbackend.soap.config.WebServiceConfig.NAMESPACE_URI;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@Data
@NoArgsConstructor
@XmlRootElement(name = "GetUserNodeByUsernameResponse", namespace = NAMESPACE_URI)
@XmlAccessorType(FIELD)
public class GetUserNodeByUsernameResponse {

  @XmlElement(required = true)
  private User user;

  @XmlElement
  private String error;

  public GetUserNodeByUsernameResponse(User user) {
    this.user = user;
  }

  public GetUserNodeByUsernameResponse(String error) {
    this.error = error;
  }
}
