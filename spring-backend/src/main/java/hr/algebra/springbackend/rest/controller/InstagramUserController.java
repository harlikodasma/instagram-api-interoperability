package hr.algebra.springbackend.rest.controller;

import hr.algebra.springbackend.rest.service.InstagramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("instagram/user")
public class InstagramUserController {

  private final InstagramUserService instagramUserService;

  @GetMapping("{username}/followers/update")
  public void updateUserFollowers(@PathVariable String username) {
    instagramUserService.updateUserFollowers(username);
  }
}
