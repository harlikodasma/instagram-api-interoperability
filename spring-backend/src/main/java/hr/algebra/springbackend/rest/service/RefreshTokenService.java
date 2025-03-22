package hr.algebra.springbackend.rest.service;

import hr.algebra.springbackend.rest.exception.RestClientException;
import hr.algebra.springbackend.rest.model.jpa.RefreshToken;
import hr.algebra.springbackend.rest.repository.RefreshTokenRepository;
import hr.algebra.springbackend.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  @Value("${jwt.refresh-token.expiration}")
  private long refreshTokenExpiration;

  public RefreshToken createRefreshToken(String username) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUser(userRepository.findByUsername(username).orElseThrow());
    refreshToken.setToken(randomUUID().toString());
    refreshToken.setExpiresAt(now().plusMillis(refreshTokenExpiration));
    return refreshTokenRepository.save(refreshToken);
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiresAt().isBefore(now())) {
      refreshTokenRepository.delete(token);
      throw new RestClientException("Refresh token has expired");
    }
    return token;
  }
}
