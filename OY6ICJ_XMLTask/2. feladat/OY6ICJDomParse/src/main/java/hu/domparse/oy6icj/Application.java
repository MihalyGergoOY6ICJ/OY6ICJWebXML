package hu.domparse.oy6icj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import hu.domparse.oy6icj.model.Neptun;
import hu.domparse.oy6icj.model.XMLParseException;

public class Application {
	final private static String menu =
			 "---------------------------------------------------\n"
			+ "Egyetem nyilvántartó\n"
			+ "(Válasszon a menüpontok közül)\n"
			+ "0. Kilépés\n"
			+ "1. XML kiírása\n"
			+ "2. Lekérdezések\n"
			+ "3. Módosítások\n"
			+ "---------------------------------------------------";
	
	final private static String printMenu = 
			  "---------------------------------------------------\n"
			+ "Kiírás\n"
			+ "(Válasszon a menüpontok közül)\n"
			+ "1. Konzolra\n"
			+ "2. Konzolra és fájlba\n"
			+ "---------------------------------------------------";
	
	final private static String queryMenu = 
			"---------------------------------------------------\n"
			+ "Lekérdezés\n"
			+ "(Válasszon a menüpontok közül)\n"
			+ "1. Egy egyetem összes hallgatója\n"
			+ "2. Egy egyetem összes tárgy és a kredit értékük\n"
			+ "3. Egy egyetem dolgozói, a beosztásuk és fizetésük, akiknek egy határ felett van a fizetése\n"
			+ "4. Egy hallgató újrafelvett tárgyai és hogy kötelezőek-e\n"
			+ "---------------------------------------------------";
	
	final private static String modifyMenu = 
			  "---------------------------------------------------\n"
			+ "Módosítások\n"
			+ "(Válasszon a menüpontok közül)\n"
			+ "1. Egy oktató emailjének megváltoztatása\n"
			+ "2. Egy egyetemhez egy telefonszám hozzáadása\n"
			+ "3. Egy egyetemen minden dolgozó fizetésének megemelése x százalékkal\n"
			+ "4. Egy tárgy teljesítése (a tárgy kreditjét hozzáadja a tárgyat hallgatók teljesített kreditjeihez)\n"
			+ "---------------------------------------------------";;
	
	private static Neptun neptun;
	private static Scanner scanner;
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, ParserConfigurationException, SAXException, XMLParseException, TransformerException {
		neptun = (Neptun)OY6ICJDOMRead.read("res/OY6ICJ_XML.xml", Neptun.class);
		scanner = new Scanner(System.in);
		
		
		boolean quit = false;
		while(!quit) {
			System.out.println(menu);
			
			char c = scanner.next().charAt(0);
			switch(c) {
			case '0':
				quit = true;
				break;
			case '1':
				print();
				break;
			case '2':
				query();
				break;
			case '3':
				modify();
				break;
			default:
				System.out.println("Helytelen menüpont, válasszon egy helyeset");
			}
		}
	}
	
	
	/**
	 * A kiírási menü kezelője.
	 * @throws IOException fájl írási hiba esetén
	 */
	private static void print() throws IOException {
		System.out.println(printMenu);
		
		char c = scanner.next().charAt(0);
		switch(c) {
		case '1':
			neptun.print(Optional.empty(), true);
			break;
		case '2':
			File file = new File("res/out.txt");
			file.delete();
			try(FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter writer = new PrintWriter(bw)){
				
				neptun.print(Optional.ofNullable(writer), true);
			}
			System.out.println("Az eredmény kiírva a(z) res/out.txt fájlba");
			break;
		default:
			System.out.println("Helytelen menüpont, válasszon egy helyeset");
		}
	}
	
	
	/**
	 * A lekérdezési menü kezelője.
	 * @throws IOException fájl írási hiba esetén
	 */
	private static void query() throws IOException {
		File qfile = new File("res/queryResults.txt");
		qfile.delete();
		
		
		System.out.println(queryMenu);
		
		try(FileWriter fw = new FileWriter(qfile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter writer = new PrintWriter(bw)){
		
			char c = scanner.next().charAt(0);
			switch(c) {
			case '1':
				OY6ICJDOMQuery.allStudentsOfUniversity(neptun, "Miskolci Egyetem", Optional.ofNullable(writer));
				System.out.println("\nAz eredmény kiírva a(z) res/queryResults.txt fájlba");
				break;
			case '2':
				OY6ICJDOMQuery.allSubjectsAndCreditsOfUniversity(neptun, "Budapesti Műszaki és Gazdaságtudományi Egyetem", Optional.ofNullable(writer));
				System.out.println("\nAz eredmény kiírva a(z) res/queryResults.txt fájlba");
				break;
			case '3':
				OY6ICJDOMQuery.allWorkersAndOccupationOfUniversityAboveAPay(neptun, "Budapesti Műszaki és Gazdaságtudományi Egyetem", 10000, Optional.ofNullable(writer));
				System.out.println("\nAz eredmény kiírva a(z) res/queryResults.txt fájlba");
				break;
			case '4':
				OY6ICJDOMQuery.allSubjectsAndIfRequiredByStudentAndIfRepeat(neptun, "Labanc Larissza", Optional.ofNullable(writer));
				System.out.println("\nAz eredmény kiírva a(z) res/queryResults.txt fájlba");
				break;
			default:
				System.out.println("Helytelen menüpont, válasszon egy helyeset");
			}
		}
	}
	
	
	/**
	 * A módosítás menü kezelője
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private static void modify() throws ParserConfigurationException, TransformerException {
		System.out.println(modifyMenu);
		
		char c = scanner.next().charAt(0);
		switch(c) {
		case '1':
			OY6ICJDOMModify.changeEmailOfTeacher(neptun, "Illés Isméria", "new.email@gmail.com");
			break;
		case '2':
			OY6ICJDOMModify.addPhoneNumberToUniversity(neptun, "Szegedi Tudományegyetem", "06309998877");
			break;
		case '3':
			OY6ICJDOMModify.raisePayOfEveryoneInUniversity(neptun, "Miskolci Egyetem", 100);
			break;
		case '4':
			OY6ICJDOMModify.subjectDone(neptun, "Haladó falnézés");
			break;
		default:
			System.out.println("Helytelen menüpont, válasszon egy helyeset");
		}
	}
}
