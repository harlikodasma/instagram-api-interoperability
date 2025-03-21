package hr.algebra.springbackend.rest.service;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.validate.ValidationDriver;
import hr.algebra.springbackend.rest.exception.RestClientException;
import hr.algebra.springbackend.rest.handler.XmlErrorHandler;
import hr.algebra.springbackend.rest.model.enums.ValidationType;
import hr.algebra.springbackend.rest.model.jpa.Submission;
import hr.algebra.springbackend.rest.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.thaiopensource.validate.ValidateProperty.ERROR_HANDLER;
import static java.lang.String.format;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class XmlValidationService {

  public static final String INSTAGRAM_USER_FOLLOWERS_XSD_VALIDATION_FILE = "instagramUserFollowers.xsd";
  public static final String INSTAGRAM_USER_FOLLOWERS_RNG_VALIDATION_FILE = "instagramUserFollowers.rng";

  private final SubmissionRepository submissionRepository;
  private final ResourceLoader resourceLoader;

  public void validate(String xml, ValidationType validationType, String validationFile) {
    List<String> errors = getValidationErrors(xml, validationType, validationFile);

    if (isNotEmpty(errors)) {
      throw new RestClientException(errors);
    }

    submissionRepository.save(new Submission(xml, validationType));
  }

  private List<String> getValidationErrors(String xml, ValidationType validationType, String validationFile) {
    List<String> errors = new ArrayList<>();

    try {
      switch (validationType) {
        case XSD:
          validateUsingXsd(xml, errors, validationFile);
          break;
        case RNG:
          validateUsingRng(xml, errors, validationFile);
          break;
        default:
          throw new IllegalArgumentException("Unsupported validation type: " + validationType);
      }
    } catch (Exception ex) {
      errors.add(ex.getMessage());
    }

    return errors;
  }

  private void validateUsingXsd(String xml, List<String> errors, String validationFile) throws SAXException, IOException {
    Resource xsdResource = resourceLoader.getResource(format("classpath:validators/%s", validationFile));
    SchemaFactory schemaFactory = newInstance(W3C_XML_SCHEMA_NS_URI);
    Schema schema = schemaFactory.newSchema(new StreamSource(new InputStreamReader(xsdResource.getInputStream())));

    Validator validator = schema.newValidator();

    XmlErrorHandler errorHandler = new XmlErrorHandler();
    validator.setErrorHandler(errorHandler);

    validator.validate(new StreamSource(new StringReader(xml)));

    errors.addAll(errorHandler.getErrors());
  }

  private void validateUsingRng(String xml, List<String> errors, String validationFile) throws IOException, SAXException {
    PropertyMapBuilder propertyMapBuilder = new PropertyMapBuilder();
    XmlErrorHandler errorHandler = new XmlErrorHandler();
    propertyMapBuilder.put(ERROR_HANDLER, errorHandler);
    PropertyMap properties = propertyMapBuilder.toPropertyMap();

    ValidationDriver driver = new ValidationDriver(properties);

    Resource rngResource = resourceLoader.getResource(format("classpath:validators/%s", validationFile));
    InputSource schemaInput = new InputSource(new InputStreamReader(rngResource.getInputStream()));
    if (!driver.loadSchema(schemaInput)) {
      errors.add("Failed to load RNG schema");
      return;
    }

    InputSource xmlInput = new InputSource(new StringReader(xml));
    if (!driver.validate(xmlInput)) {
      errors.addAll(errorHandler.getErrors());
    }
  }
}
