package hu.domparse.oy6icj;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import hu.domparse.oy6icj.model.Parsable;
import hu.domparse.oy6icj.model.XMLParseException;

public class OY6ICJDOMRead {
	/**
	 * XML beolvasása, fordítása a modelClass osztályra
	 */
	
	/**
	 * XML beolvasása, fordítása egy <code>Parsable</code> osztályra.
	 * @param filePath az XML fájl útvonala
	 * @param modelClass az XML szerkezetének megfelelő osztály
	 * @return az XML fájlból előállított <code>modelClass</code> osztály
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XMLParseException az XML olyan címkét tartalmaz, amit a <code>modelClass</code> nem tudott feldolgozni
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws TransformerException
	 */
	public static Parsable read(String filePath, Class<? extends Parsable> modelClass) throws ParserConfigurationException, SAXException, IOException, XMLParseException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, TransformerException {
		File xmlFile = new File(filePath);
		
		DocumentBuilderFactory dmf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dmf.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		doc.normalizeDocument();
		
		Parsable model = (Parsable) modelClass.getConstructors()[0].newInstance();
		model.parse(doc.getDocumentElement());
		
		DomWrite.write("test.xml", doc);
		return model;
	}
}
