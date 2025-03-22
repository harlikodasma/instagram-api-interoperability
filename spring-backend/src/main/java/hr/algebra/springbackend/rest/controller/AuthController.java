package hr.algebra.springbackend.rest.controller;

import hr.algebra.springbackend.rest.model.dto.LoginRequestDto;
import hr.algebra.springbackend.rest.model.dto.AuthResponseDto;
import hr.algebra.springbackend.rest.model.dto.RefreshTokenRequestDto;
import hr.algebra.springbackend.rest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("login")
  public AuthResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
    return authService.login(loginRequestDto);
  }

  @PostMapping("refresh")
  public AuthResponseDto refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
    return authService.refresh(refreshTokenRequestDto);
  }
}
