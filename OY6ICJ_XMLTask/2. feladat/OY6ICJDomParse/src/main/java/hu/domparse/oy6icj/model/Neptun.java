package hu.domparse.oy6icj.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.domparse.oy6icj.DomWrite;
import lombok.Data;


/**
 * A neptun elemnek (gyökérelem) megfelelő osztály.
 */
@Data
public class Neptun implements Parsable, RootWritable{
	final public static String elementName = "neptun";
	
	private List<University> universities = new ArrayList<University>();
	private List<Student> students = new ArrayList<Student>();
	private List<Teacher> teachers = new ArrayList<Teacher>();
	private List<Subject> subjects = new ArrayList<Subject>();
	private List<Taking> takings = new ArrayList<Taking>();
	
	@Override
	public void parse(Element parentElement) throws XMLParseException {
		NodeList nodeList = parentElement.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)nodeList.item(i);
				switch(element.getTagName()) {
				case University.elementName:
					University university = new University();
					university.parse(element);
					this.universities.add(university);
					break;
				case Student.elementName:
					Student student = new Student();
					student.parse(element);
					this.students.add(student);
					break;
				case Teacher.elementName:
					Teacher teacher = new Teacher();
					teacher.parse(element);
					this.teachers.add(teacher);
					break;
				case Subject.elementName:
					Subject subject = new Subject();
					subject.parse(element);
					this.subjects.add(subject);
					break;
				case Taking.elementName:
					Taking taking = new Taking();
					taking.parse(element);
					this.takings.add(taking);
					break;
				default:
					throw new XMLParseException(element.getTagName());
				}
			}
		}
	}
	
	@Override
	public void print(Optional<PrintWriter> writer, boolean indent) {
		System.out.println("A gyökérelem: " + elementName);
		if(writer.isPresent()) {
			writer.get().println("A gyökérelem: " + elementName);
		}
		for(University university :this.universities) {
			university.print(writer, true);
		}
		for(Student student : this.students) {
			student.print(writer, true);
		}
		for(Teacher teacher : this.teachers) {
			teacher.print(writer, true);
		}
		for(Subject subject :this.subjects) {
			subject.print(writer, true);
		}
		for(Taking takin : this.takings) {
			takin.print(writer, true);
		}
	}
	
	@Override
	public void write(String name) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		
		Document doc = builder.newDocument();
		
		Element root = doc.createElement("neptun");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:noNamespaceSchemaLocation", "OY6ICJ_XMLSchema.xsd");
		
		this.writeElement(doc, root);
		
		
		doc.appendChild(root);
		
		DomWrite.write(name, doc);
	}
	
	@Override
	public void writeElement(Document doc, Element parentElement) {
		for(University university : this.universities) {
			university.writeElement(doc, parentElement);
		}
		
		for(Student student : this.students) {
			student.writeElement(doc, parentElement);
		}
		
		for(Teacher teacher : this.teachers) {
			teacher.writeElement(doc, parentElement);
		}
		
		for(Subject subject : this.subjects) {
			subject.writeElement(doc, parentElement);
		}
		
		for(Taking taking : this.takings) {
			taking.writeElement(doc, parentElement);
		}
	}
	
	
	/**
	 * Egyetem lekérdezése név szerint.
	 * @param name az egyetem neve
	 * @return ha létezik, akkor az egyetem, egyébként <code>Optional.empty()</code>
	 */
	public Optional<University> getUniversityByName(String name) {
		for(University university : this.universities) {
			if(university.getName().equals(name)) {
				return Optional.ofNullable(university);
			}
		}
		return Optional.empty();
	}
	
	
	/**
	 * Hallgató lekérdezése név szerint.
	 * @param name a hallgató neve
	 * @return ha létezik, akkor a hallgató, egyébként <code>Optional.empty()</code>
	 */
	public Optional<Student> getStudentByName(String name){
		for(Student student : this.students) {
			if(student.getName().equals(name)) {
				return Optional.ofNullable(student);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * Tárgy lekérdezése név szerint.
	 * @param name a tárgy neve
	 * @return ha létezik, akkor a tárgy, egyébként <code>Optional.empty()</code>
	 */
	public Optional<Subject> getSubjectByName(String name){
		for(Subject subject : this.subjects) {
			if(subject.getName().equals(name)) {
				return Optional.ofNullable(subject);
			}
		}
		return Optional.empty();
	}
	
	
	/**
	 * Oktató lekérdezése név szerint.
	 * @param name az oktató neve
	 * @return ha létezik, akkor az oktató, egyébként <code>Optional.empty()</code>
	 */
	public Optional<Teacher> getTeacherByName(String name){
		for(Teacher teacher : this.teachers) {
			if(teacher.getName().equals(name)) {
				return Optional.ofNullable(teacher);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * Hallgatók lekérdezése ekód (egyetem azonosítója) szerint.
	 * @param eID az ekód
	 * @return a talált hallgatók, ha nem talált akkor egy üres lista
	 */
	public List<Student> getStudentsByEid(String eID){
		List<Student> ret = new ArrayList<Student>();
		for(Student student : this.students) {
			if(student.getEID().equals(eID)) {
				ret.add(student);
			}
		}
		return ret;
	}
	
	/**
	 * Oktatók lekérdezése ekód (egyetem azonosítója) szerint.
	 * @param eID az ekód
	 * @return a talált oktatók, ha nem talált, akkor egy üres lista
	 */
	public List<Teacher> getTeachersByEid(String eID){
		List<Teacher> ret = new ArrayList<Teacher>();
		for(Teacher teacher : this.teachers) {
			if(teacher.getEID().equals(eID)) {
				ret.add(teacher);
			}
		}
		return ret;
	}
	
	
	/**
	 * Tárgyak lekérdezése okód (oktató azonosítója) szerint.
	 * @param oID az okód
	 * @return a talált tárgyak, ha nem, talált, akkor egy üres lista
	 */
	public List<Subject> getSubjectsByOid(String oID){
		List<Subject> ret = new ArrayList<Subject>();
		for(Subject subject : this.subjects) {
			if(subject.getOID().equals(oID)) {
				ret.add(subject);
			}
		}
		return ret;
	}
	
	/**
	 * Tárgyak lekérdezése neptun kód (hallgató azonosítója) szerint, újrafelvételre szűrve.
	 * @param neptunKod a neptun kód
	 * @param isRepeat újrafelvétel igaz, vagy hamis legyen
	 * @return a talált tárgyak, ha nem talált, akkor egy üres lista
	 */
	public List<Subject> getSubjectsByNeptunKodAndRepeat(String neptunKod, boolean isRepeat){
		List<Taking> takings = this.takings.stream().filter(taking -> taking.getNeptunKod().equals(neptunKod)).filter(taking -> taking.isRepeat() == isRepeat).collect(Collectors.toList());
		List<Subject> ret = new ArrayList<Subject>();
		for(Taking taking : takings) {
			for(Subject subject : this.subjects) {
				if(subject.getTID().equals(taking.getTID())) {
					ret.add(subject);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Hallgatók lekérdezése tkód (tárgy azonosítója) szerint.
	 * @param tID a tkód
	 * @return a talált hallgatók, ha nem talált, akkor egy üres lista
	 */
	
	public List<Student> getStudentByTid(String tID) {
		List<Taking> takings = this.takings.stream().filter(taking -> taking.getTID().equals(tID)).collect(Collectors.toList());
		List<Student> ret = new ArrayList<Student>();
		for(Taking taking : takings) {
			for(Student student : this.students) {
				if(student.getNeptunKod().equals(taking.getNeptunKod())) {
					ret.add(student);
				}
			}
		}
		return ret;
	}
}
