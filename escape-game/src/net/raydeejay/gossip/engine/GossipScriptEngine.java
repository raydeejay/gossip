package net.raydeejay.gossip.engine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.raydeejay.gossip.engine.interpreter.SmallByteArray;
import net.raydeejay.gossip.engine.interpreter.SmallException;
import net.raydeejay.gossip.engine.interpreter.SmallInt;
import net.raydeejay.gossip.engine.interpreter.SmallInterpreter;
import net.raydeejay.gossip.engine.interpreter.SmallObject;


/**
 * Athena interpreter facade
 * <p>
 * A typical usage is described in athena.example.HelloWorld:
 * <pre>
 * import net.raydeejay.gossip.engine.*;
 *
 * import javax.script.ScriptEngine;
 * import javax.script.ScriptException;
 * import javax.script.ScriptEngineFactory;
 * 
 * 	public class HelloWorld {
 * 		public static void main(String[] argv) {
 * 			ScriptEngineFactory factory = new GossipScriptFactory();
 * 			ScriptEngine engine = factory.getScriptEngine();
 * 			
 * 			try {
 *	 			engine.eval("1 to: 10 do: [:i| <117 i>]");
 * 				engine.eval("<117 'Hello World!'>");
 * 				
 * 				String statement = factory.getOutputStatement("'Hello ' + 'World!'");
 * 				engine.eval(statement);
 *	 		}
 * 			catch(ScriptException e) {
 * 				System.err.println("ERROR :" + e);
 *	 		}
 * 		}
 * 	}
 * </pre>
 * 
 * See <a href="http://java.sun.com/developer/technicalArticles/J2SE/Desktop/scripting/">java.sun.com/developer/technicalArticles/J2SE/Desktop/scripting</a>
 * @author Alexandre Bergel (<a href="mailto:alexandre@bergel.eu">alexandre@bergel.eu</a>)
 */
public class GossipScriptEngine extends AbstractScriptEngine {
	SmallInterpreter theInterpreter;
	private Map<String,String> javaEnvironmentMap = new HashMap<String,String>();


	///////////////////
	// Evaluation
	public Object eval(String code, ScriptContext c) throws ScriptException {
		try {
			return this.executeInImage(code);
		} catch (SmallException e) {throw new ScriptException(e);}
	}

	public Object eval(Reader reader, ScriptContext c) throws ScriptException {
		BufferedReader buff = new BufferedReader(reader);
		try {
			String code = buff.readLine();
			return this.executeInImage(code);
		} catch (Exception e) {throw new ScriptException(e);}
	}

	public Object eval(Reader reader, Bindings n) throws ScriptException {
		BufferedReader buff = new BufferedReader(reader);
		try {
			String code = buff.readLine();
			return this.executeInImage(code);
		} catch (Exception e) {throw new ScriptException(e);}
	}
	///////////////////	

	///////////////////	
	// Scopes
	public Bindings engineScopedBindings() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ENGINE", "Gossip Smalltalk");
		map.put("ENGINE_VERSION", "0.1");
		map.put("IMAGE_NAME", "Gossip.image");
		
