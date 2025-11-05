package domoy6icj1105;

import java.io.File;
import java.io.FileWriter;
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

public class DomModifyOY6ICJ1 {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		
		Modify1();
		
		Modify2();
	}
	
	private static void Modify1() throws IOException, TransformerException, ParserConfigurationException, SAXException  {
		File xmlFile = new File("res/OY6ICJ_orarend.xml");
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		
		
		Node ora = doc.getElementsByTagName("ora").item(0);
		
		Element newElement = doc.createElement("oraado");
		newElement.setTextContent("János Pista");
		ora.appendChild(newElement);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		
		DOMSource source = new DOMSource(doc);
		
		System.out.println("----- Módosított fájl -----");
		StreamResult consoleResult = new StreamResult(System.out);
		transformer.transform(source, consoleResult);
		
		FileWriter writer = new FileWriter("res/orarendModify1OY6ICJ.xml");
		StreamResult fileResult = new StreamResult(writer);
		transformer.transform(source, fileResult);
		writer.close();
	}
	
	private static void Modify2() throws TransformerException, ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File("res/OY6ICJ_orarend.xml");
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		NodeList list = doc.getElementsByTagName("ora");
		for(int i = 0; i < list.getLength(); i++) {
			Node ora = list.item(i);
			
			NamedNodeMap attr = ora.getAttributes();
			Node nodeAttr = attr.getNamedItem("tipus");
			if(nodeAttr.getTextContent().equals("eloadas")) {
				nodeAttr.setTextContent("gyakorlat");
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
