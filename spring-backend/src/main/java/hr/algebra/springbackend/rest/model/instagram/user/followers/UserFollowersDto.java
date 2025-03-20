package hr.algebra.springbackend.rest.model.instagram.user.followers;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "userFollowers")
@XmlAccessorType(FIELD)
public class UserFollowersDto {

  private DataDto data;
  private String status;
}
