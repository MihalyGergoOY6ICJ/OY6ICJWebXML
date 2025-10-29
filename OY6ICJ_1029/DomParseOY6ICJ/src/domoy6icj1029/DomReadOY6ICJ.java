package domoy6icj1029;

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

public class DomReadOY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File("res/OY6ICJhallgato.xml");
		
		
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
		
		NodeList nodeList = doc.getElementsByTagName("hallgato");
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			System.out.println("\n" + "Az aktuális elem: " + node.getNodeName());
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				
				String id = element.getAttribute("id");
				
				String kNev = element.getElementsByTagName("keresztnev").item(0).getTextContent();
				
				String vNev = element.getElementsByTagName("vezeteknev").item(0).getTextContent();
				
				String foglalkozas = element.getElementsByTagName("foglalkozas").item(0).getTextContent();
				
				System.out.println("Hallgato id: " + id);
				System.out.println("Keresztnev: " + kNev);
				System.out.println("Vezeteknev: " + vNev);
				System.out.println("Foglalkozas: " + foglalkozas);
			}
		}
		
		
	}
}
