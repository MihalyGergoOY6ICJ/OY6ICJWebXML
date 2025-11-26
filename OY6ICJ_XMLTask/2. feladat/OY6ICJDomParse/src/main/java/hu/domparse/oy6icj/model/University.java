package hu.domparse.oy6icj.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.domparse.oy6icj.util.Utils;
import lombok.Data;


/**
 * Az egyetem elemnek megfelelő osztály.
 */
@Data
public class University implements Parsable, Writable{
	final public static String elementName = "egyetem";
	
	private String eID;
	
	private String name;
	private Address address = new Address();
	private String webpage;
	private List<String> phoneNumbers = new ArrayList<String>();
	
	private Rektor rektor = new Rektor();
	
	
	@Override
	public void parse(Element parentElement) throws XMLParseException {
		this.eID = parentElement.getAttribute("ekod");
		NodeList nodeList = parentElement.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)nodeList.item(i);
				String value = element.getTextContent();
				switch(element.getTagName()) {
				case "nev":
					this.name = value;
					break;
				case Address.elementName:
					this.address.parse(element);
					break;
				case "weboldal":
					this.webpage = value;
					break;
				case "telefonszam":
					this.phoneNumbers.add(value);
					break;
				case Rektor.elementName:
					this.rektor.parse(element);
					break;
				default:
					throw new XMLParseException(element.getTagName());
				}
			}
		}
	}
	
	@Override
	public void print(Optional<PrintWriter> writer, boolean indent) {
		List<String> tagNames = new ArrayList<String>(Arrays.asList("ekod", "nev", "weboldal"));
		List<String> values = new ArrayList<String>(Arrays.asList(this.eID, this.name, this.webpage));
		
		for(String phoneNumber : this.phoneNumbers) {
			tagNames.add("telefonszam");
			values.add(phoneNumber);
		}
		Utils.printBlock(elementName, tagNames.toArray(new String[0]), values.toArray(new String[0]), 1, true, indent, writer);
		this.address.print(writer, true);
		this.rektor.print(writer, true);
		
	}
	
	@Override
	public void writeElement(Document doc, Element parentElement) {
		Element university = doc.createElement(University.elementName);
		university.setAttribute("ekod", this.eID);
		
		Utils.createTextElement(doc, university, "nev", this.name);
		this.address.writeElement(doc, university);
		Utils.createTextElement(doc, university, "weboldal", this.webpage);
		for(String phoneNumber : this.phoneNumbers) {
			Utils.createTextElement(doc, university, "telefonszam", phoneNumber);
		}
		this.rektor.writeElement(doc, university);
		parentElement.appendChild(university);
	}
	
	
	
	
	/**
	 * A cím elemnek megfelelő osztály
	 */
	@Data
	private class Address implements Parsable, Writable{
		final public static String elementName = "cim";
		
		private String city;
		private String street;
		private int houseNumber;
		
		@Override
		public void parse(Element parentElement) throws XMLParseException {
			NodeList nodeList = parentElement.getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++) {
				if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)nodeList.item(i);
					String value = element.getTextContent();
					switch(element.getTagName()) {
					case "varos":
						this.city = value;
						break;
					case "utca":
						this.street = value;
						break;
					case "hazszam":
						this.houseNumber = Integer.parseInt(value);
						break;
					default:
						throw new XMLParseException(element.getTagName());
					}
				}
			}
		}
		
		@Override
		public void print(Optional<PrintWriter> writer, boolean indent) {
			String[] tagNames = {"varos", "utca", "hazszam"};
			String[] values = {this.city, this.street, this.houseNumber + ""};
			Utils.printBlock(elementName, tagNames, values, 2, false, indent, writer);
		}
		
		@Override
		public void writeElement(Document doc, Element parentElement) {
			Element address = doc.createElement(Address.elementName);
			Utils.createTextElement(doc, address, "varos", this.city);
			Utils.createTextElement(doc, address, "utca", this.street);
			Utils.createTextElement(doc, address, "hazszam", this.houseNumber + "");
			
			parentElement.appendChild(address);
		}
	}
	
	
	/**
	 * A rektor elemnek megfelelő osztály
	 */
	@Data
	public class Rektor implements Parsable, Writable{
		final public static String elementName = "rektor";
		
		private String rID;
		
		private String name;
		private String phoneNumber;
		private String email;
		private int pay;
		
		@Override
		public void parse(Element parentElement) throws XMLParseException {
			this.rID = parentElement.getAttribute("rkod");
			NodeList nodeList = parentElement.getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++) {
				if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)nodeList.item(i);
					String value = element.getTextContent();
					switch(element.getTagName()) {
					case "nev":
						this.name = value;
						break;
					case "telefonszam":
						this.phoneNumber = value;
						break;
					case "email":
						this.email = value;
						break;
					case "fizetes":
						this.pay = Integer.parseInt(value);
						break;
					default:
						throw new XMLParseException(element.getTagName());
					}
				}
			}
		}
		
		@Override
		public void print(Optional<PrintWriter> writer, boolean indent) {
			String[] tagNames = {"rkod", "nev", "telefonszam", "email", "fizetes"};
			String[] values = {this.rID, this.name, this.phoneNumber, this.email, this.pay + ""};
			Utils.printBlock(elementName, tagNames, values, 2, false, indent, writer);
		}
		
		@Override
		public void writeElement(Document doc, Element parentElement) {
			Element rektor = doc.createElement(Rektor.elementName);
			rektor.setAttribute("rkod", this.rID);
			
			Utils.createTextElement(doc, rektor, "nev", this.name);
			Utils.createTextElement(doc, rektor, "telefonszam", this.phoneNumber);
			Utils.createTextElement(doc, rektor, "email", this.email);
			Utils.createTextElement(doc, rektor, "fizetes", this.pay + "");
			
			parentElement.appendChild(rektor);
		}
	}
}