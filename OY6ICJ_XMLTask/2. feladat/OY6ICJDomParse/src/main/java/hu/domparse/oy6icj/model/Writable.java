package hu.domparse.oy6icj.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * XML fájlba írására szolgáló API.
 */
public interface Writable {
	/**
	 * Az implementáló osztálynak megfelelő XML elemet hozza létre.
	 * @param doc az írandó dokumentum
	 * @param parentElement az implementáló osztálynak megfelelő XML elem szülője, 
	 * ehhez helyben hozzá is kell fűzni a létrehozott elemet
	 */
	public void writeElement(Document doc, Element parentElement);
}
