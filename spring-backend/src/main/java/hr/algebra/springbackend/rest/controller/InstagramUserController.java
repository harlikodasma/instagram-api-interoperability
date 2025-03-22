package hr.algebra.springbackend.rest.controller;

import hr.algebra.springbackend.rest.model.instagram.user.followers.UserFollowersDto;
import hr.algebra.springbackend.rest.service.InstagramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("instagram/user/{username}/followers")
public class InstagramUserController {

  private final InstagramUserService instagramUserService;

  @GetMapping
  public UserFollowersDto updateAndGetUserFollowers(@PathVariable String username) {
    return instagramUserService.updateAndGetUserFollowers(username);
  }

  @PostMapping
  public UserFollowersDto createUserFollowers(@PathVariable String username, @RequestBody UserFollowersDto userFollowersDto) {
    return instagramUserService.createUserFollowers(username, userFollowersDto);
  }

  @PutMapping("{id}")
  public UserFollowersDto modifyUserFollowers(@PathVariable String username, @PathVariable Long id, @RequestBody UserFollowersDto userFollowersDto) {
    return instagramUserService.modifyUserFollowers(username, id, userFollowersDto);
  }

  @DeleteMapping("{id}")
  public void deleteUserFollowers(@PathVariable String username, @PathVariable Long id) {
    instagramUserService.deleteUserFollowers(username, id);
  }
}
