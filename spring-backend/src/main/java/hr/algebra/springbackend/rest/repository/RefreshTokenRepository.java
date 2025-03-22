package hr.algebra.springbackend.rest.repository;

import hr.algebra.springbackend.rest.model.jpa.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);
}
