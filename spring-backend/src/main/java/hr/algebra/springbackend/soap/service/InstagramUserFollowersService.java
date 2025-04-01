package hr.algebra.springbackend.soap.service;

import hr.algebra.springbackend.repository.InstagramUserFollowerRepository;
import hr.algebra.springbackend.service.XmlValidationService;
import hr.algebra.springbackend.soap.exception.SoapClientException;
import hr.algebra.springbackend.soap.model.wsdl.GetUserNodeByUsernameResponse;
import hr.algebra.springbackend.soap.model.xml.User;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

import static hr.algebra.springbackend.model.enums.ValidationType.XSD;
import static hr.algebra.springbackend.service.XmlValidationService.INSTAGRAM_USER_FOLLOWERS_XSD_VALIDATION_FILE;
import static java.util.Objects.requireNonNull;
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
      return findMatchingNodes(xmlDocument, username);
    } catch (Exception e) {
      throw new SoapFaultException("Unable to process latest data", e);
    }
  }

  private Document createXmlDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    return builder.parse(new InputSource(new StringReader(xml)));
  }

  private GetUserNodeByUsernameResponse findMatchingNodes(Document xmlDocument, String username) throws XPathExpressionException {
    XPathFactory xPathFactory = XPathFactory.newInstance();
    XPath xpath = xPathFactory.newXPath();

    XPathExpression expr = xpath.compile("//node[username='" + username + "']");
    NodeList nodes = (NodeList) expr.evaluate(xmlDocument, NODESET);

    if (nodes.getLength() > 0) {
      Node node = nodes.item(0);

      Long idValue = Long.valueOf(requireNonNull(getChildElementText(node, "id")));
      String usernameValue = getChildElementText(node, "username");
      String fullNameValue = getChildElementText(node, "fullName");
      String profilePicUrlValue = getChildElementText(node, "profilePicUrl");
      Boolean isPrivateValue = Boolean.valueOf(getChildElementText(node, "isPrivate"));
      Boolean isVerifiedValue = Boolean.valueOf(getChildElementText(node, "isVerified"));

      return new GetUserNodeByUsernameResponse(
        new User(idValue, usernameValue, fullNameValue, profilePicUrlValue, isPrivateValue, isVerifiedValue)
      );
    } else {
      return new GetUserNodeByUsernameResponse("Username not found");
    }
  }

  private String getChildElementText(Node parent, String childName) {
    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      if (child.getNodeName().equals(childName)) {
        return child.getTextContent();
      }
    }
    return null;
  }
}
