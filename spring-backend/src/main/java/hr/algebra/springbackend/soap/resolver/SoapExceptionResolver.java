package hr.algebra.springbackend.soap.resolver;

import hr.algebra.springbackend.soap.exception.SoapClientException;
import hr.algebra.springbackend.soap.model.errors.ErrorList;
import hr.algebra.springbackend.soap.model.errors.ServiceFault;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultException;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import java.util.List;
import java.util.Properties;

import static jakarta.xml.bind.Marshaller.JAXB_FRAGMENT;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class SoapExceptionResolver extends SoapFaultMappingExceptionResolver {

  public SoapExceptionResolver() {
    Properties properties = new Properties();
    properties.setProperty(SoapClientException.class.getName(), "CLIENT");
    properties.setProperty(Exception.class.getName(), "SERVER");
    setExceptionMappings(properties);
  }

  @Override
  protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
    ServiceFault serviceFault;
    if (ex instanceof SoapClientException soapEx) {
      serviceFault = new ServiceFault(BAD_REQUEST.value(), new ErrorList(soapEx.getErrors()));
    } else {
      serviceFault = new ServiceFault(INTERNAL_SERVER_ERROR.value(), new ErrorList(List.of(ex.getMessage())));
    }

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(ServiceFault.class);
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(JAXB_FRAGMENT, TRUE);

      marshaller.marshal(serviceFault, fault.addFaultDetail().getResult());
    } catch (Exception e) {
      throw new SoapFaultException("Failed to add fault detail", e);
    }
  }
}
