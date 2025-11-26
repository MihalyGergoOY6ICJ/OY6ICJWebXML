package hu.domparse.oy6icj.model;

import java.io.PrintWriter;
import java.util.Optional;

import org.w3c.dom.Element;


/**
 * XML feldolgozására szolgáló API.
 */
public interface Parsable {
	/**
	 * Az implementáló osztálynak megfelelő címke neve az XML-ben, elrejtése kötelező.
	 */
	final public static String elementName = "";
	 
	/**
	 * Feldolgozza az implementáló osztálynak megfelelő XML <code>Element</code>-et.
	 * @param parentElement az implementáló osztálynak megfelelő XML elem szülő eleme
	 * @throws XMLParseException az XML olyan címkét tartalmaz, amit az implementáló osztály nem tudott feldolgozni
	 */
	public void parse(Element parentElement) throws XMLParseException;
	 
	
	/**
	 * Kiír a konzolra, opcionálisan fájlba.
	 * @param writer opcionális <code>PrintWriter</code>, ha nincs a konzolra, ha van akkor fájlba is ír
	 * @param indent a kiírás tartalmazzon-e behúzást (egymásba ágyazott <code>Parsable</code> osztályok 
	 * kiírásakor az alosztályok mindenképp tartalmaznak behúzást, a hierarchia szemléltetése miatt)
	 */
	public void print(Optional<PrintWriter> writer, boolean indent);
	 
}
