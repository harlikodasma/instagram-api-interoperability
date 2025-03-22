package hr.algebra.springbackend.repository;

import hr.algebra.springbackend.model.jpa.InstagramUserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstagramUserFollowerRepository extends JpaRepository<InstagramUserFollower, Long> {

  @Query("SELECT iuf FROM InstagramUserFollower iuf ORDER BY iuf.createdAt DESC LIMIT 1")
  InstagramUserFollower getLatest();

  @Query("SELECT iuf FROM InstagramUserFollower iuf WHERE iuf.username = :username ORDER BY iuf.createdAt DESC LIMIT 1")
  InstagramUserFollower getLatestByUsername(@Param("username") String username);

  void deleteByUsernameAndId(String username, Long id);
}
