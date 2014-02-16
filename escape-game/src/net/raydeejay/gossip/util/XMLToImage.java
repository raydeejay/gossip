package net.raydeejay.gossip.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import net.raydeejay.gossip.engine.interpreter.SmallInterpreter;

import com.thoughtworks.xstream.XStream;

/**
 * After having converted your image into an XML file, you may modify classes for which instances were serialized. Just run XMLToImage later on.
 */
public class XMLToImage {
	private static String path = "/home/raydj/gossip/workspace/Gossip/";
	
	private static SmallInterpreter theInterpreter;
	private static boolean done = false;
	
	private static void readXMLImage(String imageName) {
		try {
			XStream xstream = new XStream(); 
			FileInputStream fis = new FileInputStream(path + imageName);
			theInterpreter = new SmallInterpreter();
			theInterpreter = (SmallInterpreter)xstream.fromXML(fis);
/*			theInterpreter.trueObject = (SmallObject)xstream.fromXML(fis);
			theInterpreter.falseObject = (SmallObject)xstream.fromXML(fis);
			theInterpreter.smallInts = (SmallObject)xstream.fromXML(fis);
			theInterpreter.ArrayClass = (SmallObject)xstream.fromXML(fis);
			theInterpreter.BlockClass = (SmallObject)xstream.fromXML(fis);
			theInterpreter.ContextClass = (SmallObject)xstream.fromXML(fis);
			theInterpreter.IntegerClass = (SmallObject)xstream.fromXML(fis);
*/			
			fis.close();
			done = true;
		} catch (Exception e) {
			System.out.println("received exception " + e);
			e.printStackTrace();
		}
	}
	
	private static void saveImage(String imageName) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + imageName));
			//oos.writeObject(this);
			// write one by one to avoid serialization
			oos.writeObject(theInterpreter.nilObject);
			oos.writeObject(theInterpreter.trueObject);
			oos.writeObject(theInterpreter.falseObject);
			oos.writeObject(theInterpreter.smallInts);
			oos.writeObject(theInterpreter.ArrayClass);
			oos.writeObject(theInterpreter.BlockClass);
			oos.writeObject(theInterpreter.ContextClass);
			oos.writeObject(theInterpreter.IntegerClass);

			oos.close();
		} catch (Exception e) {
			System.out.println("received exception " + e);
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] argv) {
		System.out.println("Executing XMLToImage");
		readXMLImage("image.xml");
		System.out.println(theInterpreter);
		saveImage("fromXML.image");
	}
}