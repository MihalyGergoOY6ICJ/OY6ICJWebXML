package domoy6icj1105;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryOY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File("res/OY6ICJhallgato.xml");
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
		System.out.println("----------------------------");
		
		NodeList nodeList = doc.getElementsByTagName("hallgato");
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			System.out.println("Aktuális elem: " + node.getNodeName());
			
			Element element = (Element) node;
			
			
			String vNev = element.getElementsByTagName("vezeteknev").item(0).getTextContent();
			
			System.out.println("Vezeteknev: " + vNev);
		}

	}
}
