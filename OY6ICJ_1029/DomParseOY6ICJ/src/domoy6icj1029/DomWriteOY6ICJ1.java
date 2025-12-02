package domoy6icj1029;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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

public class DomWriteOY6ICJ1 {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document out = builder.newDocument();
		
		Document in = builder.parse(new File("res/OY6ICJ_orarend.xml"));
		
		Element root = in.getDocumentElement();
		Element newRoot = out.createElement(root.getTagName());
		out.appendChild(newRoot);
		
		
		NodeList nodeList = root.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element ora = (Element)node;
				Element newOra = out.createElement(ora.getTagName());
				newOra.setAttribute("id", ora.getAttribute("id"));
				newOra.setAttribute("tipus", ora.getAttribute("tipus"));
				newRoot.appendChild(newOra);
				
				NodeList oraChilds = ora.getChildNodes();
				for(int j = 0; j < oraChilds.getLength(); j++) {
					Node child = oraChilds.item(j);
					if(child.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element)child;
						Element newChildElement = out.createElement(childElement.getTagName());
						if(childElement.getTagName().equals("idopont")) {
							NodeList idoChilds = childElement.getChildNodes();
							for(int k = 0; k < idoChilds.getLength(); k++) {
								Node idoChild = idoChilds.item(k);
								if(idoChild.getNodeType() == Node.ELEMENT_NODE) {
									Element idoElement = (Element)idoChild;
									Element newIdoElement = out.createElement(idoElement.getTagName());
									newIdoElement.setTextContent(idoElement.getTextContent());
									newChildElement.appendChild(newIdoElement);
								}
							}
						}
						else {
							newChildElement.setTextContent(childElement.getTextContent());
						}
						newOra.appendChild(newChildElement);
					}
				}
			}
			
		}
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		DOMSource source = new DOMSource(out);
		
		StreamResult file = new StreamResult("res/OY6ICJ_orarend1.xml");
		t.transform(source, file);
	}

}
