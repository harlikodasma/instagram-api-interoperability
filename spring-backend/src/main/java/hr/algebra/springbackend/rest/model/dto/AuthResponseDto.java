package hr.algebra.springbackend.rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

  private String accessToken;
  private String refreshToken;
}
