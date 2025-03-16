package hr.algebra.springbackend.rest.controller;

import hr.algebra.springbackend.rest.model.XmlValidationRequestDto;
import hr.algebra.springbackend.rest.service.XmlValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hr.algebra.springbackend.rest.model.ValidationType.RNG;
import static hr.algebra.springbackend.rest.model.ValidationType.XSD;

@RestController
@RequiredArgsConstructor
@RequestMapping("xml/validate")
public class XmlValidationController {

  private final XmlValidationService xmlValidationService;

  @PostMapping("xsd")
  public void validateByXsd(@RequestBody XmlValidationRequestDto validationRequestDto) {
    xmlValidationService.validate(validationRequestDto, XSD);
  }

  @PostMapping("rng")
  public void validateByRng(@RequestBody XmlValidationRequestDto validationRequestDto) {
    xmlValidationService.validate(validationRequestDto, RNG);
  }
}
