package hr.algebra.springbackend.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "submissions")
public class Submission {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "xml_content", nullable = false)
  private String xmlContent;

  @Column(name = "validator_content", nullable = false)
  private String validatorContent;

  @Enumerated(STRING)
  @Column(name = "validator_type", nullable = false)
  private ValidationType validatorType;

  public Submission(String xmlContent, String validatorContent, ValidationType validatorType) {
    this.xmlContent = xmlContent;
    this.validatorContent = validatorContent;
    this.validatorType = validatorType;
  }
}