		return new SimpleBindings(map);
	}
	
	public Bindings createBindings() {
		return new SimpleBindings();
	}
	///////////////////	

	
	public ScriptEngineFactory getFactory() {
		return new GossipScriptFactory();
	}
		
	public GossipScriptEngine() {
		context.setBindings(this.engineScopedBindings(), ScriptContext.ENGINE_SCOPE);
/*
System.out.println(context.getBindings(ScriptContext.ENGINE_SCOPE ));		
System.out.println(context.getBindings(ScriptContext.ENGINE_SCOPE ).keySet());		
System.out.println(context.getBindings(ScriptContext.GLOBAL_SCOPE  ));	
*/
	}
	
	private boolean isVerbose() {
		return this.get("VERBOSE").equals("true");
	}

	private String getDisplay() {
		Object ans = this.get("DISPLAY");
		if (ans == null)
			return "shell";
		else
			return (String)ans;

	}

	private String imageName() {
		Object ans = this.get("IMAGE_NAME");
		if (ans == null) 
			return "Athena.image";
		else
			return (String)ans;
	}

	private void setEnvironment() {
		// Environment has to be set after having loaded the image
		javaEnvironmentMap.put("DISPLAY", this.getDisplay());
		javaEnvironmentMap.put("VERBOSE", (this.isVerbose())?"true":"false");
		javaEnvironmentMap.put("IMAGE_NAME", this.imageName());
		theInterpreter.setJavaEnvironment(javaEnvironmentMap);
	}
	
	/**
	 * Load the Gossip Smalltalk image and initialize the environment
	 * Invoked by a factory
	 */
	void initializeImage() {
		if (this.isVerbose()) {System.out.print("Reading image " + this.imageName() + "... ");}
		System.out.print("Reading image " + this.imageName() + "... ");

		this.readImage(this.imageName());
		// The environment can be set only after having read the image
		// because Gossip strings need to be built
		this.setEnvironment();
	}
	
	void initializeImageFromStream(InputStream is) {
		if (this.isVerbose()) {System.out.print("Reading image " + this.imageName() + "... ");}
		System.out.print("Reading image " + this.imageName() + "... ");

		this.readImage(is);
		// The environment can be set only after having read the image
		// because Gossip strings need to be built
		this.setEnvironment();
	}
	
	/**
	 * Read a Gossip image
	 * <p>
	 * It uses ObjectInputStream to read the object graph. Cycle in this graph are correctly handled (no duplication). 
	 *
	 * @param ois Input source
	 */
//	public void readImage(ObjectInputStream  ois) {
//
//		
//		try {
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
//
//			if (this.isVerbose()) {System.out.println(" initialized");}
//			done = true;
//		} catch (Exception e) {
//			System.out.println("received I/O exception " + e);
//		}
//	}

    // Load and save image
    public boolean readImage(InputStream is) {
        try {
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
                    JOptionPane.showMessageDialog(new JFrame("X"), "Incorrect Image Version:\nI was expecting " + Integer.toString(SmallInterpreter.imageFormatVersion) + " but got " + Integer.toString(v) + "  \n");
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
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame("X"), "IO Exception: " + e.toString());
            return false;
        }
    }

    /**
	 * Read an Athena image
	 * <p>
	 * It uses ObjectInputStream to read the object graph. Cycle in this graph are correctly handled (no duplication). 
	 *
	 * @param name filename of the image
	 */
	public void readImage(String name) {
		try {
//			readImage(new ObjectInputStream(new FileInputStream(name)));
			readImage(new FileInputStream(name));
		}
		catch(IOException e) {System.err.println(e);}
	}


	@SuppressWarnings("unused")
	//It is okay, needed for readability
	private boolean done = false;

	/**
	 * Excecute a Smalltalk expression
	 *
	 * @param exp Expression to execute
	 */
	private SmallObject executeInImage(String exp) throws SmallException {
		// start from the basics
		SmallObject TrueClass = theInterpreter.trueObject.objClass;
		SmallObject name = TrueClass.data[0]; // a known string
		SmallObject StringClass = name.objClass;
		// now look for the method
		SmallObject methods = StringClass.data[2];
		SmallObject doItMeth = null;
		for (int i = 0; i < methods.data.length; i++) {
		   SmallObject aMethod = methods.data[i];
		   if ("doIt".equals(aMethod.data[0].toString()))
		     doItMeth = aMethod;
		}
		if (doItMeth == null) {
			System.out.println("can't find do it!!");
			return theInterpreter.nilObject;
		} else {
			SmallByteArray rec = new SmallByteArray(StringClass, exp);
			SmallObject args = new SmallObject(theInterpreter.ArrayClass, 1);
			args.data[0] = rec;
			SmallObject ctx = theInterpreter.buildContext(theInterpreter.nilObject, args, doItMeth);
			return theInterpreter.execute(ctx, null, null);
		}
	}
}
