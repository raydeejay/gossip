package net.raydeejay.gossip.util;


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import net.raydeejay.gossip.engine.interpreter.SmallByteArray;
import net.raydeejay.gossip.engine.interpreter.SmallInt;
import net.raydeejay.gossip.engine.interpreter.SmallInterpreter;
import net.raydeejay.gossip.engine.interpreter.SmallJavaObject;
import net.raydeejay.gossip.engine.interpreter.SmallObject;

import com.thoughtworks.xstream.XStream;


/**
 * Execute this converter BEFORE to modify your class
 */
public class ConvertAthenaImageToVersion1 {
	private static String path = "/home/raydj/gossip/workspace/Gossip/";
	
	private static SmallInterpreter theInterpreter;
	private static boolean done = false;
	
	private static void readImage(String name) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name));
			theInterpreter = new SmallInterpreter();
			//theInterpreter = (SmallInterpreter) ois.readObject();
			// now read object by object
			theInterpreter.nilObject = (SmallObject) ois.readObject();
			theInterpreter.trueObject = (SmallObject) ois.readObject();
			theInterpreter.falseObject = (SmallObject) ois.readObject();
			theInterpreter.smallInts = (SmallInt []) ois.readObject();
			theInterpreter.ArrayClass = (SmallObject) ois.readObject();
			theInterpreter.BlockClass = (SmallObject) ois.readObject();
			theInterpreter.ContextClass = (SmallObject) ois.readObject();
			theInterpreter.IntegerClass = (SmallObject) ois.readObject();
			System.out.println("image initialized");
			done = true;
		} catch (Exception e) {
			System.out.println("received I/O exception " + e);
		}
	}

    private static boolean saveImageToOutputStream(OutputStream name) {
        LinkedHashSet<SmallObject> set = new LinkedHashSet<SmallObject>();
        set.add(theInterpreter.nilObject);
        set.add(theInterpreter.trueObject);
        set.add(theInterpreter.falseObject);
        set.add(theInterpreter.ArrayClass);
        set.add(theInterpreter.BlockClass);
        set.add(theInterpreter.ContextClass);
        set.add(theInterpreter.IntegerClass);
        set.addAll(Arrays.asList(theInterpreter.smallInts));
        while (true) {
            java.util.List<SmallObject> newList = new ArrayList<SmallObject>();
            for (SmallObject o : set) {
                newList.add(o.objClass);
                newList.addAll(Arrays.asList(o.data));
            }
            int s1 = set.size();
            set.addAll(newList);
            if ((set.size() - s1) == 0){ break; }
        }
        
        ArrayList<SmallObject> fulllist = new ArrayList<SmallObject>();
        fulllist.addAll(set);
        
        // At this point we should have a flat object memory in list
        // what size is it?
         System.out.print("Total Object Size: ");
         System.out.print(fulllist.size());
         System.out.println();
        
        // Dump SmallJavaObjects
        ArrayList<SmallObject> list = new ArrayList<SmallObject>();
        for (SmallObject o: fulllist){
            if (!(o instanceof SmallJavaObject)) {
                list.add(o);
            }
        }
        
         System.out.print("Object Size after pruning: ");
         System.out.print(list.size());
         System.out.println();
        
        // Give each remaining object an id
        for (int i = 0 ; i < list.size(); i++){
            list.get(i).id = i;
        }
        
        // File out...
        DataOutputStream im = new DataOutputStream(new BufferedOutputStream(name));
        try {
            im.writeInt(SmallInterpreter.imageFormatVersion);
            
            for (SmallObject o : list) {
                if (o instanceof SmallInt) {
                    // Write size of record
                    int s = 1 /* length */ + 1 /* type */ + 1 /* class */ + 1 /* size of data */ + o.data.length + 1;
                    im.writeInt(s);
                    im.writeInt(0); // SmallInt
                    im.writeInt(o.objClass.id); // Class
                    im.writeInt(o.data.length); // Length of data
                    for (SmallObject o2 : o.data) {
                        // Fix references to SmallJavaObjects, which can't be serialised yet
                        if (o2 instanceof SmallJavaObject) {
                            im.writeInt(theInterpreter.nilObject.id);
                        } else {
                            im.writeInt(o2.id);}
                    }
                    im.writeInt(((SmallInt)o).value);
                } else if (o instanceof SmallByteArray) {
                    // Write size of record
                    int s = 1 /* length */ + 1 /* type */ + 1 /* class */ + 1 /* size of data */ + o.data.length + ((SmallByteArray)o).values.length;
                    im.writeInt(s);
                    im.writeInt(1); // SmallByteArray
                    im.writeInt(o.objClass.id); // Class
                    im.writeInt(o.data.length); // Length of data
                    for (SmallObject o2 : o.data) {
                        // Fix references to SmallJavaObjects, which can't be serialised yet
                        if (o2 instanceof SmallJavaObject) {
                            im.writeInt(theInterpreter.nilObject.id);
                        } else {
                            im.writeInt(o2.id);}
                        
                    }
                    for (byte b : ((SmallByteArray)o).values) {
                        im.writeByte(b);
                    }
                } else if (o instanceof SmallJavaObject) {
                    // Do nothing - SmallJavaObjects cannot be serialised at this stage
                    // this is a placeholder for if we change our minds
                } else {
                    // Write size of record
                    int s = 1 /* length */ + 1 /* type */ + 1 /* class */ + 1 /* size of data */ + o.data.length;
                    im.writeInt(s);
                    im.writeInt(2); // SmallObject
                    im.writeInt(o.objClass.id); // Class
                    im.writeInt(o.data.length); // Length of data
                    for (SmallObject o2 : o.data) {
                        // Fix references to SmallJavaObjects, which can't be serialised yet
                        if (o2 instanceof SmallJavaObject) {
                            im.writeInt(theInterpreter.nilObject.id);
                        } else {
                            im.writeInt(o2.id);}
                        
                    }
                }
            }
            im.close();
            
        } catch (Exception e) { System.out.println(e); return false; }
        
        return true;
        
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
		System.out.println("Saving to new format");
		
        try {
            saveImageToOutputStream(new FileOutputStream("image.v1"));
        } catch (Exception e) {
        	System.out.println("got I/O Exception " + e);
        }
	}
}
