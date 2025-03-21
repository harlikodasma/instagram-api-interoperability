package hr.algebra.springbackend.soap.service;

import hr.algebra.springbackend.repository.InstagramUserFollowerRepository;
import hr.algebra.springbackend.service.XmlValidationService;
import hr.algebra.springbackend.soap.exception.SoapClientException;
import hr.algebra.springbackend.soap.model.wsdl.GetUserNodeByUsernameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.SoapFaultException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static hr.algebra.springbackend.model.enums.ValidationType.XSD;
import static hr.algebra.springbackend.service.XmlValidationService.INSTAGRAM_USER_FOLLOWERS_XSD_VALIDATION_FILE;
import static javax.xml.xpath.XPathConstants.NODESET;

@Service
@RequiredArgsConstructor
public class InstagramUserFollowersService {

  private final XmlValidationService xmlValidationService;
  private final InstagramUserFollowerRepository instagramUserFollowerRepository;

  public GetUserNodeByUsernameResponse getUserNodeByUsername(String username) {
    String latestXml = instagramUserFollowerRepository.getLatest().getXml();
    xmlValidationService.validate(latestXml, XSD, INSTAGRAM_USER_FOLLOWERS_XSD_VALIDATION_FILE, SoapClientException::new);

    try {
      Document xmlDocument = createXmlDocument(latestXml);
      String responseXml = findMatchingNodes(xmlDocument, username);

      return new GetUserNodeByUsernameResponse(responseXml);
    } catch (Exception e) {
      throw new SoapFaultException("Unable to process latest data", e);
    }
  }

  private Document createXmlDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    return builder.parse(new InputSource(new StringReader(xml)));
  }

  private String findMatchingNodes(Document xmlDocument, String username) throws XPathExpressionException, TransformerException {
    XPathFactory xPathFactory = XPathFactory.newInstance();
    XPath xpath = xPathFactory.newXPath();

    XPathExpression expr = xpath.compile("//node[username='" + username + "']");
    NodeList nodes = (NodeList) expr.evaluate(xmlDocument, NODESET);

    String nodeXml;
    if (nodes.getLength() > 0) {
      Node node = nodes.item(0);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(node), new StreamResult(writer));
      nodeXml = writer.toString();
    } else {
      nodeXml = "<node>Username not found</node>";
    }

    return nodeXml;
  }
}
