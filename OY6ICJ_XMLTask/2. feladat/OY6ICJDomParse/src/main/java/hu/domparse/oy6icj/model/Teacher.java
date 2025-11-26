package hu.domparse.oy6icj.model;

import java.io.PrintWriter;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.domparse.oy6icj.util.Utils;
import lombok.Data;


/**
 * Az oktató elemnek megfelelő osztály.
 */
@Data
public class Teacher implements Parsable, Writable{
	public final static String elementName = "oktato"; 
	
	private String oID;
	private String eID;
	
	private String name;
	private String office;
	private String email;
	private int pay;
	
	@Override
	public void parse(Element parentElement) throws XMLParseException {
		this.oID = parentElement.getAttribute("okod");
		this.eID = parentElement.getAttribute("ekod");
		NodeList nodeList = parentElement.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)nodeList.item(i);
				String value = element.getTextContent();
				switch (element.getTagName()) {
				case "nev":
					this.name = value;
					break;
				case "iroda":
					this.office = value;
					break;
				case "email":
					this.email = value;
					break;
				case "fizetes":
					this.pay = Integer.valueOf(value);
					break;
				default:
					throw new XMLParseException(element.getTagName());
				}
			}
		}
	}
	
	@Override
	public void print(Optional<PrintWriter> writer, boolean indent) {
		String[] tagNames = {"okod", "ekod", "nev", "iroda", "email", "fizetes"};
		String[] values = {this.oID, this.eID, this.name, this.office, this.email, this.pay + ""};
		Utils.printBlock(elementName, tagNames, values, 1, true, indent, writer);
	}
	
	@Override
	public void writeElement(Document doc, Element parentElement) {
		Element teacher = doc.createElement(Teacher.elementName);
		teacher.setAttribute("okod", this.oID);
		teacher.setAttribute("ekod", this.eID);
		
		Utils.createTextElement(doc, teacher, "nev", this.name);
		Utils.createTextElement(doc, teacher, "iroda", this.office);
		Utils.createTextElement(doc, teacher, "email", this.email);
		Utils.createTextElement(doc, teacher, "fizetes", this.pay + "");
		
		parentElement.appendChild(teacher);
	}
}
