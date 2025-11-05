package domoy6icj1029;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadOY6ICJ1 {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File("res/OY6ICJ_orarend.xml");
		
		
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
		
		NodeList nodeList = doc.getElementsByTagName("ora");
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			System.out.println("\n" + "Az aktuális elem: " + node.getNodeName());
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element ora = (Element) node;
				String id = ora.getAttribute("id");
				String tipus = ora.getAttribute("tipus");
				
				
				String targy = ora.getElementsByTagName("targy").item(0).getTextContent();
				
				Element idopont = (Element) ora.getElementsByTagName("idopont").item(0);
				
				String nap = idopont.getElementsByTagName("nap").item(0).getTextContent();
				String tol = idopont.getElementsByTagName("tol").item(0).getTextContent();
				String ig = idopont.getElementsByTagName("ig").item(0).getTextContent(); 
				
				String helyszin = ora.getElementsByTagName("helyszin").item(0).getTextContent();
				
				String oktato = ora.getElementsByTagName("oktato").item(0).getTextContent();
				
				String szak = ora.getElementsByTagName("szak").item(0).getTextContent();
				
				
				System.out.println("Ora id: " + id);
				System.out.println("Ora tipus: " + tipus);
				System.out.println("Targy nev: " + targy);
				System.out.println("Nap: " + nap);
				System.out.println("Tol: " + tol);
				System.out.println("Ig: " + ig);
				System.out.println("Helyszin: " + helyszin);
				System.out.println("Oktato: " + oktato);
				System.out.println("Szak: " + szak);
				
			}
		}
		
	}
}
