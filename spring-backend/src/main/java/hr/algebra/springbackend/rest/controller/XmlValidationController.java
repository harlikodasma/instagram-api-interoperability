package hr.algebra.springbackend.rest.controller;

import hr.algebra.springbackend.rest.exception.RestClientException;
import hr.algebra.springbackend.service.XmlValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hr.algebra.springbackend.model.enums.ValidationType.RNG;
import static hr.algebra.springbackend.model.enums.ValidationType.XSD;
import static hr.algebra.springbackend.service.XmlValidationService.INSTAGRAM_USER_FOLLOWERS_RNG_VALIDATION_FILE;
import static hr.algebra.springbackend.service.XmlValidationService.INSTAGRAM_USER_FOLLOWERS_XSD_VALIDATION_FILE;

@RestController
@RequiredArgsConstructor
@RequestMapping("xml/validate")
public class XmlValidationController {

  private final XmlValidationService xmlValidationService;

  @PostMapping("xsd")
  public void validateByXsd(@RequestBody String xml) {
    xmlValidationService.validate(xml, XSD, INSTAGRAM_USER_FOLLOWERS_XSD_VALIDATION_FILE, RestClientException::new);
  }

  @PostMapping("rng")
  public void validateByRng(@RequestBody String xml) {
    xmlValidationService.validate(xml, RNG, INSTAGRAM_USER_FOLLOWERS_RNG_VALIDATION_FILE, RestClientException::new);
  }
}
