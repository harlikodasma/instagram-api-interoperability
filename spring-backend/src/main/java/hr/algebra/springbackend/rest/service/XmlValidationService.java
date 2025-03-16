package hr.algebra.springbackend.rest.service;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.validate.ValidationDriver;
import hr.algebra.springbackend.rest.exception.RestClientException;
import hr.algebra.springbackend.rest.handler.XmlErrorHandler;
import hr.algebra.springbackend.rest.model.Submission;
import hr.algebra.springbackend.rest.model.ValidationType;
import hr.algebra.springbackend.rest.model.XmlValidationRequestDto;
import hr.algebra.springbackend.rest.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.thaiopensource.validate.ValidateProperty.ERROR_HANDLER;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class XmlValidationService {

  private final SubmissionRepository submissionRepository;

  public void validate(XmlValidationRequestDto validationRequestDto, ValidationType validationType) {
    List<String> errors = getValidationErrors(validationRequestDto, validationType);

    if (isNotEmpty(errors)) {
      throw new RestClientException(errors);
    }

    submissionRepository.save(new Submission(validationRequestDto.getXml(), validationRequestDto.getValidator(), validationType));
  }

  private List<String> getValidationErrors(XmlValidationRequestDto validationRequestDto, ValidationType validationType) {
    List<String> errors = new ArrayList<>();

    try {
      switch (validationType) {
        case XSD:
          validateUsingXsd(validationRequestDto, errors);
          break;
        case RNG:
          validateUsingRng(validationRequestDto, errors);
          break;
        default:
          throw new IllegalArgumentException("Unsupported validation type: " + validationType);
      }
    } catch (Exception ex) {
      errors.add(ex.getMessage());
    }

    return errors;
  }

  private void validateUsingXsd(XmlValidationRequestDto validationRequestDto, List<String> errors) throws SAXException, IOException {
    SchemaFactory schemaFactory = newInstance(W3C_XML_SCHEMA_NS_URI);
    Schema schema = schemaFactory.newSchema(new StreamSource(new StringReader(validationRequestDto.getValidator())));

    Validator validator = schema.newValidator();

    XmlErrorHandler errorHandler = new XmlErrorHandler();
    validator.setErrorHandler(errorHandler);

    validator.validate(new StreamSource(new StringReader(validationRequestDto.getXml())));

    errors.addAll(errorHandler.getErrors());
  }

  private void validateUsingRng(XmlValidationRequestDto validationRequestDto, List<String> errors) throws IOException, SAXException {
    PropertyMapBuilder propertyMapBuilder = new PropertyMapBuilder();
    XmlErrorHandler errorHandler = new XmlErrorHandler();
    propertyMapBuilder.put(ERROR_HANDLER, errorHandler);
    PropertyMap properties = propertyMapBuilder.toPropertyMap();

    ValidationDriver driver = new ValidationDriver(properties);

    InputSource schemaInput = new InputSource(new StringReader(validationRequestDto.getValidator()));
    if (!driver.loadSchema(schemaInput)) {
      errors.add("Failed to load RNG schema");
      return;
    }

    InputSource xmlInput = new InputSource(new StringReader(validationRequestDto.getXml()));
    if (!driver.validate(xmlInput)) {
      errors.addAll(errorHandler.getErrors());
    }
  }
}
