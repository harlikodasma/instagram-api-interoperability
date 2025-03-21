package hr.algebra.springbackend.rest.service;

import hr.algebra.springbackend.rest.model.instagram.user.followers.UserFollowersDto;
import hr.algebra.springbackend.model.jpa.InstagramUserFollower;
import hr.algebra.springbackend.repository.InstagramUserFollowerRepository;
import hr.algebra.springbackend.rest.util.XmlConversionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

@Service
@RequiredArgsConstructor
public class InstagramUserService {

  @Value("${rapidapi.key}")
  private String rapidapiKey;

  private final InstagramUserFollowerRepository instagramUserFollowerRepository;
  private final XmlConversionUtil xmlConversionUtil;

  private static final RestTemplate restTemplate = new RestTemplate();
  private static final String RAPIDAPI_HOST = "real-time-instagram-scraper-api1.p.rapidapi.com";

  public void updateUserFollowers(String username) {
    UserFollowersDto userFollowers = getUserFollowers(username);
    String xml = xmlConversionUtil.convertToXml(userFollowers, UserFollowersDto.class);
    instagramUserFollowerRepository.save(new InstagramUserFollower(username, xml));
  }

  private UserFollowersDto getUserFollowers(String username) {
    String url = format("https://%s/v1/user_followers_adv?username_or_id=%s", RAPIDAPI_HOST, username);
    HttpEntity<String> httpEntity = createHttpEntityWithHeaders();

    return restTemplate.exchange(url, GET, httpEntity, UserFollowersDto.class).getBody();
  }

  private HttpEntity<String> createHttpEntityWithHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("X-RapidAPI-Key", rapidapiKey);
    headers.set("X-RapidAPI-Host", RAPIDAPI_HOST);

    return new HttpEntity<>(headers);
  }
}
