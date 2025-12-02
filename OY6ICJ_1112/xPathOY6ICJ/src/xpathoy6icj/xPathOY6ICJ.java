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



public class xPathOY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		File xmlFile = new File("res/studentOY6ICJ.xml");
		
		DocumentBuilderFactory docf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docf.newDocumentBuilder();
		Document doc = docBuilder.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		
		/*----------------------------------------------*/
		/*				   Lekérdezések 		  		*/
		/*----------------------------------------------*/
		//String expr = "class/student";
		//String expr = "class/student[@id=02]";
		//String expr = "class//student";
		//String expr = "class/student[2]";
		//String expr = "class/student[last()]";
		//String expr = "class/student[last() - 1]";
		//String expr = "class/student[position() <= 2]";
		//String expr = "class/student";
		//String expr = "class/student[count(@*) >= 1]";
		//String expr = "class/student";
		//String expr = "class/student[kor >= 20]";
		String expr = "class/student/keresztnev|class/student/vezeteknev";
		
		/*----------------------------------------------*/
		NodeList nodeList = (NodeList) xPath.compile(expr).evaluate(doc, XPathConstants.NODESET);
		
		for(int i = 0; i < nodeList.getLength(); i++) 
		{
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) 
			{
				Element element = (Element) node;
				
				Student student = new Student(
						element.getAttribute("id"),
						element.getElementsByTagName("keresztnev").item(0).getTextContent(),
						element.getElementsByTagName("vezeteknev").item(0).getTextContent(),
						element.getElementsByTagName("becenev").item(0).getTextContent(),
						element.getElementsByTagName("kor").item(0).getTextContent()
				);
				
				Student.Print(student);
			}
		}
	}
}