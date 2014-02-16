package net.raydeejay.gossip.util;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.raydeejay.gossip.engine.interpreter.SmallByteArray;
import net.raydeejay.gossip.engine.interpreter.SmallInt;
import net.raydeejay.gossip.engine.interpreter.SmallInterpreter;
import net.raydeejay.gossip.engine.interpreter.SmallObject;

import com.thoughtworks.xstream.XStream;


/**
 * Execute this converter BEFORE to modify your class
 */
public class ImageToXML {
	private static String path = "/home/raydj/gossip/workspace/Gossip/";
	
	private static SmallInterpreter theInterpreter;
	private static boolean done = false;
	
//	private static void readImage(String name) {
//		try {
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name));
//			theInterpreter = new SmallInterpreter();
//			//theInterpreter = (SmallInterpreter) ois.readObject();
//			// now read object by object
//			theInterpreter.nilObject = (SmallObject) ois.readObject();
//			theInterpreter.trueObject = (SmallObject) ois.readObject();
//			theInterpreter.falseObject = (SmallObject) ois.readObject();
//			theInterpreter.smallInts = (SmallInt []) ois.readObject();
//			theInterpreter.ArrayClass = (SmallObject) ois.readObject();
//			theInterpreter.BlockClass = (SmallObject) ois.readObject();
//			theInterpreter.ContextClass = (SmallObject) ois.readObject();
//			theInterpreter.IntegerClass = (SmallObject) ois.readObject();
//			System.out.println("image initialized");
//			done = true;
//		} catch (Exception e) {
//			System.out.println("received I/O exception " + e);
//		}
//	}

	private static void readImage(String name) {
		try {
			FileInputStream is = new FileInputStream(name);

            java.util.List<SmallObject> obj = new ArrayList<SmallObject>();
            java.util.List<int[]> list = new ArrayList<int[]>();
            
            // initialize interpreter
            theInterpreter = new SmallInterpreter();
            
            
            // Read in input into list
            // for each line in list, create smallobject of appropriate class in obj
            DataInputStream r = new DataInputStream(new BufferedInputStream(is));
            try {
                int v = r.readInt();
                if (v != SmallInterpreter.imageFormatVersion) {
                    JOptionPane.showMessageDialog(new JFrame("X"), "Incorrect Image Version:\nI was expecting " + Integer.toString(theInterpreter.imageFormatVersion) + " but got " + Integer.toString(v) + "  \n");
                }
                while (true) {
                    int si = r.readInt();
                    int[] qw = new int[si];
                    qw[0] = si;
                    qw[1] = r.readInt();
                    if (qw[1] == 0) { //SmallInt
                        for (int i = 2 ; i < si ; i++){
                            qw[i] = r.readInt();
                        }
                        obj.add(new SmallInt());
                    } else if ((qw[1] == 0) || (qw[1]==2)) { //SmallObject
                        for (int i = 2 ; i < si ; i++){
                            qw[i] = r.readInt();
                        }
                        obj.add(new SmallObject());
                    } else if (qw[1]==1){ // SmallByteArray
                        qw[2] = r.readInt(); // class
                        qw[3] = r.readInt(); // datasize
                        for (int i = 4; i < 4 + qw[3]; i++){
                            qw[i]= r.readInt();
                        }
                        for (int i = 4 + qw[3]; i < qw[0]; i++){
                            qw[i]= (int)r.readByte();
                        }
                        obj.add(new SmallByteArray());
                    }
                    list.add(qw);
                }
            } catch (EOFException e) {}
            System.out.println("Done reading into list");
            
            // using list, fill in fields in smallobjects in obj
            int it;
            for (it = 0; it < list.size(); it++) {
                SmallObject co = obj.get(it);
                int[] cso = list.get(it);
                
                // Determine values
                int objLength = cso[0];
                int objType = cso[1];
                int objClass = cso[2];
                int objDataLength = cso[3];
                
                int[] objData = new int[objDataLength];
                for (int i = 4; i < 4 + objDataLength; i++) {
                    objData[i-4] = cso[i];
                }
                
                int smallIntValue = 0;
                if (objType == 0) { // SmallInt
                    smallIntValue = cso[objLength - 1];
                }
                
                byte[] byteArrayValues = new byte[0];
                if (objType == 1) { // SmallByteArray
                    byteArrayValues = new byte[objLength - 4 - objDataLength];
                    for (int i = 4 + objDataLength; i < objLength; i++) {
                        byteArrayValues[i - 4 - objDataLength] = (byte) cso[i];
                    }
                }
                
                // Add values
                co.objClass = obj.get(objClass);
                co.data = new SmallObject[objDataLength];
                for (int i = 0 ; i < objDataLength; i++) {
                    co.data[i] = obj.get(objData[i]);
                }
                
                if (objType == 0) { // SmallInt
                    ((SmallInt) co).value = smallIntValue;
                }
                
                if (objType == 1) { // SmallByteArray
                    ((SmallByteArray) co).values = byteArrayValues;
                }
                
            }
            System.out.println("Done initialising SmallObjects");
            
            // set up constants
            theInterpreter.nilObject = obj.get(0);
            theInterpreter.trueObject = obj.get(1);
            theInterpreter.falseObject = obj.get(2);
            theInterpreter.ArrayClass = obj.get(3);
            theInterpreter.BlockClass = obj.get(4);
            theInterpreter.ContextClass = obj.get(5);
            theInterpreter.IntegerClass = obj.get(6);
            theInterpreter.smallInts = new SmallInt[10];
            theInterpreter.smallInts[0] = (SmallInt) obj.get(7);
            theInterpreter.smallInts[1] = (SmallInt) obj.get(8);
            theInterpreter.smallInts[2] = (SmallInt) obj.get(9);
            theInterpreter.smallInts[3] = (SmallInt) obj.get(10);
            theInterpreter.smallInts[4] = (SmallInt) obj.get(11);
            theInterpreter.smallInts[5] = (SmallInt) obj.get(12);
            theInterpreter.smallInts[6] = (SmallInt) obj.get(13);
            theInterpreter.smallInts[7] = (SmallInt) obj.get(14);
            theInterpreter.smallInts[8] = (SmallInt) obj.get(15);
            theInterpreter.smallInts[9] = (SmallInt) obj.get(16);
            System.out.println("Done loading system");
            
		} catch (Exception e) {
			System.out.println("received I/O exception " + e);
		}
	}
	
	private static void saveImageAsXML(String imageName) {
		try {
			XStream xstream = new XStream(); 
			String xml = xstream.toXML(theInterpreter);
			FileOutputStream fos = new FileOutputStream(path + imageName);
			fos.write(xml.getBytes());
			fos.close();
		} catch (Exception e) {
			System.out.println("received exception " + e);
		}
	
//		try {
//			XMLEncoder e = new XMLEncoder(
//							new BufferedOutputStream(
//								new FileOutputStream(path + "/Test.xml")));
//			/////////
//			// Specifying the persistence strategy
//			e.setPersistenceDelegate(SmallObject.class, new PersistenceDelegate() {
//				protected Expression instantiate(Object oldInstance, Encoder out) {
//					return new Expression(oldInstance,
//                              oldInstance.getClass(),
//                              "new",
//                              new Object[]{ ((SmallObject)oldInstance).objClass, 
//											((SmallObject)oldInstance).data.length});
//				}
//			});
//			/////////
//
//			e.writeObject(theInterpreter.nilObject);
//			e.close();
//
//		} catch (Exception e) {
//			System.out.println("received exception " + e);
//		}
	
	}
	
	public static void main(String[] argv) {
		System.out.println("Executing ImageToXML");
		readImage("Gossip.image");
		System.out.println(theInterpreter);
		saveImageAsXML("image.xml");
	}
}