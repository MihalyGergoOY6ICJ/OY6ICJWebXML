package hu.domparse.oy6icj;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class DomWrite {
	/**
	 * <code>Document</code> XML fájlba írása.
	 * @param name az XML fájl neve
	 * @param doc a menteni kívánt <code>Document<code>
	 * @throws TransformerException
	 */
	public static void write(String name, Document doc) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		DOMSource source = new DOMSource(doc);
		
		StreamResult file = new StreamResult(new File("res/" + name));
		
		transformer.transform(source, file);
	}
}
