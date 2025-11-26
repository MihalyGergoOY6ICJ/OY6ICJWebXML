package hu.domparse.oy6icj.model;


/**
 * XML feldolgozásakor keletkező nem végzetes hiba
 */
public class XMLParseException extends RuntimeException {
	private static final long serialVersionUID = 7149980639126930318L;

	public XMLParseException(String elementName) {
		super(elementName + " not parsable!");
	}
}
