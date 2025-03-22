package hr.algebra.springbackend.model.jpa;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import hr.algebra.springbackend.rest.converter.UserFollowersDtoConverter;
import hr.algebra.springbackend.rest.model.instagram.user.followers.UserFollowersDto;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

  @Column(name = "json", nullable = false, columnDefinition = "jsonb")
  @Type(JsonBinaryType.class)
  @Convert(converter = UserFollowersDtoConverter.class)
  private UserFollowersDto json;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private LocalDateTime createdAt;

  public InstagramUserFollower(String username, String xml, UserFollowersDto json) {
    this.username = username;
    this.xml = xml;
    this.json = json;
  }

  public InstagramUserFollower(Long id, String username, String xml, UserFollowersDto json) {
    this.id = id;
    this.username = username;
    this.xml = xml;
    this.json = json;
  }
}
