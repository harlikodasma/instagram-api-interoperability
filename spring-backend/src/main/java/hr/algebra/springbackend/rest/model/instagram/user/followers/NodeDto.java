package hr.algebra.springbackend.rest.model.instagram.user.followers;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(FIELD)
public class NodeDto {

  private Long id;
  private String username;

  @JsonAlias("full_name")
  private String fullName;

  @JsonAlias("profile_pic_url")
  private String profilePicUrl;

  @JsonAlias("is_private")
  private boolean isPrivate;

  @JsonAlias("is_verified")
  private boolean isVerified;
}
