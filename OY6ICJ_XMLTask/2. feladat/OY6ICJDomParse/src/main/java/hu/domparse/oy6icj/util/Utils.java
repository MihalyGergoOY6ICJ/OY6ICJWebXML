package hu.domparse.oy6icj.util;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Segédfüggvényeket tartalmaz.
 */
public class Utils {
	
	/**
	 * Egy elem adatait blokk formában kiírja a konzolra, opcionálisan egy fájlba, állítható behúzással, sortöréssel. 
	 * @param elementName a kiírandó elem neve
	 * @param tagNames a kiírandó elem gyerekeinek a neve (csak amiknek szöveges tartalma van)
	 * @param values a kiírandó elem gyerekeinek szöveges tartalma (ha van)
	 * @param indentCount a behúzás mértéke 
	 * @param emptyLineBefore sortöréssel kezdődjön-e a blokk
	 * @param isIndented kell-e behúzás
	 * @param writer opcionális <code>PrintWriter</code>, ha nincs a konzolra, ha van akkor fájlba is ír
	 */
	public static void printBlock(String elementName, String[] tagNames, String[] values, int indentCount, boolean emptyLineBefore, boolean isIndented, Optional<PrintWriter> writer) {
		String tab = "\t";
		String indent = "";
		String headerIndent = "";
		if(isIndented) {
			for(int i = 0; i < indentCount; i++) {
				indent += tab;
				if(i != 0) {
					headerIndent += tab;
				}
			}
			
		}
		else {
			indent = tab;
		}
		if(emptyLineBefore) {
			System.out.println("");
			if(writer.isPresent()) {
				writer.get().println("");
			}
		}
		
		System.out.printf(headerIndent + "Az aktuális elem: %s\n", elementName);
		if(writer.isPresent()) {
			writer.get().printf(headerIndent + "Az aktuális elem: %s\n", elementName);
		}
		for(int i = 0; i < tagNames.length; i++) {
			System.out.printf(indent + tagNames[i] + ": %s\n", values[i]);
			if(writer.isPresent()) {
				writer.get().printf(indent + tagNames[i] + ": %s\n", values[i]);
			}
		}
	}
	
	/**
	 * Lekérdezések ereményét írja ki konzolra és fájlba, támogatja a többértékű eredményeket.
	 * @param queryDesc a lekérdezés leírása
	 * @param writer opcionális <code>PrintWriter</code>, ha nincs a konzolra, ha van akkor fájlba is ír
	 * @param queryResults a lekérdezés eredménye(i)
	 */
	@SafeVarargs
	public static void printQueryResult(String queryDesc, Optional<PrintWriter> writer, List<String>... queryResults) {
		System.out.println(queryDesc);
		if(writer.isPresent()) {
			writer.get().println(queryDesc);
		}
		if(queryResults.length == 0 || queryResults[0].size() == 0) {
			System.out.println("\tNincs");
			if(writer.isPresent()) {
				writer.get().println("\tNincs");
			}
		}
		else {
			
			for(int i = 0; i < queryResults[0].size(); i++) {
				if(i != 0) {
					System.out.print("\n");
					if(writer.isPresent()) {
						writer.get().print("\n");
					}
				}
				System.out.printf("\t%s", queryResults[0].get(i));
				if(writer.isPresent()) {
					writer.get().printf("\t%s", queryResults[0].get(i));
				}
				for(int j = 1; j < queryResults.length; j++) {
					if(j != queryResults.length) {
						System.out.print(", ");
						if(writer.isPresent()) {
							writer.get().print(", ");
						}
					}
					System.out.printf("%s", queryResults[j].get(i));
					if(writer.isPresent()) {
						writer.get().printf("%s", queryResults[j].get(i));
					}
				}
			}
		}
	}
	
	
	
	/**
	 * Új szöveges tartalmú XML elemet hoz létre.
	 * @param doc a dokumentum amiben létrehozza az új elemet
	 * @param parentElement az új elem szülője
	 * @param name az új elem neve
	 * @param textContent az új elem szöveges tartalma
	 */
	public static void createTextElement(Document doc, Element parentElement, String name, String textContent) {
		Element element = doc.createElement(name);
		element.setTextContent(textContent);
		parentElement.appendChild(element);
	}
}
