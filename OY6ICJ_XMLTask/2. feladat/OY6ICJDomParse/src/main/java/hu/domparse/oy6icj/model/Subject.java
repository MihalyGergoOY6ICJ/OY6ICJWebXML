package hu.domparse.oy6icj.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.domparse.oy6icj.util.Utils;
import lombok.Data;



/**
 * A tárgy elemnek megfelelő osztály
 */
@Data
public class Subject implements Parsable, Writable{
	final public static String elementName = "targy";
	
	private String tID;
	private String oID;
	
	private String name;
	private int credit;
	private List<Lesson> lessons = new ArrayList<Subject.Lesson>();
	private boolean isRequired;
	
	
	@Override
	public void parse(Element parentElement) throws XMLParseException {
		this.tID = parentElement.getAttribute("tkod");
		this.oID = parentElement.getAttribute("okod");
		NodeList nodeList = parentElement.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)nodeList.item(i);
				String value = element.getTextContent();
				switch(element.getTagName()) {
				case "nev":
					this.name = value;
					break;
				case "kredit":
					this.credit = Integer.valueOf(value);
					break;
				case Lesson.elementName:
					Lesson lesson = new Lesson();
					lesson.parse(element);
					this.lessons.add(lesson);
					break;
				case "kotelezo":
					this.isRequired = (value.equals("Igen"));
					break;
				default:
					throw new XMLParseException(element.getTagName());
				}
			}
		}
	}
	
	@Override
	public void print(Optional<PrintWriter> writer, boolean indent) {
		String[] tagNames = {"tkod", "okod", "nev", "kredit", "kotelezo"};
		String[] values = {this.tID, this.oID, this.name, this.credit + "", (this.isRequired) ? "Igen" : "Nem"};
		Utils.printBlock(elementName, tagNames, values, 1, true, indent, writer);
		
		for(Lesson lesson : this.lessons) {
			lesson.print(writer, true);
		}

	}

	@Override
	public void writeElement(Document doc, Element parentElement) {
		Element subject = doc.createElement(Subject.elementName);
		subject.setAttribute("tkod", this.tID);
		subject.setAttribute("okod", this.oID);
		
		Utils.createTextElement(doc, subject, "nev", this.name);
		Utils.createTextElement(doc, subject, "kredit", this.credit + "");
		for(Lesson lesson : this.lessons) {
			lesson.writeElement(doc, subject);
		}
		Utils.createTextElement(doc, subject, "kotelezo", (this.isRequired) ? "Igen" : "Nem");
		
		parentElement.appendChild(subject);
	}
	
	
	
	
	/**
	 * Az óra elemnek megfelelő osztály.
	 */
	@Data
	private class Lesson implements Parsable, Writable{
		final public static String elementName = "ora";
		
		private LessonType type;
		private Day day;
		private int start;
		
		@Override
		public void parse(Element parentElement) throws XMLParseException {
			NodeList nodeList = parentElement.getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++) {
				if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)nodeList.item(i);
					String value = element.getTextContent();
					switch(element.getTagName()) {
					case "tipus":
						this.type = LessonType.valueOf(value);
						break;
					case "nap":
						this.day = Day.valueOf(value);
						break;
					case "kezdet":
						this.start = Integer.valueOf(value);
						break;
					default:
						throw new XMLParseException(element.getTagName());
					}
				}
			}
		}
		
		@Override
		public void print(Optional<PrintWriter> writer, boolean indent) {
			String[] tagNames = {"tipus", "nap", "kezdet"};
			String[] values = {this.type.name(), this.day.name(), this.start + ""};
			Utils.printBlock(elementName, tagNames, values, 2, false, indent, writer);
		}
		
		@Override
		public void writeElement(Document doc, Element parentElement) {
			Element lesson = doc.createElement(Lesson.elementName);
			Utils.createTextElement(doc, lesson, "tipus", this.type.toString());
			Utils.createTextElement(doc, lesson, "nap", this.day.toString());
			Utils.createTextElement(doc, lesson, "kezdet", this.start + "");
			
			parentElement.appendChild(lesson);
		}
	}
}
