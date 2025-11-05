package domoy6icj1105;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQuery1OY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File("res/OY6ICJ_orarend.xml");
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		printCourseNames(doc);
	}
	
	private static void printCourseNames(Document doc){
		HashSet<String> courseNames = new HashSet<String>();
		
		
		NodeList nodeList = doc.getElementsByTagName("ora");
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			
			String courseName = element.getElementsByTagName("targy").item(0).getTextContent();
			
			courseNames.add(courseName);
		}
		
		System.out.println(courseNames);
	}
	
	private static void printFirstElement(Document doc) {
		Element element = (Element) doc.getElementsByTagName("ora").item(0);
		
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document newDoc = db.newDocument();
		
		newDoc.appendChild(element);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		
		
		StreamResult consoleResult = new StreamResult(System.out);
		transformer.transform(newDoc, consoleResult);
		
		FileWriter writer = new FileWriter("res/orarendModify1OY6ICJ.xml");
		StreamResult fileResult = new StreamResult(writer);
		transformer.transform(source, fileResult);
		writer.close();
		
	} 
}
