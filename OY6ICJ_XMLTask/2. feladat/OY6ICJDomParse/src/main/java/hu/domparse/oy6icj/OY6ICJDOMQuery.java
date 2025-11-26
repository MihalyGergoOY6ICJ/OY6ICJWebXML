package hu.domparse.oy6icj;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hu.domparse.oy6icj.model.Neptun;
import hu.domparse.oy6icj.model.Student;
import hu.domparse.oy6icj.model.Subject;
import hu.domparse.oy6icj.model.Teacher;
import hu.domparse.oy6icj.model.University;
import hu.domparse.oy6icj.model.University.Rektor;
import hu.domparse.oy6icj.util.Utils;



/**
 * Lekérdezések a <code>Neptun</code> XML-en
 */

public class OY6ICJDOMQuery {
	
	/**
	 * Egy egyetem minden hallgatója.
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name az egyetem neve
	 * @param writer opcionális <code>PrintWriter</code>, ha nincs a konzolra, ha van akkor fájlba is írja az eredményt 
	 */
	public static void allStudentsOfUniversity(Neptun neptun, String name, Optional<PrintWriter> writer){
		String queryDesc = "A " + name + " hallgatói:";
		Optional<University> university = neptun.getUniversityByName(name);
		if(!university.isPresent()) {
			Utils.printQueryResult(queryDesc, writer, new ArrayList<String>());
			return;
		}
		List<Student> students = neptun.getStudentsByEid(university.get().getEID());
		List<String> queryResult = students.stream().map(Student::getName).collect(Collectors.toList());
		Utils.printQueryResult(queryDesc, writer, queryResult);
	}
	
	
	/**
	 * Egy egyetem minden tárgya és a kredit értékük.
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name az egyetem neve
	 * @param writer opcionális <code>PrintWriter</code>, ha nincs a konzolra, ha van akkor fájlba is írja az eredményt
	 */
	public static void allSubjectsAndCreditsOfUniversity(Neptun neptun, String name, Optional<PrintWriter> writer) {
		String queryDesc = "A " + name + " tárgyai és kredit számuk:";
		Optional<University> university = neptun.getUniversityByName(name);
		if(!university.isPresent()) {
			Utils.printQueryResult(queryDesc, writer, new ArrayList<String>());
			return;
		}
		List<Teacher> teachers = neptun.getTeachersByEid(university.get().getEID());
		List<Subject> subjects = new ArrayList<Subject>();
		for(Teacher teacher : teachers) {
			subjects.addAll(neptun.getSubjectsByOid(teacher.getOID()));
		}
		List<String> queryResult1 = subjects.stream().map(Subject::getName).collect(Collectors.toList());
		List<String> queryResult2 = subjects.stream().map(Subject::getCredit).map(number -> number + "").collect(Collectors.toList());
		Utils.printQueryResult(queryDesc, writer, queryResult1, queryResult2);
	}
	
	
	/**
	 * Egy egyetem minden dolgozója és a beosztásuk, fizetésre szűrve.
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name az egyetem neve
	 * @param payThreshold a szűrés határa 
	 * @param writer opcionális <code>PrintWriter</code>, ha nincs a konzolra, ha van akkor fájlba is írja az eredményt
	 */
	public static void allWorkersAndOccupationOfUniversityAboveAPay(Neptun neptun, String name, int payThreshold, Optional<PrintWriter> writer) {
		String queryDesc = "A " + name + " dolgozói, beosztásuk, fizetésük akiknek fizetése " + payThreshold + " felett van:";
		Optional<University> university = neptun.getUniversityByName(name);
		if(!university.isPresent()) {
			Utils.printQueryResult(queryDesc, writer, new ArrayList<String>());
			return;
		}
		Rektor rektor = university.get().getRektor();
		List<Teacher> teachers = neptun.getTeachersByEid(university.get().getEID());
		teachers = teachers.stream().filter(teacher -> teacher.getPay() > payThreshold).collect(Collectors.toList());
		List<String> queryResult1 = teachers.stream().map(Teacher::getName).collect(Collectors.toList());
		List<String> queryResult2 = teachers.stream().map(teacher -> Teacher.elementName).collect(Collectors.toList());
		List<String> queryResult3 = teachers.stream().map(Teacher::getPay).map(number -> number + "").collect(Collectors.toList());
		if(rektor.getPay() > payThreshold) {
			queryResult1.add(rektor.getName());
			queryResult2.add(Rektor.elementName);
			queryResult3.add(rektor.getPay() + "");
		}
		
		Utils.printQueryResult(queryDesc, writer, queryResult1, queryResult2, queryResult3);
	}
	
	
	/**
	 * Egy hallgató tárgyai és hogy kötelezőek-e, újrafelvettre szűrve.
	 * @param neptun a <code>Neptun</code> gyökér elem
	 * @param name a hallgató neve
	 * @param writer opcionális <code>PrintWriter</code>, ha nincs a konzolra, ha van akkor fájlba is írja az eredményt
	 */
	public static void allSubjectsAndIfRequiredByStudentAndIfRepeat(Neptun neptun, String name, Optional<PrintWriter> writer) {
		String queryDesc = "A " + name + " hallgató tárgyai amik újrafelvettek és hogy kötelezők-e";
		Optional<Student> student = neptun.getStudentByName(name);
		if(!student.isPresent()) {
			Utils.printQueryResult(queryDesc, writer, new ArrayList<String>());
			return;
		}
		List<Subject> subjects = neptun.getSubjectsByNeptunKodAndRepeat(student.get().getNeptunKod(), true);
		List<String> queryResult1 = subjects.stream().map(Subject::getName).collect(Collectors.toList());
		List<String> queryResult2 = subjects.stream().map(Subject::isRequired).map(required -> ((required) ? "Igen" : "Nem")).collect(Collectors.toList());
		Utils.printQueryResult(queryDesc, writer, queryResult1, queryResult2);
 	}
}
