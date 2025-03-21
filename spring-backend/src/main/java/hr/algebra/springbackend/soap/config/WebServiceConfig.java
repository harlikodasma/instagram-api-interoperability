package hr.algebra.springbackend.soap.config;

import hr.algebra.springbackend.soap.resolver.SoapExceptionResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  public static final String NAMESPACE_URI = "http://algebra.hr/springbackend/ws";

  @Bean
  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, "/ws/*");
  }

  @Bean(name = "instagramUserFollowers")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema instagramUserFollowersSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("InstagramUserFollowersPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace(NAMESPACE_URI);
    wsdl11Definition.setSchema(instagramUserFollowersSchema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema instagramUserFollowersSchema() {
    return new SimpleXsdSchema(new ClassPathResource("xsd/instagramUserFollowersService.xsd"));
  }

  @Bean
  public EndpointExceptionResolver soapExceptionResolver() {
    SoapExceptionResolver resolver = new SoapExceptionResolver();
    resolver.setOrder(HIGHEST_PRECEDENCE);
    return resolver;
  }
}
