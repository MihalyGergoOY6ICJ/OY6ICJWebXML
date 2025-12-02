package xpathoy6icj;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathModifyOY6ICJ {
	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File("res/studentOY6ICJ.xml");
		
		DocumentBuilderFactory docf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docf.newDocumentBuilder();
		Document doc = docBuilder.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		
		
		NodeList xpath = (NodeList) xPath.compile("class/student[@id=1]").evaluate(doc, XPathConstants.NODESET);
		Node student = xpath.item(0);
		NodeList studentList = student.getChildNodes();
		
		
		for(int i = 0; i < studentList.getLength(); i++) {
			if(studentList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)studentList.item(i);
				if(element.getTagName().equals("keresztnev")) {
					element.setTextContent("Béla");
				}
				
				System.out.println(element.getTagName() + ": " + element.getTextContent());

			}
		}
		
		
	}
}
