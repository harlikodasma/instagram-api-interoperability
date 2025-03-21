package hr.algebra.springbackend.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "instagram_user_followers")
public class InstagramUserFollower {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "xml", nullable = false)
  private String xml;

  @Column(name = "created_at", nullable = false, insertable = false)
  private LocalDateTime createdAt;

  public InstagramUserFollower(String username, String xml) {
    this.username = username;
    this.xml = xml;
  }
}
