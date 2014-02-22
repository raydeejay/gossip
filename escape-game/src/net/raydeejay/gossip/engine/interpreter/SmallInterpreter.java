package net.raydeejay.gossip.engine.interpreter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import net.raydeejay.escapegame.Reactor;

import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitPane;

import ru.sg_studio.escapegame.ContextedFactory;
import ru.sg_studio.escapegame.ContextedObjectProvider.ObjectPrototype;
import ru.sg_studio.escapegame.SmallInterpreterInterfacer;
import ru.sg_studio.escapegame.eventSystem.CommonEventHandler.EventType;

/**
 * Athena interpreter.
 * <p>
 * Originally based on Tim Budd's work with Little Smalltalk. Description of
 * <code>net.raydeejay.gossip.engine.interpreter</code> summarizes the
 * differences.
 * 
 * @see <a
 *      href="package-summary.html">net.raydeejay.gossip.engine.interpreter</a>
 * @author Timothy A. Budd (<a
 *         href="http://web.engr.oregonstate.edu/~budd">web.engr
 *         .oregonstate.edu/~budd</a>)
 * @author Alexandre Bergel (<a
 *         href="mailto:alexandre@bergel.eu">alexandre@bergel.eu</a>)
 */
public class SmallInterpreter implements Serializable {
	private static final long serialVersionUID = 1L;

	// version
	public static final int imageFormatVersion = 1;

	// global constants
	public SmallObject nilObject;
	public SmallObject trueObject;
	public SmallObject falseObject;
	public SmallInt[] smallInts;
	public SmallObject ArrayClass;
	public SmallObject BlockClass;
	public SmallObject ContextClass;
	public SmallObject IntegerClass;

	/** Current namespace. Maybe a thread local variable in the future. */
	public SmallObject namespace;

	private HashMap<String, SmallByteArray> javaEnvironment = new HashMap<String, SmallByteArray>();

	private int mod(int x, int y) {
		int result = x % y;
		if (result < 0) {
			result += y;
		}
		return result;
	}

	public void addInJavaEnvironment(String key, String value) {
		this.javaEnvironment.put(key, this.createGossipString(value));
	}

	private SmallByteArray createGossipString(String msg) {
		SmallObject TrueClass = this.trueObject.objClass;
		SmallObject name = TrueClass.data[0]; // a known string
		SmallObject StringClass = name.objClass;

		SmallByteArray rec = new SmallByteArray(StringClass, msg);
		return rec;
	}

