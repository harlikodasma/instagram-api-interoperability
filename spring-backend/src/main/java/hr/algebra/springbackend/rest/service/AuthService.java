package hr.algebra.springbackend.rest.service;

import hr.algebra.springbackend.rest.exception.RestClientException;
import hr.algebra.springbackend.rest.model.dto.AuthResponseDto;
import hr.algebra.springbackend.rest.model.dto.LoginRequestDto;
import hr.algebra.springbackend.rest.model.dto.RefreshTokenRequestDto;
import hr.algebra.springbackend.rest.model.jpa.RefreshToken;
import hr.algebra.springbackend.rest.model.jpa.User;
import hr.algebra.springbackend.rest.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final RefreshTokenService refreshTokenService;

  public AuthResponseDto login(LoginRequestDto loginRequestDto) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
    User user = (User) authentication.getPrincipal();

    String accessToken = jwtUtil.generateToken(user);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

    return new AuthResponseDto(accessToken, refreshToken.getToken());
  }

  public AuthResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
    String refreshToken = refreshTokenRequestDto.getRefreshToken();
    return refreshTokenService.findByToken(refreshToken)
      .map(refreshTokenService::verifyExpiration)
      .map(RefreshToken::getUser)
      .map(user -> {
        String newAccessToken = jwtUtil.generateToken(user);
        return new AuthResponseDto(newAccessToken, refreshToken);
      })
      .orElseThrow(() -> new RestClientException("Invalid refresh token"));
  }
}
