package domoy6icj1105;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyOY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		File xmlFile = new File("res/OY6ICJhallgato.xml");
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		Node hallgato = doc.getElementsByTagName("hallgato").item(0);
		
		NamedNodeMap attr = hallgato.getAttributes();
		Node nodeAttr = attr.getNamedItem("id");
		nodeAttr.setTextContent("01");
		
		NodeList list = hallgato.getChildNodes();
		
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				
				if(eElement.getNodeName().equals("keresztnev")) {
					if(eElement.getTextContent().equals("Pál")) {
						eElement.setTextContent("Olivia");
					}
				}
				
				if(eElement.getNodeName().equals("vezeteknev")) {
					if(eElement.getTextContent().equals("Kiss")) {
						eElement.setTextContent("Erős");
					}
				}
			}
		}
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		
		DOMSource source = new DOMSource(doc);
		
		System.out.println("----- Módosított fájl -----");
		StreamResult consoleResult = new StreamResult(System.out);
		transformer.transform(source, consoleResult);
	}
}
