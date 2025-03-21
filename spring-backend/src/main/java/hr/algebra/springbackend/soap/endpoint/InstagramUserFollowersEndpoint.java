package hr.algebra.springbackend.soap.endpoint;

import hr.algebra.springbackend.soap.service.InstagramUserFollowersService;
import hr.algebra.springbackend.soap.model.wsdl.GetUserNodeByUsernameRequest;
import hr.algebra.springbackend.soap.model.wsdl.GetUserNodeByUsernameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static hr.algebra.springbackend.soap.config.WebServiceConfig.NAMESPACE_URI;

@Endpoint
@RequiredArgsConstructor
public class InstagramUserFollowersEndpoint {

  private final InstagramUserFollowersService instagramUserFollowersService;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserNodeByUsernameRequest")
  @ResponsePayload
  public GetUserNodeByUsernameResponse getUserNodeByUsername(@RequestPayload GetUserNodeByUsernameRequest request) {
    return instagramUserFollowersService.getUserNodeByUsername(request.getUsername());
  }
}