	public void setJavaEnvironment(Map<String, String> mapping) {
		javaEnvironment = new HashMap<String, SmallByteArray>();
		Set<String> keys = mapping.keySet();
		for (String k : keys) {
			String value = mapping.get(k);

			javaEnvironment.put(k, this.createGossipString(value));
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getJavaEnvironment() {
		return (Map<String, String>) this.javaEnvironment.clone();
	}

	private boolean saveImageToOutputStream(OutputStream name) {
		LinkedHashSet<SmallObject> set = new LinkedHashSet<SmallObject>();
		set.add(nilObject);
		set.add(trueObject);
		set.add(falseObject);
		set.add(ArrayClass);
		set.add(BlockClass);
		set.add(ContextClass);
		set.add(IntegerClass);
		set.addAll(Arrays.asList(smallInts));
		
		set.remove(null); // does nothing?
		
		while (true) {
			java.util.List<SmallObject> newList = new ArrayList<SmallObject>();
			for (SmallObject o : set) {
				if(o != null) {
				    newList.add(o.objClass);
				    newList.addAll(Arrays.asList(o.data));
				}
			}
			int s1 = set.size();
			set.addAll(newList);
			if ((set.size() - s1) == 0) {
				break;
			}
		}

		ArrayList<SmallObject> fulllist = new ArrayList<SmallObject>();
		fulllist.addAll(set);

		// At this point we should have a flat object memory in list
		// what size is it?
		// System.out.print("Total Object Size:  ");
		// System.out.print(list.size());
		// System.out.println();

		// Dump SmallJavaObjects
		ArrayList<SmallObject> list = new ArrayList<SmallObject>();
		for (SmallObject o : fulllist) {
			if (!(o instanceof SmallJavaObject)) {
				list.add(o);
			}
		}

		list.remove(null);
		
		// Give each remaining object an id
		for (int i = 0; i < list.size(); i++) {
			list.get(i).id = i;
		}

		// File out...
		DataOutputStream im = new DataOutputStream(new BufferedOutputStream(
				name));
		try {
			im.writeInt(imageFormatVersion);

			for (SmallObject o : list) {
				if (o instanceof SmallInt) {
					// Write size of record
					int s = 1 /* length */+ 1 /* type */+ 1 /* class */+ 1 /*
																		 * size
																		 * of
																		 * data
																		 */
							+ o.data.length + 1;
					im.writeInt(s);
					im.writeInt(0); // SmallInt
					im.writeInt(o.objClass.id); // Class
					im.writeInt(o.data.length); // Length of data
					for (SmallObject o2 : o.data) {
						// Fix references to SmallJavaObjects, which can't be
						// serialised yet
						if (o2 instanceof SmallJavaObject) {
							im.writeInt(nilObject.id);
						} else {
							im.writeInt(o2.id);
						}
					}
					im.writeInt(((SmallInt) o).value);
				} else if (o instanceof SmallByteArray) {
					// Write size of record
					int s = 1 /* length */+ 1 /* type */+ 1 /* class */+ 1 /*
																		 * size
																		 * of
																		 * data
																		 */
							+ o.data.length
							+ ((SmallByteArray) o).values.length;
					im.writeInt(s);
					im.writeInt(1); // SmallByteArray
					im.writeInt(o.objClass.id); // Class
					im.writeInt(o.data.length); // Length of data
					for (SmallObject o2 : o.data) {
						// Fix references to SmallJavaObjects, which can't be
						// serialised yet
						if (o2 instanceof SmallJavaObject) {
							im.writeInt(nilObject.id);
						} else {
							im.writeInt(o2.id);
						}

					}
					for (byte b : ((SmallByteArray) o).values) {
						im.writeByte(b);
					}
				} else if (o instanceof SmallJavaObject) {
					// Do nothing - SmallJavaObjects cannot be serialised at
					// this stage
					// this is a placeholder for if we change our minds
				} else {
					// Write size of record
					int s = 1 /* length */+ 1 /* type */+ 1 /* class */+ 1 /*
																		 * size
																		 * of
																		 * data
																		 */
							+ o.data.length;
					im.writeInt(s);
					im.writeInt(2); // SmallObject
					im.writeInt(o.objClass.id); // Class
					im.writeInt(o.data.length); // Length of data
					for (SmallObject o2 : o.data) {
						// Fix references to SmallJavaObjects, which can't be
						// serialised yet
						if (o2 instanceof SmallJavaObject) {
							im.writeInt(nilObject.id);
						} else if (o2 == null) {
							im.writeInt(nilObject.id);
						} else {
							im.writeInt(o2.id);
						}

					}
				}
			}
			im.close();

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		return true;

	}

	/** Create a new integer */
	private SmallInt newInteger(int val) {
		if ((val >= 0) && (val < 10))
			return smallInts[val];
		else
			return new SmallInt(IntegerClass, val);
	}

	/**
	 * Method lookup algorithm This method assume that the image supports Method
	 * Namespaces
	 */
	private SmallObject methodLookup(SmallObject receiverClass,
			SmallByteArray messageSelector, SmallObject context,
			SmallObject arguments) throws SmallException {
		String name = messageSelector.toString();
		SmallObject cls;

		SmallObject potentialMethod = null; // Method that will be returned if
											// no namespace matches
		int shortestPath = 100000;

		for (cls = receiverClass; cls != nilObject; cls = cls.data[1]) {
			SmallObject dict = cls.data[2]; // dictionary in class
			for (int i = 0; i < dict.data.length; i++) {
				SmallObject aMethod = dict.data[i];
				// A method is found:
				if (name.equals(aMethod.data[0].toString())) {
					// If the image does not support Method Namespaces
					if (aMethod.data.length == 7 || this.namespace == null)
						potentialMethod = aMethod;

					else if (aMethod.data.length >= 8 && this.namespace != null) {
						// Check if the namespace of the method is the current
						// one
						if (aMethod.data[7] == this.namespace)
							return aMethod;
						int path = 0;
						// We begin from the current namespace
						// System.out.println("D2: this.namespace.data[1].toString()="
						// + this.namespace.data[1].toString() +
						// "   methodName=" + name);
						for (SmallObject nm = this.namespace; nm != this.nilObject; nm = nm.data[0]) {
							// The first variable of MethodNamespace is the
							// parent

							path++;

							// System.out.println("D: shortestPath=" +
							// shortestPath + "   path=" + path +
							// "  nm.data[1].toString()=" +
							// nm.data[1].toString());
							// System.out.println("D: " + path +
							// "   nm.data.length="+nm.data.length);
							if (aMethod.data[7] == this.nilObject
									&& nm.data[0] == this.nilObject
									&& path < shortestPath) {
								// System.out.println("D3");
								shortestPath = path;
								potentialMethod = aMethod;
							}
							if (aMethod.data[7] == nm && path < shortestPath) {
								// System.out.println("D4: aMethod.data[7].data[1].toString()="
								// + aMethod.data[7].data[1].toString());
								shortestPath = path;
								potentialMethod = aMethod;
							}
						}
						/*
						 * if (potentialMethod.data[7] != this.nilObject)
						 * System.out.println("Result: shortestPath=" +
						 * shortestPath +
						 * "   potentialMethod.data[7].data[1].toString()=" +
						 * potentialMethod.data[7].data[1].toString()); else
						 * System.out.println("Result2: shortestPath=" +
						 * shortestPath);
						 */
					}

					/*
					 * if (aMethod.data.length >= 8 && aMethod.data[7] ==
					 * this.namespace) return aMethod; if (aMethod.data.length
					 * >= 8 && aMethod.data[7] == this.nilObject)
					 * potentialMethod = aMethod;
					 */
				}
			}
			// No better method has been found
			if (potentialMethod != null)
				return potentialMethod;
		}

		// We reify the method if the receiver is a Java Object
		// Maybe this could be done in the image itself.
		if (receiverClass.data[0].toString().equals("JavaObject")
				&& !name.startsWith("invoke:")) {
			// System.out.println(receiverClass.data[0].toString() + " >> " +
			// name + "    " +
			// receiverClass.data[0].toString().equals("JavaObject") + " " +
			// !name.startsWith("invoke:"));
			// System.out.println(arguments.data.length);
			SmallObject[] newArgs = new SmallObject[arguments.data.length + 1];
			String javaMethodName = name.replaceAll(":", ""); // Remove all : in
																// the Java
																// Method name
			newArgs[0] = arguments.data[0]; // same receiver
			newArgs[1] = new SmallByteArray(messageSelector.objClass,
					javaMethodName); // 1st argument = name of the method

			// We copy arguments
			for (int i = 1; i < arguments.data.length; i++)
				newArgs[i + 1] = arguments.data[i];

			String newNameToInvoke = (arguments.data.length == 1) ? "invoke:"
					: ((arguments.data.length == 2) ? "invoke:with:"
							: ((arguments.data.length == 3) ? "invoke:with:with:"
									: ("error:")));
			// System.out.println("DONE" + javaMethodName+ " " +
			// newNameToInvoke);
			arguments.data = newArgs;
			return methodLookup(receiverClass, new SmallByteArray(
					messageSelector.objClass, newNameToInvoke), context,
					arguments);
		}

		// try once to handle method in Smalltalk before giving up
		if (name.equals("error:"))
			throw new SmallException("Unrecognized message selector: "
					+ messageSelector, context);
		SmallObject[] newArgs = new SmallObject[2];
		newArgs[0] = arguments.data[0]; // same receiver
		newArgs[1] = new SmallByteArray(messageSelector.objClass,
				"Unrecognized message selector: " + name);
		arguments.data = newArgs;
		return methodLookup(receiverClass, new SmallByteArray(
				messageSelector.objClass, "error:"), context, arguments);
	}

	public SmallObject buildContext(SmallObject oldContext,
			SmallObject arguments, SmallObject method) {
		SmallObject context = new SmallObject(ContextClass, 7);
		context.data[0] = method;
		context.data[1] = arguments;
		// allocate temporaries
		int max = ((SmallInt) (method.data[4])).value;
		if (max > 0) {
			context.data[2] = new SmallObject(ArrayClass, max);
			while (max > 0)
				// iniailize to nil
				context.data[2].data[--max] = nilObject;
		}
		// allocate stack
		max = ((SmallInt) (method.data[3])).value;
		context.data[3] = new SmallObject(ArrayClass, max);
		context.data[4] = smallInts[0]; // byte pointer
		context.data[5] = smallInts[0]; // stacktop
		context.data[6] = oldContext;
		return context;
	}

	// execution method
	public SmallObject execute(SmallObject context, final Thread myThread,
			final Thread parentThread) throws SmallException {
		SmallObject[] selectorCache = new SmallObject[197];
		SmallObject[] classCache = new SmallObject[197];
		SmallObject[] methodCache = new SmallObject[197];
		int lookup = 0;
		int cached = 0;

		SmallObject[] contextData = context.data;

		outerLoop: while (true) {

			SmallObject method = contextData[0]; // method in context
			byte[] code = ((SmallByteArray) method.data[1]).values; // code
																	// pointer
			int bytePointer = ((SmallInt) contextData[4]).value;
			SmallObject[] stack = contextData[3].data;
			int stackTop = ((SmallInt) contextData[5]).value;
			SmallObject returnedValue = null;
			SmallObject temp;
			SmallObject[] tempa;

			// everything else can be null for now
			SmallObject[] temporaries = null;
			SmallObject[] instanceVariables = null;
			SmallObject arguments = null;
			SmallObject[] literals = null;

			innerLoop: while (true) {
				int high = code[bytePointer++];
				int low = high & 0x0F;
				high = (high >>= 4) & 0x0F;

				if (high == 0) {
					high = low;
					// convert to positive int
					low = (int) code[bytePointer++] & 0x0FF;
				}

				switch (high) {
				// [BYTECODE 1] PushInstance
				case 1:
					if (arguments == null)
						arguments = contextData[1];
					if (instanceVariables == null)
						instanceVariables = arguments.data[0].data;
					// System.out.println("DEB: low = "+low +
					// "  instancaVariables.length = " +
					// instanceVariables.length);
					if (low >= instanceVariables.length)
						throw new SmallException("Bad variable access", context);
					stack[stackTop++] = instanceVariables[low];
					break;

				// [BYTECODE 2] PushArgument
				case 2:
					if (arguments == null)
						arguments = contextData[1];
					stack[stackTop++] = arguments.data[low];
					break;

				// [BYTECODE 3] PushTemporary
				case 3:
					if (temporaries == null)
						temporaries = contextData[2].data;
					stack[stackTop++] = temporaries[low];
					break;

				// [BYTECODE 4] PushLiteral
				case 4:
					if (literals == null)
						literals = method.data[2].data;
					stack[stackTop++] = literals[low];
					break;

				// [BYTECODE 5] PushConstant
				case 5:
					switch (low) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
						stack[stackTop++] = smallInts[low];
						break;
					case 10:
						stack[stackTop++] = nilObject;
						break;
					case 11:
						stack[stackTop++] = trueObject;
						break;
					case 12:
						stack[stackTop++] = falseObject;
						break;
					default:
						throw new SmallException("Unknown constant " + low,
								context);
					}
					break;

				// [BYTECODE 6] AssignInstance
				case 6:
					if (arguments == null)
						arguments = contextData[1];
					if (instanceVariables == null)
						instanceVariables = arguments.data[0].data;
					// leave result on stack
					instanceVariables[low] = stack[stackTop - 1];
					break;

				// [BYTECODE 7] AssignTemporary
				case 7:
					if (temporaries == null)
						temporaries = contextData[2].data;
					temporaries[low] = stack[stackTop - 1];
					break;

				// [BYTECODE 8] MarkArguments
				case 8:
					SmallObject newArguments = new SmallObject(ArrayClass, low);
					tempa = newArguments.data; // direct access to array
					while (low > 0)
						tempa[--low] = stack[--stackTop];
					stack[stackTop++] = newArguments;
					break;

				// [BYTECODE 9] SendMessage (save old context)
				case 9:
					arguments = stack[--stackTop];
					// expand newInteger in line
					// contextData[5] = newInteger(stackTop);
					contextData[5] = (stackTop < 10) ? smallInts[stackTop]
							: new SmallInt(IntegerClass, stackTop);
					// contextData[4] = newInteger(bytePointer);
					contextData[4] = (bytePointer < 10) ? smallInts[bytePointer]
							: new SmallInt(IntegerClass, bytePointer);
					// now build new context
					if (literals == null)
						literals = method.data[2].data;

					returnedValue = literals[low]; // message selector
					// System.out.println("Sending "
					// + returnedValue);
					// System.out.println("Arguments "
					// + arguments);
					// System.out.println("Arguments receiver "
					// + arguments.data[0]);
					// System.out.println("Arguments class "
					// +
					// arguments.data[0].objClass);
					high = mod(
							(arguments.data[0].objClass.hashCode() + returnedValue
									.hashCode()), 197);
					if ((selectorCache[high] != null)
							&& (selectorCache[high] == returnedValue)
							&& (classCache[high] == arguments.data[0].objClass)) {
						method = methodCache[high];
						cached++;
					} else {
						method = methodLookup(arguments.data[0].objClass,
								(SmallByteArray) literals[low], context,
								arguments);
						lookup++;
						selectorCache[high] = returnedValue;
						classCache[high] = arguments.data[0].objClass;
						methodCache[high] = method;
					}
					context = buildContext(context, arguments, method);
					contextData = context.data;
					// load information from context
					continue outerLoop;

					// [BYTECODE 10] SendUnary (save old context)
				case 10:
					if (low == 0) { // isNil
						SmallObject arg = stack[--stackTop];
						stack[stackTop++] = (arg == nilObject) ? trueObject
								: falseObject;
					} else if (low == 1) { // notNil
						SmallObject arg = stack[--stackTop];
						stack[stackTop++] = (arg != nilObject) ? trueObject
								: falseObject;
					} else
						throw new SmallException("Illegal SendUnary " + low,
								context);
					break;

				// [BYTECODE 11] SendBinary
				case 11: {
					if ((stack[stackTop - 1] instanceof SmallInt)
							&& (stack[stackTop - 2] instanceof SmallInt)) {
						int j = ((SmallInt) stack[--stackTop]).value;
						int i = ((SmallInt) stack[--stackTop]).value;
						boolean done = true;
						switch (low) {
						case 0: // <
							returnedValue = (i < j) ? trueObject : falseObject;
							break;
						case 1: // <=
							returnedValue = (i <= j) ? trueObject : falseObject;
							break;
						case 2: // +
							long li = i + (long) j;
							if (li != (i + j))
								done = false; // overflow
							returnedValue = newInteger(i + j);
							break;
						}
						if (done) {
							stack[stackTop++] = returnedValue;
							break;
						} else
							stackTop += 2; // overflow, send message
					}
					// non optimized binary message
					arguments = new SmallObject(ArrayClass, 2);
					arguments.data[1] = stack[--stackTop];
					arguments.data[0] = stack[--stackTop];
					contextData[5] = newInteger(stackTop);
					contextData[4] = newInteger(bytePointer);
					SmallByteArray msg = null;
					switch (low) {
					case 0:
						msg = new SmallByteArray(null, "<");
						break;
					case 1:
						msg = new SmallByteArray(null, "<=");
						break;
					case 2:
						msg = new SmallByteArray(null, "+");
						break;
					}
					method = methodLookup(arguments.data[0].objClass, msg,
							context, arguments);
					context = buildContext(context, arguments, method);
					contextData = context.data;
					continue outerLoop;
				}

				// [BYTECODE 12] PushBlock (low is argument location, next byte
				// is goto value)
				case 12:
					high = (int) code[bytePointer++] & 0x0FF;
					returnedValue = new SmallObject(BlockClass, 10);
					tempa = returnedValue.data;
					tempa[0] = contextData[0]; // share method
					tempa[1] = contextData[1]; // share arguments
					tempa[2] = contextData[2]; // share temporaries
					tempa[3] = contextData[3]; // stack (later replaced)
					tempa[4] = newInteger(bytePointer); // current byte pointer
					tempa[5] = smallInts[0]; // stacktop
					tempa[6] = contextData[6]; // previous context
					tempa[7] = newInteger(low); // argument location
					tempa[8] = context; // creating context
					tempa[9] = newInteger(bytePointer); // current byte pointer
					stack[stackTop++] = returnedValue;
					bytePointer = high;
					break;

				// [BYTECODE 13] Do Primitive, low is arg count, next byte is
				// number
				case 13:
					high = (int) code[bytePointer++] & 0x0FF;
					switch (high) {

					// [PRIMITIVE 1] object identity
					case 1:
						returnedValue = stack[--stackTop];
						if (returnedValue == stack[--stackTop])
							returnedValue = trueObject;
						else
							returnedValue = falseObject;
						break;

					// [PRIMITIVE 2] object class
					case 2:
						returnedValue = stack[--stackTop].objClass;
						break;

					// [PRIMITIVE 4] object size
					case 4:
						returnedValue = stack[--stackTop];
						if (returnedValue instanceof SmallByteArray)
							low = ((SmallByteArray) returnedValue).values.length;
						else
							low = returnedValue.data.length;
						returnedValue = newInteger(low);
						break;

					// [PRIMITIVE 5] object at put
					case 5:
						low = ((SmallInt) stack[--stackTop]).value;
						returnedValue = stack[--stackTop];
						returnedValue.data[low - 1] = stack[--stackTop];
						break;

					// [PRIMITIVE 6] new context execute
					case 6:
						returnedValue = execute(stack[--stackTop], myThread,
								parentThread);
						break;

					// [PRIMITIVE 7] new object allocation
					case 7:
						low = ((SmallInt) stack[--stackTop]).value;
						returnedValue = new SmallObject(stack[--stackTop], low);
						while (low > 0)
							returnedValue.data[--low] = nilObject;
						break;

					// [PRIMITIVE 8] block invocation
					case 8: {
						returnedValue = stack[--stackTop]; // the block
						high = ((SmallInt) returnedValue.data[7]).value; // arg
																			// location
						low -= 2;
						if (low >= 0) {
							temporaries = returnedValue.data[2].data;
							while (low >= 0) {
								temporaries[high + low--] = stack[--stackTop];
							}
						}
						contextData[5] = newInteger(stackTop);
						contextData[4] = newInteger(bytePointer);
						SmallObject newContext = new SmallObject(ContextClass,
								10);
						for (int i = 0; i < 10; i++)
							newContext.data[i] = returnedValue.data[i];
						newContext.data[6] = contextData[6];
						newContext.data[5] = smallInts[0]; // stack top
						newContext.data[4] = returnedValue.data[9]; // starting
																	// addr
						low = newContext.data[3].data.length; // stack size
						newContext.data[3] = new SmallObject(ArrayClass, low); // new
																				// stack
						context = newContext;
						contextData = context.data;
						continue outerLoop;
					}

					// [PRIMITIVE 9] read a char from input
					case 9:
						try {
							returnedValue = newInteger(System.in.read());
						} catch (IOException e) {
							returnedValue = nilObject;
						}
						break;

					// [PRIMITIVE 10] small integer addition need to handle
					// overflow
					case 10: {
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						long lhigh = ((long) high) + (long) low;
						high += low;
						if (lhigh == high)
							returnedValue = newInteger(high);
						else
							returnedValue = nilObject;
					}
						break;

					// [PRIMITIVE 11] small integer quotient
					case 11:
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						high /= low;
						returnedValue = newInteger(high);
						break;

					// [PRIMITIVE 12] small integer remainder
					case 12:
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						high %= low;
						returnedValue = newInteger(high);
						break;

					// [PRIMITIVE 14] small int equality
					case 14:
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						returnedValue = (low == high) ? trueObject
								: falseObject;
						break;

					// [PRIMITIVE 15] small integer multiplication
					case 15: {
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						long lhigh = ((long) high) * (long) low;
						high *= low;
						if (lhigh == high)
							returnedValue = newInteger(high);
						else
							returnedValue = nilObject;
					}
						break;

					// [PRIMITIVE 16] small integer subtraction
					case 16: {
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						long lhigh = ((long) high) - (long) low;
						high -= low;
						if (lhigh == high)
							returnedValue = newInteger(high);
						else
							returnedValue = nilObject;
					}
						break;

					// [PRIMITIVE 17] small integer as string
					case 17:
						low = ((SmallInt) stack[--stackTop]).value;
						returnedValue = new SmallByteArray(stack[--stackTop],
								String.valueOf(low));
						break;

					// [PRIMITIVE 18] debug -- dummy for now
					case 18:
						returnedValue = stack[--stackTop];
						System.out.println("Debug " + returnedValue + " class "
								+ returnedValue.objClass.data[0]);
						break;

					// [PRIMITIVE 19] block fork
					case 19:
						returnedValue = stack[--stackTop];
						new ActionThread(returnedValue, myThread).start();
						break;

					// [PRIMITIVE 20] byte array allocation
					case 20:
						low = ((SmallInt) stack[--stackTop]).value;
						returnedValue = new SmallByteArray(stack[--stackTop],
								low);
						break;

					// [PRIMITIVE 21] string at
					case 21:
						low = ((SmallInt) stack[--stackTop]).value;
						returnedValue = stack[--stackTop];
						SmallByteArray baa = (SmallByteArray) returnedValue;
						low = (int) baa.values[low - 1] & 0x0FF;
						returnedValue = newInteger(low);
						break;

					// [PRIMITIVE 22] string at put
					case 22:
						low = ((SmallInt) stack[--stackTop]).value;
						SmallByteArray ba = (SmallByteArray) stack[--stackTop];
						high = ((SmallInt) stack[--stackTop]).value;
						ba.values[low - 1] = (byte) high;
						returnedValue = ba;
						break;

					// [PRIMITIVE 23] string copy
					case 23:
						returnedValue = stack[--stackTop];
						returnedValue = stack[--stackTop].copy(returnedValue);
						break;

					// [PRIMITIVE 24] string append
					case 24: {
						SmallByteArray a = (SmallByteArray) stack[--stackTop];
						SmallByteArray b = (SmallByteArray) stack[--stackTop];
						low = a.values.length + b.values.length;
						SmallByteArray n = new SmallByteArray(a.objClass, low);
						high = 0;
						for (int i = 0; i < a.values.length; i++)
							n.values[high++] = a.values[i];
						for (int i = 0; i < b.values.length; i++)
							n.values[high++] = b.values[i];
						returnedValue = n;
					}
						break;

					// [PRIMITIVE 26] string compare
					case 26: {
						SmallByteArray a = (SmallByteArray) stack[--stackTop];
						SmallByteArray b = (SmallByteArray) stack[--stackTop];
						low = a.values.length;
						high = b.values.length;
						int s = (low < high) ? low : high;
						int r = 0;
						for (int i = 0; i < s; i++)
							if (a.values[i] < b.values[i]) {
								r = 1;
								break;
							} else if (b.values[i] < a.values[i]) {
								r = -1;
								break;
							}
						if (r == 0)
							if (low < high)
								r = 1;
							else if (low > high)
								r = -1;
						returnedValue = newInteger(r);
					}
						break;

					// [PRIMITIVE 29] Save to SmallWorld 2007 image format (default)
					case 29: { // image export (was image save)
						SmallByteArray a = (SmallByteArray) stack[--stackTop];
						String name = a.toString();
						try {
							saveImageToOutputStream(new FileOutputStream(name));
						} catch (Exception e) {
							throw new SmallException("got I/O Exception " + e,
									context);
						}
						returnedValue = a;
					}
						break;

					// {PRIMITIVE 29} Athena image save
					// case 29: {
					// SmallByteArray a = (SmallByteArray) stack[--stackTop];
					// String name = a.toString();
					// try {
					// // saveImageToOutputStream(new FileOutputStream(name));
					// ObjectOutputStream oos = new ObjectOutputStream(
					// new FileOutputStream(name));
					// // oos.writeObject(this);
					// // write one by one to avoid serialization
					// oos.writeObject(nilObject);
					// oos.writeObject(trueObject);
					// oos.writeObject(falseObject);
					// oos.writeObject(smallInts);
					// oos.writeObject(ArrayClass);
					// oos.writeObject(BlockClass);
					// oos.writeObject(ContextClass);
					// oos.writeObject(IntegerClass);
					// } catch (Exception e) {
					// throw new SmallException("got I/O Exception " + e,
					// context);
					// }
					// returnedValue = a;
					// }
					// break;

					// [PRIMITIVE 30] array at
					case 30:
						low = ((SmallInt) stack[--stackTop]).value;
						returnedValue = stack[--stackTop];
						returnedValue = returnedValue.data[low - 1];
						break;

					// [PRIMITIVE 31] array with: (add new item)
					case 31: {
						SmallObject oldar = stack[--stackTop];
						low = oldar.data.length;
						returnedValue = new SmallObject(oldar.objClass, low + 1);
						for (int i = 0; i < low; i++)
							returnedValue.data[i] = oldar.data[i];
						returnedValue.data[low] = stack[--stackTop];
					}
						break;

					// [PRIMITIVE 32] object add: increase object size
					case 32: {
						returnedValue = stack[--stackTop];
						low = returnedValue.data.length;
						SmallObject na[] = new SmallObject[low + 1];
						for (int i = 0; i < low; i++)
							na[i] = returnedValue.data[i];
						na[low] = stack[--stackTop];
						returnedValue.data = na;
					}
						break;

					// [PRIMITIVE 33] Sleep for a bit
					case 33: {
						low = ((SmallInt) stack[--stackTop]).value;
						try {
							Thread.sleep(low);
						} catch (Exception a) {
						}
					}
						break;

					// [PRIMITIVE 34] thread kill
					case 34: {
						if (parentThread != null)
							parentThread.stop();
						if (myThread != null)
							myThread.stop();
						System.out.println("is there life after death?");
					}
						break;

					// [PRIMITIVE 35] return current context
					case 35:
						returnedValue = context;
						break;

					// [PRIMITIVE 36] fast array creation
					case 36:
						returnedValue = new SmallObject(ArrayClass, low);
						for (int i = low - 1; i >= 0; i--)
							returnedValue.data[i] = stack[--stackTop];
						break;

					// [PRIMITIVE 41] open file for output
					case 41: {
						try {
							FileOutputStream of = new FileOutputStream(
									stack[--stackTop].toString()+".st");
							PrintStream ps = new PrintStream(of);
							returnedValue = new SmallJavaObject(
									stack[--stackTop], ps);
						} catch (IOException e) {
							throw new SmallException("I/O exception " + e,
									context);
						}
					}
						break;

					// [PRIMITIVE 42] open file for input
					case 42: {
						try {
							FileInputStream of = new FileInputStream(
									stack[--stackTop].toString());
							DataInput ps = new DataInputStream(of);
							returnedValue = new SmallJavaObject(
									stack[--stackTop], ps);
						} catch (IOException e) {
							throw new SmallException("I/O exception " + e,
									context);
						}
					}
						break;

					// [PRIMITIVE 43] write a string
					case 43: {
						try {
							PrintStream ps = (PrintStream) ((SmallJavaObject) stack[--stackTop]).value;
							ps.print(stack[--stackTop]);
						} catch (Exception e) {
							throw new SmallException("I/O exception " + e,
									context);
						}
					}
						break;

					// [PRIMITIVE 44] read a string
					case 44: {
						try {
							DataInput di = (DataInput) ((SmallJavaObject) stack[--stackTop]).value;
							String line = di.readLine();
							if (line == null) {
								--stackTop;
								returnedValue = nilObject;
							} else
								returnedValue = new SmallByteArray(
										stack[--stackTop], line);
						} catch (EOFException e) {
							returnedValue = nilObject;
						} catch (IOException f) {
							throw new SmallException("I/O exception " + f,
									context);
						}
					}
						break;

					// [PRIMITIVE 45] read a file
					case 45: {
						try {
							DataInput di = (DataInput) ((SmallJavaObject) stack[--stackTop]).value;
							String line = di.readLine();
							String answer = "";
							while (line != null) {
								answer = answer + line + "\n";
								line = di.readLine();
							}
							returnedValue = new SmallByteArray(
									stack[--stackTop], answer);
						} catch (EOFException e) {
							returnedValue = nilObject;
						} catch (IOException f) {
							throw new SmallException("I/O exception " + f,
									context);
						}
					}
						break;

					// [PRIMITIVE 50] integer into float
					case 50:
						low = ((SmallInt) stack[--stackTop]).value;
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new Double((double) low));
						break;

					// [PRIMITIVE 51] addition of float
					case 51: {
						double a = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						double b = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new Double(a + b));
					}
						break;

					// [PRIMITIVE 52] subtraction of float
					case 52: {
						double a = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						double b = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new Double(a - b));
					}
						break;

					// [PRIMITIVE 53] multiplication of float
					case 53: {
						double a = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						double b = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new Double(a * b));
					}
						break;

					// [PRIMITIVE 54] division of float
					case 54: {
						double a = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						double b = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new Double(a / b));
					}
						break;

					// [PRIMITIVE 55] less than test of float
					case 55: {
						double a = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						double b = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						returnedValue = (a < b) ? trueObject : falseObject;
					}
						break;

					// [PRIMITIVE 56] equality test of float
					case 56: {
						double a = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						double b = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						returnedValue = (a == b) ? trueObject : falseObject;
					}
						break;

					// [PRIMITIVE 57] float to int
					case 57: {
						double a = ((Double) ((SmallJavaObject) stack[--stackTop]).value)
								.doubleValue();
						returnedValue = newInteger((int) a);
					}
						break;

					// [PRIMITIVE 58] random float
					case 58:
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new Double(Math.random()));
						break;

					// [PRIMITIVE 59] print of float
					case 59:
						returnedValue = (SmallJavaObject) stack[--stackTop];
						returnedValue = new SmallByteArray(
								stack[--stackTop],
								String.valueOf(((Double) ((SmallJavaObject) returnedValue).value)
										.doubleValue()));
						break;

					// [PRIMITIVE 60] <SWING> make window
					case 60: { // make window
						JDialog jd = new JDialog();
						jd.setVisible(false);
						returnedValue = new SmallJavaObject(stack[--stackTop],
								jd);
					}
						break;

					// [PRIMITIVE 61] <SWING> Show/hide text window
					case 61: { // show/hide text window
						returnedValue = stack[--stackTop];
						SmallJavaObject jo = (SmallJavaObject) stack[--stackTop];
						if (returnedValue == trueObject)
							((JDialog) jo.value).setVisible(true);
						else
							((JDialog) jo.value).setVisible(false);
					}
						break;

					// [PRIMITIVE 62] <SWING> Set content pane
					case 62: { // set content pane
						SmallJavaObject tc = (SmallJavaObject) stack[--stackTop];
						returnedValue = stack[--stackTop];
						SmallJavaObject jd = (SmallJavaObject) returnedValue;
						((JDialog) jd.value).getContentPane().add(
								(Component) tc.value);
					}
						break;

					// [PRIMITIVE 63] <SWING> Set size
					case 63: // set size
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						returnedValue = stack[--stackTop];
						{
							SmallJavaObject wo = (SmallJavaObject) returnedValue;
							((JDialog) wo.value).setSize(low, high);
						}
						break;

					// [PRIMITIVE 64] <SWING> Add menu to window
					case 64: { // add menu to window
						SmallJavaObject menu = (SmallJavaObject) stack[--stackTop];
						returnedValue = stack[--stackTop];
						SmallJavaObject jo = (SmallJavaObject) returnedValue;
						JDialog jd = (JDialog) jo.value;
						if (jd.getJMenuBar() == null)
							jd.setJMenuBar(new JMenuBar());
						jd.getJMenuBar().add((JMenu) menu.value);
					}
						break;

					// [PRIMITIVE 65] <SWING> Set title
					case 65: { // set title
						SmallObject title = stack[--stackTop];
						returnedValue = stack[--stackTop];
						SmallJavaObject jd = (SmallJavaObject) returnedValue;
						((JDialog) jd.value).setTitle(title.toString());
					}
						break;

					// [PRIMITIVE 66] <SWING> Repaint window
					case 66: { // repaint window
						returnedValue = stack[--stackTop];
						SmallJavaObject jd = (SmallJavaObject) returnedValue;
						((JDialog) jd.value).repaint();
					}
						break;

					// [PRIMITIVE 67] <SWING> New MultiSplitPane
					case 67: {
//					    String layoutDef = "(COLUMN (ROW weight=1.0 left (COLUMN middle.top middle middle.bottom) right) bottom)";
						String layoutDef = stack[--stackTop].toString();
						MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);

						MultiSplitPane multiSplitPane = new MultiSplitPane();
						multiSplitPane.getMultiSplitLayout().setModel(modelRoot);
						// return here
						returnedValue = new SmallJavaObject(
								stack[--stackTop],
								multiSplitPane);
					}
						break;
							
