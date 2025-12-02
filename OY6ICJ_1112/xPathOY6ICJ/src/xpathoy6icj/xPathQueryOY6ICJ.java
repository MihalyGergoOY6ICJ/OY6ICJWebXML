package xpathoy6icj;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathQueryOY6ICJ {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerException {
File xmlFile = new File("res/OY6ICJ_orarend.xml");
		
		DocumentBuilderFactory docf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docf.newDocumentBuilder();
		Document doc = docBuilder.parse(xmlFile);
		
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		getTargyOfOraWithID1(doc, xPath);
		getNamesOfTeachers(doc, xPath);
		getPlaceOfLectures(doc, xPath);
		
		changeTargyOfOraWithID1(doc, xPath);
		changeIdopontOf2ndOra(doc, xPath);
		changeSzakOfLastOra(doc, xPath);
		
		DOMSource source = new DOMSource(doc);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		
		t.transform(source, new StreamResult("res/OY6ICJ_orarend1.xml"));
	}
	
	private static void getTargyOfOraWithID1(Document doc, XPath xpath) throws XPathExpressionException {
		NodeList nodeList = (NodeList) xpath.compile("oy6icj_orarend/ora[@id=1]").evaluate(doc, XPathConstants.NODESET);
		Element ora = (Element)nodeList.item(0);
		System.out.println(ora.getElementsByTagName("targy").item(0).getTextContent());
	}
	private static void getNamesOfTeachers(Document doc, XPath xpath) throws XPathExpressionException {
		HashSet<String> teachers = new HashSet<String>();
		
		NodeList nodeList = (NodeList) xpath.compile("oy6icj_orarend/ora/oktato").evaluate(doc, XPathConstants.NODESET);
		for(int i = 0; i < nodeList.getLength(); i++) {
			teachers.add(nodeList.item(i).getTextContent());
		}
		System.out.println(teachers);
	}
	private static void getPlaceOfLectures(Document doc, XPath xpath) throws XPathExpressionException {
		NodeList nodeList = (NodeList) xpath.compile("oy6icj_orarend/ora[@tipus=\"eloadas\"]/helyszin").evaluate(doc, XPathConstants.NODESET);
		System.out.println(nodeList.getLength());
		for(int i = 0; i < nodeList.getLength(); i++) {
			System.out.println(nodeList.item(i).getTextContent());
		}
	}
	
	private static void changeTargyOfOraWithID1(Document doc, XPath xpath) throws XPathExpressionException {
		NodeList nodeList = (NodeList) xpath.compile("oy6icj_orarend/ora[@id=1]/targy").evaluate(doc, XPathConstants.NODESET);
		nodeList.item(0).setTextContent("Új tárgy");
	}
	
	private static void changeIdopontOf2ndOra(Document doc, XPath xpath) throws XPathExpressionException {
		NodeList nodeList = (NodeList) xpath.compile("oy6icj_orarend/ora[2]/idopont/ig").evaluate(doc, XPathConstants.NODESET);
		nodeList.item(0).setTextContent("20");
	}
	
	private static void changeSzakOfLastOra(Document doc, XPath xpath) throws XPathExpressionException {
		NodeList nodeList = (NodeList) xpath.compile("oy6icj_orarend/ora[last()]/szak").evaluate(doc, XPathConstants.NODESET);
		nodeList.item(0).setTextContent("Új szak");
		
	}
}
