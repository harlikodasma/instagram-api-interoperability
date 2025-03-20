package hr.algebra.springbackend.rest.model.instagram.user.followers;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import static java.util.Collections.emptyList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(FIELD)
public class EdgeFollowedByDto {

  private Long count;

  @JsonAlias("page_info")
  private PageInfoDto pageInfo;

  @XmlTransient
  private List<EdgeDto> edges;

  @XmlElementWrapper(name = "edges")
  @XmlElement(name = "node")
  public List<NodeDto> getNodes() {
    if (edges == null) return emptyList();
    return edges.stream()
      .map(EdgeDto::getNode)
      .toList();
  }
}
