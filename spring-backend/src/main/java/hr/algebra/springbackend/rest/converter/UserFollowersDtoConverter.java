package hr.algebra.springbackend.rest.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.springbackend.rest.exception.RestClientException;
import hr.algebra.springbackend.rest.model.instagram.user.followers.UserFollowersDto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserFollowersDtoConverter implements AttributeConverter<UserFollowersDto, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(UserFollowersDto userFollowersDto) {
    try {
      return objectMapper.writeValueAsString(userFollowersDto);
    } catch (Exception e) {
      throw new RestClientException("Could not convert object to JSON");
    }
  }

  @Override
  public UserFollowersDto convertToEntityAttribute(String json) {
    try {
      return json == null ? null : objectMapper.readValue(json, UserFollowersDto.class);
    } catch (Exception e) {
      throw new RestClientException("Could not convert JSON to object");
    }
  }
}
