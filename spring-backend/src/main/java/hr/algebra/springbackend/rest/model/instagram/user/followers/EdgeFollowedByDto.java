package hr.algebra.springbackend.rest.model.instagram.user.followers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("edges")
  private List<EdgeDto> edges;

  @XmlTransient
  @JsonIgnore
  private List<NodeDto> nodes;

  @XmlElementWrapper(name = "edges")
  @XmlElement(name = "node")
  @JsonIgnore
  public List<NodeDto> getNodes() {
    if (edges == null) return emptyList();
    return edges.stream()
      .map(EdgeDto::getNode)
      .toList();
  }
}
