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
 * A hallgató elemnek megfelelő osztály.
 */
@Data
public class Student implements Parsable, Writable{
	final public static String elementName = "hallgato";
	
	private String neptunKod;
	private String eID;
	
	private String name;
	private DateOfBirth dateOfBirth = new DateOfBirth();
	private String email;
	private int creditsDone;
	
	
	@Override
	public void parse(Element parentElement) throws XMLParseException {
		this.neptunKod = parentElement.getAttribute("neptun_kod");
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
				case "email":
					this.email = value;
					break;
				case "teljesitett_kreditek":
					this.creditsDone = Integer.parseInt(value);
					break;
				case DateOfBirth.elementName:
					this.dateOfBirth.parse(element);
					break;
				default:
					throw new XMLParseException(element.getTagName());
				}
			}
		}	
	}
	
	@Override
	public void print(Optional<PrintWriter> writer, boolean indent) {
		String[] tagNames = {"neptun_kod", "ekod", "nev", "email","teljesitett_kreditek"};
		String[] values = {this.neptunKod, this.eID, this.name, this.email, this.creditsDone + ""}; 
		Utils.printBlock(elementName, tagNames, values, 1, true, indent, writer);
		this.dateOfBirth.print(writer, true);
	}
	
	@Override
	public void writeElement(Document doc, Element parentElement) {
		Element student = doc.createElement(Student.elementName);
		student.setAttribute("neptun_kod", this.neptunKod);
		student.setAttribute("ekod", this.eID);
		
		Utils.createTextElement(doc, student, "nev", this.name);
		this.dateOfBirth.writeElement(doc, student);
		Utils.createTextElement(doc, student, "email", this.email);
		Utils.createTextElement(doc, student, "teljesitett_kreditek", this.creditsDone + "");
		
		parentElement.appendChild(student);
	};
	
	
	/**
	 * A születési dátum elemnek megfelelő osztály.
	 */
	@Data
	private static class DateOfBirth implements Parsable, Writable{
		final public static String elementName = "szuletesi_datum";
		
		private int year;
		private int month;
		private int day;
		
		@Override
		public void parse(Element parentElement) throws XMLParseException {
			NodeList nodeList = parentElement.getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++) {
				if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodeList.item(i);
					int value = Integer.parseInt(element.getTextContent());
					switch(element.getTagName()) {
					case "ev":
						this.year = value;
						break;
					case "honap":
						this.month = value;
						break;
					case "nap":
						this.day = value;
						break;
					default:
						throw new XMLParseException(element.getTagName());
					}
				}
			}
		}
		
		@Override
		public void print(Optional<PrintWriter> writer, boolean indent) {
			String[] tagNames = {"ev", "honap", "nap"};
			String[] values = {this.year + "", this.month + "", this.day + ""};
			Utils.printBlock(elementName, tagNames, values, 2, false, indent, writer);
		}
		
		@Override
		public void writeElement(Document doc, Element parentElement) {
			Element dateOfBirth = doc.createElement(DateOfBirth.elementName);
			
			Utils.createTextElement(doc, dateOfBirth, "ev", this.year + "");
			Utils.createTextElement(doc, dateOfBirth, "honap", this.month + "");
			Utils.createTextElement(doc, dateOfBirth, "nap", this.day + "");
			
			parentElement.appendChild(dateOfBirth);
		}

	}
}
