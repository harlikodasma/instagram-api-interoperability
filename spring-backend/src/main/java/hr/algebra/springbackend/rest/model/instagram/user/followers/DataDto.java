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
public class DataDto {

  @JsonAlias("edge_followed_by")
  private EdgeFollowedByDto edgeFollowedBy;
}
