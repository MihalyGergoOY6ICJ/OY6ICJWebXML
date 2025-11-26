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
 * A felvett elemnek megfelelő osztály.
 */
@Data
public class Taking implements Parsable, Writable{
	final public static String elementName = "felvett";
	
	private String neptunKod;
	private String tID;
	
	private boolean isSubsequent;
	private boolean isRepeat;
	
	
	@Override
	public void parse(Element parentElement) throws XMLParseException {
		this.neptunKod = parentElement.getAttribute("neptun_kod");
		this.tID = parentElement.getAttribute("tkod");
		NodeList nodeList = parentElement.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)nodeList.item(i);
				String value = element.getTextContent();
				switch (element.getTagName()) {
				case "utolagos":
					this.isSubsequent = (value.equals("Igen"));
					break;
				case "ujrafelvetel":
					this.isRepeat = (value.equals("Igen"));
					break;
				default:
					throw new XMLParseException(element.getTagName());
				}
			}
		}
	}

	@Override
	public void print(Optional<PrintWriter> writer, boolean indent) {
		String[] tagNames = {"neptun_kod", "tkod", "utolagos", "ujrafelvett"};
		String[] values = {this.neptunKod, this.tID, (this.isSubsequent) ? "Igen" : "Nem", (this.isRepeat) ? "Igen" : "Nem"};
		Utils.printBlock(elementName, tagNames, values, 1, true, indent, writer);
	}
	
	@Override
	public void writeElement(Document doc, Element parentElement) {
		Element taking = doc.createElement(Taking.elementName);
		taking.setAttribute("neptun_kod", this.neptunKod);
		taking.setAttribute("tkod", this.tID);
		
		Utils.createTextElement(doc, taking, "utolagos", (this.isSubsequent) ? "Igen" : "Nem");
		Utils.createTextElement(doc, taking, "ujrafelvetel", (this.isRepeat) ? "Igen" : "Nem");

		parentElement.appendChild(taking);
	}
}
