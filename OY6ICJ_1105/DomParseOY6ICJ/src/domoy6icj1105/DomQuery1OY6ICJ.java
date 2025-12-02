package domoy6icj1105;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQuery1OY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		File xmlFile = new File("res/OY6ICJ_orarend.xml");
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document doc = db.parse(xmlFile);
		
		printCourseNames(doc);
		printFirstElement(doc);
		printTeachers(doc);
		getPlaceOfAllLectures(doc);
	}
	
	private static void printCourseNames(Document doc){
		ArrayList<String> courseNames = new ArrayList<String>();
		
		
		NodeList nodeList = doc.getElementsByTagName("ora");
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			
			String courseName = element.getElementsByTagName("targy").item(0).getTextContent();
			
			courseNames.add(courseName);
		}
		
		System.out.println(courseNames);
	}
	
	private static void printFirstElement(Document doc) throws ParserConfigurationException, TransformerException{
		Element element = (Element) doc.getElementsByTagName("ora").item(0);
		
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dmf.newDocumentBuilder();
		
		Document newDoc = db.newDocument();
		
		
		newDoc.appendChild(newDoc.adoptNode(element.cloneNode(true)));
		
		
		DOMSource source = new DOMSource(newDoc);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		
		
		StreamResult consoleResult = new StreamResult(System.out);
		StreamResult file = new StreamResult("res/query.xml");
		transformer.transform(source, consoleResult);
		
		transformer.transform(source, file);
		
	}
	
	private static void printTeachers(Document doc){
		Set<String> teachers = new HashSet<String>();
		
		
		NodeList nodeList = doc.getElementsByTagName("ora");
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			
			String courseName = element.getElementsByTagName("oktato").item(0).getTextContent();
			
			teachers.add(courseName);
		}
		
		System.out.println("\n" + teachers);
	}
	
	private static void getPlaceOfAllLectures(Document doc) {
		ArrayList<String> places = new ArrayList<String>();
		
		NodeList nodeList = doc.getElementsByTagName("ora");
		for(int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element)nodeList.item(i);
			if(element.getAttribute("tipus").equals("eloadas")) {
				places.add(element.getElementsByTagName("helyszin").item(0).getTextContent());
			}
		}
		
		System.out.println(places);
	}
	
}
