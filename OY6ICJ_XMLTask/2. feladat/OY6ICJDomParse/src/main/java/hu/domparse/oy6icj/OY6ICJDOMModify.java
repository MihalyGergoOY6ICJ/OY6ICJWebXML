package hu.domparse.oy6icj;

import java.util.List;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import hu.domparse.oy6icj.model.Neptun;
import hu.domparse.oy6icj.model.Student;
import hu.domparse.oy6icj.model.Subject;
import hu.domparse.oy6icj.model.Teacher;
import hu.domparse.oy6icj.model.University;
import hu.domparse.oy6icj.model.University.Rektor;


/**
 * Módosítások a <code>Neptun</code> XML-en
 */
public class OY6ICJDOMModify {
	final private static String printHeader = "\nA módosított elemek:";
	
	/**
	 * Egy oktató email címét módosítja.
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name az oktató neve
	 * @param newEmail az új email cím
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void changeEmailOfTeacher(Neptun neptun, String name, String newEmail) throws ParserConfigurationException, TransformerException {
		final String fileName = "modify1.xml";
		
		
		Optional<Teacher> teacher = neptun.getTeacherByName(name);
		if(!teacher.isPresent()) {
			System.out.printf("\n\n%s nevű oktató nem létezik, nem történt módosítás", name);
			return;
		}
		
		teacher.get().setEmail(newEmail);
		System.out.println(printHeader);
		teacher.get().print(Optional.empty(), false);
		
		neptun.write(fileName);
		System.out.printf("A módosított XML kiírva a(z) %s fájlba\n", "res/" + fileName);
		
		
	}
	
	/**
	 * Új telefonszám hozzáadása egy egyetemhez.
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name az egyetem neve
	 * @param phoneNumber az új telefonszám
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void addPhoneNumberToUniversity(Neptun neptun, String name, String phoneNumber) throws ParserConfigurationException, TransformerException {
		final String fileName = "modify2.xml";
		
		
		Optional<University> university = neptun.getUniversityByName(name);
		if(!university.isPresent()) {
			System.out.printf("\n\n%s nevű egyetem nem létezik, nem történt változás", name);
			return;
		}
		
		List<String> phoneNumbers = university.get().getPhoneNumbers();
		phoneNumbers.add(phoneNumber);
		university.get().setPhoneNumbers(phoneNumbers);
		
		System.out.println(printHeader);
		university.get().print(Optional.empty(), false);
		
		neptun.write(fileName);
		System.out.printf("A módosított XML kiírva a(z) %s fájlba\n", "res/" + fileName);
	}
	
	
	/**
	 * Egy egyetem minden dolgozójának (oktató, rektor) a fizetésének a megemelése.
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name az egyetem neve
	 * @param percent emelés százaléka
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void raisePayOfEveryoneInUniversity(Neptun neptun, String name, int percent) throws ParserConfigurationException, TransformerException {
		final String fileName = "modify3.xml";
		
		Optional<University> university = neptun.getUniversityByName(name);
		if(!university.isPresent()) {
			System.out.printf("\n\n%s nevű egyetem nem létezik, nem történt módosítás\n", name);
			return;
		}
		
		Rektor rektor = university.get().getRektor();
		rektor.setPay((int)(rektor.getPay() * (float)(1 + (percent / 100))));
		List<Teacher> teachers = neptun.getTeachersByEid(university.get().getEID());
		for(Teacher teacher : teachers) {
			teacher.setPay((int)(teacher.getPay() * (float)(1 + (percent / 100))));
		}
		
		System.out.println(printHeader);
		rektor.print(Optional.empty(), false);
		for(Teacher teacher : teachers) {
			teacher.print(Optional.empty(), false);
		}
		
		neptun.write(fileName);
		System.out.printf("A módosított XML kiírva a(z) %s fájlba\n", "res/" + fileName);
	}
	
	/**
	 * Egy tárgy elvégzése (minden hallagtójának a teljesített kreditjéhez hozzáadódik a tárgy kreditje). 
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name a tárgy neve
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void subjectDone(Neptun neptun, String name) throws ParserConfigurationException, TransformerException {
		final String fileName = "modify4.xml";
		
		
		Optional<Subject> subject = neptun.getSubjectByName(name);
		if(!subject.isPresent()) {
			System.out.printf("\n\n%s nevű tárgy nem létezik, nem történt módosítás", name);
			return;
		}
		
		List<Student> students = neptun.getStudentByTid(subject.get().getTID());
		for(Student student : students) {
			student.setCreditsDone(student.getCreditsDone() + subject.get().getCredit());
		}
		
		System.out.println(printHeader);
		for(Student student : students) {
			student.print(Optional.empty(), false);
		}
		
		neptun.write(fileName);
		System.out.printf("A módosított XML kiírva a(z) %s fájlba\n", "res/" + fileName);
	}
}
