package hr.algebra.springbackend.rest.service;

import hr.algebra.springbackend.model.jpa.InstagramUserFollower;
import hr.algebra.springbackend.repository.InstagramUserFollowerRepository;
import hr.algebra.springbackend.rest.model.instagram.user.followers.UserFollowersDto;
import hr.algebra.springbackend.rest.util.XmlConversionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  public UserFollowersDto updateAndGetUserFollowers(String username) {
    UserFollowersDto userFollowers = fetchUserFollowers(username);
    String xml = xmlConversionUtil.convertToXml(userFollowers, UserFollowersDto.class);
    return instagramUserFollowerRepository.save(new InstagramUserFollower(username, xml, userFollowers)).getJson();
  }

  public UserFollowersDto createUserFollowers(String username, UserFollowersDto userFollowers) {
    String xml = xmlConversionUtil.convertToXml(userFollowers, UserFollowersDto.class);
    return instagramUserFollowerRepository.save(new InstagramUserFollower(username, xml, userFollowers)).getJson();
  }

  public UserFollowersDto modifyUserFollowers(String username, Long id, UserFollowersDto userFollowers) {
    String latestXml = instagramUserFollowerRepository.getLatestByUsername(username).getXml();
    return instagramUserFollowerRepository.save(new InstagramUserFollower(id, username, latestXml, userFollowers)).getJson();
  }

  @Transactional
  public void deleteUserFollowers(String username, Long id) {
    instagramUserFollowerRepository.deleteByUsernameAndId(username, id);
  }

  private UserFollowersDto fetchUserFollowers(String username) {
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
