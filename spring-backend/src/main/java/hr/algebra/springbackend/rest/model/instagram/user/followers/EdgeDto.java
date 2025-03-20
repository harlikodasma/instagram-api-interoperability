package hr.algebra.springbackend.rest.model.instagram.user.followers;

import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(FIELD)
public class EdgeDto {

  private NodeDto node;
}
