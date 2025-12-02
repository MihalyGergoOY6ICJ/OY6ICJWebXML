package domoy6icj1029;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomWriteOY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document out = builder.newDocument();
		
		Document hallgatok = builder.parse(new File("res/OY6ICJhallgato.xml"));
		
		Element root = hallgatok.getDocumentElement();
		Element newRoot = out.createElement(root.getTagName());
		out.appendChild(newRoot);
		
		
		NodeList nodeList = root.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element hallgato = (Element)node;
				Element newHallgato = out.createElement(hallgato.getTagName());
				newHallgato.setAttribute("id", hallgato.getAttribute("id"));
				newRoot.appendChild(newHallgato);
				NodeList hallgatoChilds = hallgato.getChildNodes();
				for(int j = 0; j < hallgatoChilds.getLength(); j++) {
					Node child = hallgatoChilds.item(j);
					if(child.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element)child;
						Element newChildElement = out.createElement(childElement.getTagName());
						newChildElement.setTextContent(childElement.getTextContent());
						newHallgato.appendChild(newChildElement);
					}
				}
			}
			
		}
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		DOMSource source = new DOMSource(out);
		
		StreamResult file = new StreamResult("res/OY6ICJhallgato1.xml");
		t.transform(source, file);
	}
}