					// [PRIMITIVE 68] <SWING> Add component to MultiSplitPane
					case 68: {
						String constraints = stack[--stackTop].toString();
						SmallJavaObject jc = (SmallJavaObject) stack[--stackTop];
						SmallJavaObject pane = (SmallJavaObject) stack[--stackTop];
						
						((MultiSplitPane) pane.value).add((Component) jc.value, constraints);
					}
						break;
							
					// [PRIMITIVE 70] <SWING> New label panel
					case 70: { // new label panel
						JLabel jl = new JLabel(stack[--stackTop].toString());
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new JScrollPane(jl));
					}
						break;

					// [PRIMITIVE 71] <SWING> New button
					case 71: { // new button
						final SmallObject action = stack[--stackTop];
						final JButton jb = new JButton(
								stack[--stackTop].toString());
						returnedValue = new SmallJavaObject(stack[--stackTop],
								jb);
						jb.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								new ActionThread(action, myThread).start();
							}
						});
					}
						break;

					// [PRIMITIVE 72] <SWING> New text line
					case 72: // new text line
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new JTextField());
						break;

					// [PRIMITIVE 73] <SWING> New text area
					case 73: // new text area
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new JScrollPane(new JTextArea()));
						break;

					// [PRIMITIVE 74] <SWING> New grid panel 
					case 74: { // new grid panel
						SmallObject data = stack[--stackTop];
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						JPanel jp = new JPanel();
						jp.setLayout(new GridLayout(low, high));
						for (int i = 0; i < data.data.length; i++)
							jp.add((Component) ((SmallJavaObject) data.data[i]).value);
						returnedValue = new SmallJavaObject(stack[--stackTop],
								jp);
					}
						break;

					// [PRIMITIVE 75] <SWING> New list panel
					case 75: { // new list panel
						final SmallObject action = stack[--stackTop];
						SmallObject data = stack[--stackTop];
						returnedValue = stack[--stackTop];
						final JList jl = new JList(data.data);
						jl.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
						returnedValue = new SmallJavaObject(returnedValue,
								new JScrollPane(jl));
						jl.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if ((!e.getValueIsAdjusting())
										&& (jl.getSelectedIndex() >= 0)) {
									new ActionThread(action, myThread, jl
											.getSelectedIndex() + 1).start();
									// new ActionThread(action,
									// myThread).start();
								}
							}
						});
					}
						break;

					// [PRIMITIVE 76] <SWING> New border panel
					case 76: { // new border panel
						JPanel jp = new JPanel();
						jp.setLayout(new BorderLayout());
						returnedValue = stack[--stackTop];
						if (returnedValue != nilObject)
							jp.add("Center",
									(Component) ((SmallJavaObject) returnedValue).value);
						returnedValue = stack[--stackTop];
						if (returnedValue != nilObject)
							jp.add("West",
									(Component) ((SmallJavaObject) returnedValue).value);
						returnedValue = stack[--stackTop];
						if (returnedValue != nilObject)
							jp.add("East",
									(Component) ((SmallJavaObject) returnedValue).value);
						returnedValue = stack[--stackTop];
						if (returnedValue != nilObject)
							jp.add("South",
									(Component) ((SmallJavaObject) returnedValue).value);
						returnedValue = stack[--stackTop];
						if (returnedValue != nilObject)
							jp.add("North",
									(Component) ((SmallJavaObject) returnedValue).value);
						returnedValue = new SmallJavaObject(stack[--stackTop],
								jp);
					}
						break;

					// [PRIMITIVE 77] <SWING> Set image on label
					case 77: { // set image on label
						SmallJavaObject img = (SmallJavaObject) stack[--stackTop];
						SmallJavaObject lab = (SmallJavaObject) stack[--stackTop];
						Object jo = lab.value;
						if (jo instanceof JScrollPane)
							jo = ((JScrollPane) jo).getViewport().getView();
						if (jo instanceof JLabel) {
							JLabel jlb = (JLabel) jo;
							jlb.setIcon(new ImageIcon((Image) img.value));
							jlb.setHorizontalAlignment(SwingConstants.LEFT);
							jlb.setVerticalAlignment(SwingConstants.TOP);
							jlb.repaint();
						}
					}
						break;

					// [PRIMITIVE 79] <SWING> Repaint (component)
					case 79: {// repaint
						returnedValue = stack[--stackTop];
						SmallJavaObject jo = (SmallJavaObject) returnedValue;
						((JComponent) jo.value).repaint();
					}
						break;

					// [PRIMITIVE 80] <SWING> Get content of text area
					case 80: { // content of text area
						SmallJavaObject jt = (SmallJavaObject) stack[--stackTop];
						returnedValue = stack[--stackTop]; // class
						Object jo = jt.value;
						if (jo instanceof JScrollPane)
							jo = ((JScrollPane) jo).getViewport().getView();
						if (jo instanceof JTextComponent)

							returnedValue = new SmallByteArray(returnedValue,
									((JTextComponent) jo).getText());
						else
							returnedValue = new SmallByteArray(returnedValue,
									"");
					}
						break;

					// [PRIMITIVE 81] <SWING> Get selection in text area						
					case 81: {// content of selected text area
						SmallJavaObject jt = (SmallJavaObject) stack[--stackTop];
						returnedValue = stack[--stackTop]; // class
						Object jo = jt.value;
						if (jo instanceof JScrollPane)
							jo = ((JScrollPane) jo).getViewport().getView();
						if (jo instanceof JTextComponent)

							returnedValue = new SmallByteArray(returnedValue,
									((JTextComponent) jo).getSelectedText());
						else
							returnedValue = new SmallByteArray(returnedValue,
									"");
					}
						break;

					// [PRIMITIVE 82] <SWING> Set text area's contents
					case 82: { // set text area
						returnedValue = stack[--stackTop];// text
						SmallJavaObject jt = (SmallJavaObject) stack[--stackTop];
						Object jo = jt.value;
						if (jo instanceof JScrollPane)
							jo = ((JScrollPane) jo).getViewport().getView();
						if (jo instanceof JTextComponent)
							((JTextComponent) jo).setText(returnedValue
									.toString());
					}
						break;

					// [PRIMITIVE 83] <SWING> Get selected index
					case 83: { // get selected index
						SmallJavaObject jo = (SmallJavaObject) stack[--stackTop];
						Object jl = jo.value;
						if (jl instanceof JScrollPane)
							jl = ((JScrollPane) jl).getViewport().getView();
						if (jl instanceof JList)
							returnedValue = newInteger(((JList) jl)
									.getSelectedIndex() + 1);
						else if (jl instanceof JScrollBar)
							returnedValue = newInteger(((JScrollBar) jl)
									.getValue());
						else
							returnedValue = newInteger(0);
					}
						break;

					// [PRIMITIVE 84] <SWING> Set list data
					case 84: { // set list data
						SmallObject data = stack[--stackTop];
						returnedValue = stack[--stackTop];
						SmallJavaObject jo = (SmallJavaObject) returnedValue;
						Object jl = jo.value;
						if (jl instanceof JScrollPane)
							jl = ((JScrollPane) jl).getViewport().getView();
						if (jl instanceof JList) {
							((JList) jl).setListData(data.data);
							((JList) jl).repaint();
						}
					}
						break;

					// [PRIMITIVE 85] <SWING> New slider
					case 85: { // new slider
						final SmallObject action = stack[--stackTop];
						int max = ((SmallInt) stack[--stackTop]).value + 10; // why?
						int min = ((SmallInt) stack[--stackTop]).value;
						SmallObject orient = stack[--stackTop];
						final JScrollBar bar = new JScrollBar(
								((orient == trueObject) ? JScrollBar.VERTICAL
										: JScrollBar.HORIZONTAL), min, 10, min,
								max);
						returnedValue = new SmallJavaObject(stack[--stackTop],
								bar);
						if (action != nilObject)
							bar.addAdjustmentListener(new AdjustmentListener() {
								public void adjustmentValueChanged(
										AdjustmentEvent ae) {
									new ActionThread(action, myThread, ae
											.getValue()).start();
								}
							});
					}
						break;

					// [PRIMITIVE 86] <SWING> Set block to be executed onMouseDown 
					case 86: { // onMouseDown b
						final SmallObject action = stack[--stackTop];
						SmallJavaObject pan = (SmallJavaObject) stack[--stackTop];
						Object jo = pan.value;
						if (jo instanceof JScrollPane)
							jo = (JComponent) ((JScrollPane) jo).getViewport()
									.getView();
						final JComponent jpan = (JComponent) jo;
						jpan.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								new ActionThread(action, myThread, e.getX(), e
										.getY()).start();
							}
						});
					}
						break;

					// [PRIMITIVE 87] <SWING> Set block to be executed onMouseUp 
					case 87: { // onMouseUp b
						final SmallObject action = stack[--stackTop];
						SmallJavaObject pan = (SmallJavaObject) stack[--stackTop];
						Object jo = pan.value;
						if (jo instanceof JScrollPane)
							jo = (JComponent) ((JScrollPane) jo).getViewport()
									.getView();
						final JComponent jpan = (JComponent) jo;
						jpan.addMouseListener(new MouseAdapter() {
							public void mouseReleased(MouseEvent e) {
								new ActionThread(action, myThread, e.getX(), e
										.getY()).start();
							}
						});
					}
						break;

					// [PRIMITIVE 88] <SWING> Set block to be executed onMouseMove
					case 88: { // onMouseMove b
						final SmallObject action = stack[--stackTop];
						SmallJavaObject pan = (SmallJavaObject) stack[--stackTop];
						Object jo = pan.value;
						if (jo instanceof JScrollPane)
							jo = (JComponent) ((JScrollPane) jo).getViewport()
									.getView();
						final JComponent jpan = (JComponent) jo;
						jpan.addMouseMotionListener(new MouseMotionAdapter() {
							public void mouseDragged(MouseEvent e) {
								new ActionThread(action, myThread, e.getX(), e
										.getY()).start();
							}

							public void mouseMoved(MouseEvent e) {
								new ActionThread(action, myThread, e.getX(), e
										.getY()).start();
							}
						});
					}
						break;

					// [PRIMITIVE 89] <SWING> Set/Replace selection in text area  
					case 89: { // set selected text area
						returnedValue = stack[--stackTop];// text
						SmallJavaObject jt = (SmallJavaObject) stack[--stackTop];
						Object jo = jt.value;
						if (jo instanceof JScrollPane)
							jo = ((JScrollPane) jo).getViewport().getView();
						if (jo instanceof JTextComponent)
							((JTextComponent) jo)
									.replaceSelection(returnedValue.toString());
					}
						break;

					// [PRIMITIVE 90] <SWING> New menu 
					case 90: { // new menu
						SmallObject title = stack[--stackTop]; // text
						returnedValue = stack[--stackTop]; // class
						JMenu menu = new JMenu(title.toString());
						returnedValue = new SmallJavaObject(returnedValue, menu);
					}
						break;

					// [PRIMITIVE 91] <SWING> New menu item 
					case 91: { // new menu item
						final SmallObject action = stack[--stackTop];
						final SmallObject text = stack[--stackTop];
						returnedValue = stack[--stackTop];
						SmallJavaObject mo = (SmallJavaObject) returnedValue;
						JMenu menu = (JMenu) mo.value;
						JMenuItem ji = new JMenuItem(text.toString());
						ji.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								new ActionThread(action, myThread).start();
							}
						});
						menu.add(ji);
					}
						break;

					// [PRIMITIVE 100] new semaphore
					case 100:
						returnedValue = new SmallJavaObject(stack[--stackTop],
								new Sema());
						break;

					// [PRIMITIVE 101] semaphore wait
					case 101: {
						SmallJavaObject jo = (SmallJavaObject) stack[--stackTop];
						returnedValue = ((Sema) jo.value).get();
					}
						break;

					// [PRIMITIVE 102] semaphore set
					case 102: {
						returnedValue = stack[--stackTop];
						SmallJavaObject jo = (SmallJavaObject) stack[--stackTop];
						((Sema) jo.value).set(returnedValue);
					}
						break;

					// [PRIMITIVE 110] <SWING> New image 
					case 110: { // new image
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						Image img = new BufferedImage(low, high,
								BufferedImage.TYPE_INT_RGB);
						Graphics g = img.getGraphics();
						g.setColor(Color.white);
						g.fillRect(0, 0, low, high);
						returnedValue = new SmallJavaObject(stack[--stackTop],
								img);
					}
						break;

					// [PRIMITIVE 111] <SWING>  New image from file
					case 111: { // new image from file
						SmallByteArray title = (SmallByteArray) stack[--stackTop];
						returnedValue = stack[--stackTop];
						Image img = null;
						try {
							img = Toolkit.getDefaultToolkit().getImage(
									title.toString());
							System.out.println("got image " + img);
							// MediaTracker mt = new MediaTracker(null);
							// mt.addImage(img, 0);
							// mt.waitForID(0);
						} catch (Exception e) {
							System.out.println("image read got exception " + e);
						}
						low = img.getWidth(null);
						high = img.getHeight(null);
						BufferedImage bi = new BufferedImage(low, high,
								BufferedImage.TYPE_INT_RGB);
						Graphics g = bi.createGraphics();
						g.drawImage(img, 0, 0, null);
						returnedValue = new SmallJavaObject(returnedValue, bi);
					}
						break;

					// [PRIMITIVE 113] <SWING> Draw image
					case 113: { // draw image
						SmallJavaObject img2 = (SmallJavaObject) stack[--stackTop];
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						SmallJavaObject img = (SmallJavaObject) stack[--stackTop];
						Graphics g = ((Image) img.value).getGraphics();
						g.drawImage((Image) img2.value, low, high, null);
					}
						break;

					// [PRIMITIVE 114] <SWING> Draw text
					case 114: { // draw text
						SmallByteArray text = (SmallByteArray) stack[--stackTop];
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						int c = ((SmallInt) stack[--stackTop]).value;
						SmallJavaObject img = (SmallJavaObject) stack[--stackTop];
						Graphics g = ((Image) img.value).getGraphics();
						g.setColor(new Color(c));
						g.drawString(text.toString(), low, high);
					}
						break;

					// [PRIMITIVE 115] <SWING> Draw/fill circle/rectangle/line
					case 115: { // draw/fill circle
						int s = ((SmallInt) stack[--stackTop]).value;
						int h = ((SmallInt) stack[--stackTop]).value;
						int w = ((SmallInt) stack[--stackTop]).value;
						low = ((SmallInt) stack[--stackTop]).value;
						high = ((SmallInt) stack[--stackTop]).value;
						int c = ((SmallInt) stack[--stackTop]).value;
						SmallJavaObject img = (SmallJavaObject) stack[--stackTop];
						Graphics g = ((Image) img.value).getGraphics();
						g.setColor(new Color(c));
						switch (s) {
						case 1:
							g.drawOval(low, high, h, w);
							break;
						case 2:
							g.fillOval(low, high, h, w);
							break;
						case 3:
							g.drawRect(low, high, h, w);
							break;
						case 4:
							g.fillRect(low, high, h, w);
							break;
						case 5:
							g.drawLine(low, high, h, w);
							break;
						}
					}
						break;

					// {PRIMITIVE 116} SmallWorld 2007 Save-to-JAR
					// case 116: { // image save
					// try {
					// // We have a problem - java won't allow us to amend a
					// jar/zip file
					// // even though the jar tool does it without problems.
					// //
					// // So we must read all the files in the jar file (except
					// 'image' of course)
					// // one by one into a new jar file, and then swap it for
					// the old one.
					// //
					// // This works because the vm+image is still quite small.
					// //
					// // This is a lot of effort, but libraries which do this
					// for us are
					// // relatively large (TrueZip, for example, is over 350K)
					// and would require
					// // us to take on board even more complexity if we were to
					// deploy in a single
					// // jar file (which is, after all, the point of the
					// exercise!)
					//
					// // First off, get name of jar
					// String nameOfJar =
					// this.getClass().getClassLoader().getSystemResource("image").getFile();
					// nameOfJar = nameOfJar.substring(5, nameOfJar.length()-7);
					// JarFile jar = new JarFile(nameOfJar);
					//
					// // Create temp file and access existing file
					//
					// File tempJar = null;
					// JarOutputStream newJar = null;
					// tempJar = File.createTempFile("tempJarFile", null);
					// newJar = new JarOutputStream(new
					// FileOutputStream(tempJar));
					// // } catch (Exception e)
					// {JOptionPane.showMessageDialog(new JFrame("X"), "1");}
					//
					// byte buffer[] = new byte[1024];
					// int bytesRead;
					//
					// // Add back the original files, except image
					// Enumeration entries = jar.entries();
					// while (entries.hasMoreElements()) {
					// JarEntry entry = (JarEntry) entries.nextElement();
					// if (entry.getName().contentEquals("image") == false) {
					// InputStream is = jar.getInputStream(entry);
					// newJar.putNextEntry(entry);
					// while ((bytesRead = is.read(buffer)) != -1) {
					// newJar.write(buffer, 0, bytesRead);
					// }}
					// }
					//
					// // Add new file last
					// JarEntry entry = new JarEntry("image");
					// newJar.putNextEntry(entry);
					// saveImageToOutputStream(newJar);
					// newJar.close();
					// jar.close();
					//
					//
					// File origFile = new File(nameOfJar);
					// origFile.delete();
					// tempJar.renameTo(origFile);
					//
					// returnedValue = trueObject;
					// } catch (Exception e) {throw new
					// SmallException("I/O exception " + e, context);}
					// } break;

					// [PRIMITIVE 117] Print string on the output stream
					case 117: {
						System.out.println(stack[--stackTop].toString());
						returnedValue = trueObject;
					}
						break;

					// [PRIMITIVE 118] <SWING> Set block to execute onWindowClose
					case 118: { // onWindow close b
						try {
							final SmallObject action = stack[--stackTop];
							SmallJavaObject pan = (SmallJavaObject) stack[--stackTop];
							JDialog jo = (JDialog) pan.value;
							jo.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
									new ActionThread(action, myThread).start();
								}
							});
						} catch (Exception e) {
							throw new SmallException("Exception: "
									+ e.toString(), context);
						}
					}
						break;
						
					// [PRIMITIVE 119] Throw an exception
					case 119: {
						String msg = stack[--stackTop].toString();
						throw new SmallException(msg, context);
					}

					// [PRIMITIVE 120] Get a value from the Java environment
					//
					case 120: {
						String key = stack[--stackTop].toString();
						returnedValue = javaEnvironment.get(key);
						// for(Map.Entry<String,SmallByteArray> each:
						// javaEnvironment.entrySet())
						// System.out.println("...==>" + each);

						if (returnedValue == null) {
							returnedValue = this.falseObject;
						}
					}
						break;

					// [PRIMITIVE 121] Reify a Java class
					case 121: {
						String javaClassName = stack[--stackTop].toString();
						SmallObject cls = stack[--stackTop];
						try {
							ClassLoader cl = SmallInterpreter.class
									.getClassLoader();
							returnedValue = new SmallJavaObject(cls,
									cl.loadClass(javaClassName));
						} catch (Exception e) {
							System.err.println(e);
							returnedValue = this.createGossipString(e
									.toString());
						}
					}
						break;

					// [PRIMITIVE 122] Invoke a NON-STATIC method on a Java
					// Object
					case 122: {
						SmallObject argsAthena = stack[--stackTop];
						// System.out.println(argsAthena.data.length);
						SmallObject[] args = argsAthena.data;

						String methodName = stack[--stackTop].toString();
						SmallJavaObject receiver = (SmallJavaObject) stack[--stackTop];

						Object javaObject = receiver.value;
						try {
							Class cls = javaObject.getClass();
							// Method mtd = cls.getMethod(methodName);
							Method mtd = null;
							Method[] methods = cls.getMethods();
							for (Method m : methods) {
								if (m.getName().equals(methodName)) {
									mtd = m;
									break;
								}
							}

							/*
							 * if (argsAthena.data.length > 0){
							 * System.out.println
							 * (((SmallJavaObject)argsAthena.data
							 * [0]).value.getClass()); }
							 */

							Object result;
							if (argsAthena.data.length == 0)
								result = mtd.invoke(javaObject);
							else if (args.length == 1)
								result = mtd.invoke(javaObject,
										((SmallJavaObject) args[0]).value);
							else if (args.length == 2)
								result = mtd.invoke(javaObject,
										((SmallJavaObject) args[0]).value,
										((SmallJavaObject) args[1]).value);
							else if (args.length == 3)
								result = mtd.invoke(javaObject,
										((SmallJavaObject) args[0]).value,
										((SmallJavaObject) args[1]).value,
										((SmallJavaObject) args[2]).value);
							else
								result = mtd.invoke(javaObject);
							// System.out.println("to send: "+result);
							if (result == null) {
								result = "null";
							} // this happen if the method return void
							returnedValue = new SmallJavaObject(
									receiver.objClass, result);
						} catch (Exception e) {
							System.err.println(e);
							returnedValue = this.createGossipString(e
									.toString());
						}
					}
						break;

					// [PRIMITIVE 123] Return a string that represent the Java
					// Object
					case 123: {
						SmallJavaObject receiver = (SmallJavaObject) stack[--stackTop];
						Object javaObject = receiver.value;
						returnedValue = this.createGossipString(javaObject
								.toString());
					}
						break;

					// [PRIMITIVE 124] Invoke a STATIC method on a Java Class
					//
					case 124: {
						String methodName = stack[--stackTop].toString();
						SmallJavaObject receiver = (SmallJavaObject) stack[--stackTop];
						if (!(receiver.value instanceof Class)) {
							// The object receiver should be a class
							returnedValue = this
									.createGossipString("ERROR: Receiver "
											+ receiver.value
											+ " is not a class");
						}

						Class javaClass = (Class) receiver.value;
						try {
							Method mtd = javaClass.getMethod(methodName);
							Object result = mtd.invoke(null); // Not need of
																// receiver
							if (result == null) {
								result = "null";
							} // this happen if the method return void
							returnedValue = new SmallJavaObject(
									receiver.objClass, result);
						} catch (Exception e) {
							System.err.println(e);
							returnedValue = this.createGossipString(e
									.toString());
						}
					}
						break;

					// [PRIMITIVE 125] Set the current namespace
					case 125: {
						SmallObject newNamespace = (SmallObject) stack[--stackTop];
						this.namespace = newNamespace;
						returnedValue = this.trueObject;
					}
						break;

					// [PRIMITIVE 126] Get the current namespace
					case 126: {
						// This is ugly, has to be changed
						if (this.namespace == null) {
							this.namespace = this.nilObject;
						}
						returnedValue = this.namespace;
					}
						break;

					// case 127: { // Invoke a method with some arguments
					// } break;

					// [PRIMITIVE 128] Convert a SmallJavaObject into a SmallInt (??)
					//
					case 128: {
						SmallInt smallInt = (SmallInt) stack[--stackTop];
						SmallObject javaObjectClass = stack[--stackTop];
						returnedValue = new SmallJavaObject(javaObjectClass,
								smallInt.value);
					}
						break;

					// [PRIMITIVE 129] Convert a SmallJavaObject into a SmallInt
					//
					case 129: {
						SmallJavaObject obj = (SmallJavaObject) stack[--stackTop];
						returnedValue = new SmallInt(this.IntegerClass,
								(java.lang.Integer) obj.value);
					}
						break;

					// [PRIMITIVE 130] Convert a SmallString into a Java String
					//
					case 130: {
						SmallByteArray smallString = (SmallByteArray) stack[--stackTop];
						SmallObject javaObjectClass = stack[--stackTop];
						returnedValue = new SmallJavaObject(javaObjectClass,
								smallString.toString());
					}
						break;

					// [PRIMITIVE 131] Convert a SmallJavaObject String into a SmallString
					case 131: {
						SmallJavaObject obj = (SmallJavaObject) stack[--stackTop];
						SmallObject stringClass = stack[--stackTop];
						returnedValue = new SmallByteArray(stringClass,
								(String) obj.value);
					}
						break;

					// [PRIMITIVE 132] Quit without saving
					case 132: {
						System.exit(0);
					}
						break;

					// [PRIMITIVE 133] get current value of the millisecond clock
					case 133: {
						returnedValue = newInteger((int) java.lang.System
								.currentTimeMillis());
					}
						break;

					// [PRIMITIVE 134] Attach an action to a keystroke
					// <134 self "control D" [ self doIt ]>
					case 134: {
						final SmallObject action = stack[--stackTop];
						String keyStrokeAndKey = stack[--stackTop].toString();

						SmallJavaObject jt = (SmallJavaObject) stack[--stackTop];
						Object jo = jt.value;

						if (jo instanceof JScrollPane)
							jo = ((JScrollPane) jo).getViewport().getView();
						
						if (jo instanceof JTextComponent) {
								JTextComponent component = (JTextComponent) jo;
								Action javaAction = new AbstractAction() {
									private static final long serialVersionUID = 6271727413361133775L;

									@Override
									public void actionPerformed(ActionEvent e) {
											new ActionThread(action, myThread).start();										
									} };
									
								KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
								component.getInputMap().put(keyStroke, keyStrokeAndKey);
								component.getActionMap().put(keyStrokeAndKey, javaAction);
						}
					}
						break;

					// [PRIMITIVE 142] open file for input <br>
					case 142: {
						InputStream of = SmallInterpreterInterfacer.ReadInjarFile(stack[--stackTop].toString());
						DataInput ps = new DataInputStream(of);
						returnedValue = new SmallJavaObject(stack[--stackTop], ps);	
					}
						break;

					// [PRIMITIVE 150] Attach an InputListener to a Reactor
					case 150: {
							final SmallObject action = stack[--stackTop];
							Reactor reactor =  (Reactor) ((SmallJavaObject) stack[--stackTop]).value;						
						
							ActionThread thread = new ActionThread(action, myThread);
							ContextedFactory.instance().getContextedItem(ObjectPrototype.EventListener, EventType.onClick, (Reactor)reactor,(ActionThread)thread);
					}
						break;

    				// END OF PRIMITIVES
					default:
						throw new SmallException("Unknown Primitive " + high,
								context);
					}
					
					stack[stackTop++] = returnedValue;
					break;

					
				// [BYTECODE 14] PushClassVariable
				case 14:
					if (arguments == null)
						arguments = contextData[1];
					if (instanceVariables == null)
						instanceVariables = arguments.data[0].data;
					stack[stackTop++] = arguments.data[0].objClass.data[low + 5];
					break;

				// [BYTECODE 15] Do Special
				case 15:
					switch (low) {
					case 1: // self return
						if (arguments == null)
							arguments = contextData[1];
						returnedValue = arguments.data[0];
						context = contextData[6]; // previous context
						break innerLoop;

					case 2: // stack return
						returnedValue = stack[--stackTop];
						context = contextData[6]; // previous context
						break innerLoop;

					case 3: // block return
						returnedValue = stack[--stackTop];
						context = contextData[8]; // creating context in block
						context = context.data[6]; // previous context
						break innerLoop;

					case 4: // duplicate
						returnedValue = stack[stackTop - 1];
						stack[stackTop++] = returnedValue;
						break;

					case 5: // pop top
						stackTop--;
						break;

					case 6: // branch
						low = (int) code[bytePointer++] & 0x0FF;
						bytePointer = low;
						break;

					case 7: // branch if true
						low = (int) code[bytePointer++] & 0x0FF;
						returnedValue = stack[--stackTop];
						if (returnedValue == trueObject)
							bytePointer = low;
						break;

					case 8: // branch if false
						low = (int) code[bytePointer++] & 0x0FF;
						returnedValue = stack[--stackTop];
						if (returnedValue == falseObject)
							bytePointer = low;
						break;

					case 11: // send to super
						low = (int) code[bytePointer++] & 0x0FF;
						// message selector
						// save old context
						arguments = stack[--stackTop];
						contextData[5] = newInteger(stackTop);
						contextData[4] = newInteger(bytePointer);
						// now build new context
						if (literals == null)
							literals = method.data[2].data;
						if (method == null)
							method = context.data[0];
						method = method.data[5]; // class in method
						method = method.data[1]; // parent in class
						method = methodLookup(method,
								(SmallByteArray) literals[low], context,
								arguments);
						context = buildContext(context, arguments, method);
						contextData = context.data;
						// load information from context
						continue outerLoop;

					default: // throw exception
						throw new SmallException(
								"Unrecogized DoSpecial " + low, context);
					}
					break;

				default: // throw exception
					throw new SmallException("Unrecogized opCode " + low,
							context);
				}
			} // end of inner loop

			if ((context == null) || (context == nilObject)) {
				// System.out.println("lookups " + lookup + " cached " +
				// cached);
				return returnedValue;
			}
			contextData = context.data;
			stack = contextData[3].data;
			stackTop = ((SmallInt) contextData[5]).value;
			stack[stackTop++] = returnedValue;
			contextData[5] = newInteger(stackTop);
		}
	} // end of outer loop

	public class ActionThread extends Thread {
		public ActionThread(SmallObject block, Thread myT) {
			myThread = myT;
			action = new SmallObject(ContextClass, 10);
			for (int i = 0; i < 10; i++)
				action.data[i] = block.data[i];
		}

		public ActionThread(SmallObject block, Thread myT, int v1) {
			myThread = myT;
			action = new SmallObject(ContextClass, 10);
			for (int i = 0; i < 10; i++)
				action.data[i] = block.data[i];
			int argLoc = ((SmallInt) action.data[7]).value;
			action.data[2].data[argLoc] = newInteger(v1);
		}

		public ActionThread(SmallObject block, Thread myT, int v1, int v2) {
			myThread = myT;
			action = new SmallObject(ContextClass, 10);
			for (int i = 0; i < 10; i++)
				action.data[i] = block.data[i];
			int argLoc = ((SmallInt) action.data[7]).value;
			action.data[2].data[argLoc] = newInteger(v1);
			action.data[2].data[argLoc + 1] = newInteger(v2);
		}

		private SmallObject action;
		private Thread myThread;

		public void run() {
			int stksize = action.data[3].data.length;
			action.data[3] = new SmallObject(ArrayClass, stksize); // new stack
			action.data[4] = action.data[9]; // byte pointer
			action.data[5] = newInteger(0); // stack top
			action.data[6] = nilObject;
			try {
				execute(action, this, myThread);
			} catch (Exception e) {
				System.out.println("caught exception " + e);
			}
		}
	}

}
