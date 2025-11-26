package hu.domparse.oy6icj.model;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Az XML gyökérelemének megfelelő osztálynak kell implementálja, egy dokumentumhoz csak egy tartozhat.
 */
public interface RootWritable extends Writable{
	
	/**
	 * A <code>Document</code>-et és a gyökérelemet hozza létre, majd hívja a tényleges fájl kiírást.
	 * @param name az XML fájl neve
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public void write(String name) throws ParserConfigurationException, TransformerException;
}
