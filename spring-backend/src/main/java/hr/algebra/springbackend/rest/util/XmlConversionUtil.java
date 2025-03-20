package hr.algebra.springbackend.rest.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

import static jakarta.xml.bind.JAXBContext.newInstance;
import static jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

@Component
public class XmlConversionUtil {

  public <T> String convertToXml(T object, Class<T> clazz) {
    try {
      JAXBContext context = newInstance(clazz);

      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);

      StringWriter stringWriter = new StringWriter();
      marshaller.marshal(object, stringWriter);

      return stringWriter.toString();
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to convert object to XML", e);
    }
  }
}
