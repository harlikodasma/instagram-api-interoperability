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
@XmlRootElement(name = "GetUserNodeByUsernameResponse", namespace = NAMESPACE_URI)
@XmlAccessorType(FIELD)
public class GetUserNodeByUsernameResponse {

  @XmlElement(required = true)
  private Long id;

  @XmlElement(required = true)
  private String username;

  @XmlElement(required = true)
  private String fullName;

  @XmlElement(required = true)
  private String profilePicUrl;

  @XmlElement(required = true)
  private Boolean isPrivate;

  @XmlElement(required = true)
  private Boolean isVerified;

  @XmlElement
  private String error;

  public GetUserNodeByUsernameResponse(String error) {
    this.error = error;
  }
}
