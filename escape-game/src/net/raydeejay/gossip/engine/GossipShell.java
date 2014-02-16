package net.raydeejay.gossip.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * The Athena Shell This class is meant to be run from the command line. For a
 * list of available options
 * <code>java -cp athena-0.1.jar net.raydeejay.gossip.engine.GossipShell -?</code>
 * <br>
 * 
 * @author Alexandre Bergel (<a
 *         href="mailto:alexandre@bergel.eu">alexandre@bergel.eu</a>)
 */
public class GossipShell {
	private boolean verbose = false;
	private boolean interaction = false;
	private String imageName = "Gossip.image";
	private String fileName = null;
	private String expressionToEvaluate = null;

	private GossipScriptFactory factory;
	private ScriptEngine engine;

	/** experimental */
	private boolean newSyntax = false;

	private <T> LinkedList<T> arrayToLinkedList(T[] array) {
		LinkedList<T> t = (LinkedList<T>) new LinkedList();
		for (T each : array) {
			t.addLast(each);
		}
		return t;
	}

	private void printOnScreen(Object obj) {
		System.out.println(obj);
	}

	private void displayIntro() {
		this.printOnScreen("_,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,");
		this.printOnScreen("|\t\t" + engine.get("ENGINE") + " Server"
				+ "\t\t\t|");
		this.printOnScreen("|\t\t\t" + "Version "
				+ engine.get("ENGINE_VERSION") + "\t\t\t|");
		this.printOnScreen("|\t      " + "Enter any Smalltalk expression"
				+ "\t\t|");
		this.printOnScreen("|\t"
				+ "You may also use \"help\", \"license\" and \"quit\"" + "\t|");
		this.printOnScreen("^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~");
	}

	private void displayHelp() {
		this.printOnScreen("_,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:");
		this.printOnScreen("Athena comprises a set of tools to use the Smalltalk");
		this.printOnScreen("programming language in an embedded setting. Athena");
		this.printOnScreen("offers a small virtual machine written in Java and");
		this.printOnScreen("a set of tool to program Athena from Squeak, an ");
		this.printOnScreen("open Smalltalk dialect.");
		this.printOnScreen("");
		this.displayArgumentList();
		this.printOnScreen("");
		this.printOnScreen("website: http://bergel.eu/athena");
		this.printOnScreen("contact: alexandre@bergel.eu");
		this.printOnScreen("_,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:");
	}

	private void displayLicense() {
		this.printOnScreen("_,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:");
		this.printOnScreen("The MIT License");
		this.printOnScreen("");
		this.printOnScreen("Copyright (c) 2007 Alexandre Bergel (all packages plus part of net.raydeejay.gossip.engine.interpreter)");
		this.printOnScreen("Copyright (c) 1984, 2002, 2004, Timothy A. Budd (part of net.raydeejay.gossip.engine.interpreter)");
		this.printOnScreen("");
		this.printOnScreen("Permission is hereby granted, free of charge, to any person obtaining a copy");
		this.printOnScreen("of this software and associated documentation files (the \"Software\"), to deal");
		this.printOnScreen("in the Software without restriction, including without limitation the rights");
		this.printOnScreen("to use, copy, modify, merge, publish, distribute, sublicense, and/or sell");
		this.printOnScreen("copies of the Software, and to permit persons to whom the Software is");
		this.printOnScreen("furnished to do so, subject to the following conditions:");
		this.printOnScreen("");
		this.printOnScreen("The above copyright notice and this permission notice shall be included in");
		this.printOnScreen("all copies or substantial portions of the Software.");
		this.printOnScreen("");
		this.printOnScreen("THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR");
		this.printOnScreen("IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,");
		this.printOnScreen("FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE");
		this.printOnScreen("AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER");
		this.printOnScreen("LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,");
		this.printOnScreen("OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN");
		this.printOnScreen("THE SOFTWARE.");
		this.printOnScreen("_,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:");
	}

	private void quitAthena() {
		this.display("bye!");
		System.exit(0);
	}

	private String readLine() {
		// Java 6: System.console().readLine();
		try {
			BufferedReader d = new BufferedReader(new InputStreamReader(
					System.in));
			return d.readLine();
		} catch (IOException ex) {
			System.err.println(ex);
			return "";
		}
	}

	private void enterInteractiveMode() {
		String userInput;
		this.displayIntro();

		while (true) {
			System.out.print("owl: ");
			Boolean isKeyword = false;
			userInput = this.readLine();
			if (userInput == null || userInput.equals("quit")) {
				quitAthena();
				isKeyword = true;
			}
			if (userInput.equals("help")) {
				displayHelp();
				isKeyword = true;
			}
			if (userInput.equals("license")) {
				displayLicense();
				isKeyword = true;
			}

			if (!isKeyword) {
				try {
					this.printOnScreen(engine.eval("[" + userInput
							+ "] value printString"));
				} catch (ScriptException e) {
					System.err.println(e);
				}
			}
		}
	}

	private void parserArguments(String[] args) {
		for (Iterator<String> i = this.arrayToLinkedList(args).iterator(); i
				.hasNext();) {
			String s = (String) i.next();

			if (s.equals("-h") || s.equals("-?") || s.equals("?")
					|| s.equals("help"))
				this.displayHelpAndQuit();
			if (s.charAt(0) != '-')
				imageName = s;
			if (s.equals("-i"))
				interaction = true;
			if (s.equals("-v"))
				verbose = true;
			if (s.equals("-f"))
				fileName = (String) i.next();
			if (s.equals("-newSyntax"))
				newSyntax = true;
			if (s.equals("-e"))
				expressionToEvaluate = (String) i.next();
		}

		if (imageName == null) {
			imageName = "Gossip.image";
		}
	}

	public GossipShell() {
		this(null);
	}

	public GossipShell(String[] args) {
		if (args != null)
			this.parserArguments(args);

		this.display("imageName: " + imageName);

		// String className = (String)(l.toArray() [l.indexOf(imageName) + 1]);

		factory = new GossipScriptFactory();
		factory.setImageName(imageName);
		if (verbose)
			factory.setVerbose(true);
		factory.setDisplay("shell");
		engine = factory.getScriptEngine();

		// Evaluate a file
		if (fileName != null & !newSyntax) {
			try {
				this.display("reading file: " + fileName);
				engine.eval("File fileIn: '" + fileName + "'");
			} catch (ScriptException e) {
				System.err.println("ERROR :" + e);
			}
		}

		// Evaluate a file with a new syntax
		if (fileName != null & newSyntax) {
			this.evalFileWithNewSyntax();
		}

		if (expressionToEvaluate != null) {

			evalExpression(expressionToEvaluate);
		}

		if (interaction)
			this.enterInteractiveMode(); // Terminate the VM when done
	}

	public Object evalExpression(String expr) {
		try {
			this.display("Evaluating expression: " + expr);
			return engine.eval(expr);
		} catch (ScriptException e) {
			System.err.println("ERROR when evaluating an expression:" + e);
		}
		// The method should throw an exception rather to return null
		return null;
	}

	/**
	 * Eval fileName using the new syntax
	 * 
	 * Example of the new syntax: Object subclass: #MyClass. MyClass>>foo {
	 * Transcript show: 'Hello!'. }
	 * 
	 * 
	 * 
	 * class MyClass { foo {Transcript show: 'Hello!'.}} class MyClass2 extends
	 * MyClass { add: aNumber with: aSecondNumber {^ aNumber + aSecondNumber} }
	 */
	public void evalStringWithNewSyntax(String content) {
		buffer = content;
		try {
			// while(true)
			{
				String token = this.nextToken();
				if (token.equals("class")) {
					String className = this.nextToken();
					this.display("For class " + className);
					String t = this.nextToken();

					engine.eval("Class addNewClass: (Object subclass: '"
							+ className + "' variables: '' classVariables: '')");

					// We now have to read methods
					if (t.equals("{")) {
						while (!this.isClosingCurlyBracket()) {
							String methodName = readMethodName();
							this.display("  methodName " + methodName);
							this.readOpenCurlyBracket();
							String methodBody = this.untilEndOfMethod();
							this.display("For class " + className
									+ " methods named: " + methodName
									+ "with body" + methodBody);
							engine.eval(className + " compileMethod: '"
									+ methodName + "   " + methodBody + "'");
						}
					}
				}
			}

		} catch (ScriptException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (Exception e) {
			System.err.println("ERROR :" + e);
			e.printStackTrace();
		}
	}

	private String readMethodName() {
		String methodName = "";

		String tt = this.nextToken();
		methodName += (tt + " ");
		while (!this.isOpenCurlyBracket()) {
			tt = this.nextToken();
			methodName += (tt + " ");
		}
		return methodName;
	}

	private boolean isClosingCurlyBracket() {
		return buffer.trim().charAt(0) == '}';
	}

	private boolean isOpenCurlyBracket() {
		return buffer.trim().charAt(0) == '{';
	}

	private void readOpenCurlyBracket() {
		buffer = buffer.trim().substring(1);
	}

	private void readClosingCurlyBracket() {
		buffer = buffer.trim().substring(1);
	}

	private void evalFileWithNewSyntax() {
		this.display("reading file using the new syntax: " + fileName);

		try {
			this.evalStringWithNewSyntax(readfile(fileName));
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
	}

	public static String readfile(String filename) throws IOException {
		RandomAccessFile file = new RandomAccessFile(filename, "r");
		int rem = (int) file.length();
		if (rem > 1000000)
			throw new IOException("file exceeds 1MB");

		byte[] b = new byte[rem];
		while (rem > 0)
			rem -= file.read(b, b.length - rem, rem);
		file.close();

		return new String(b);
	}

	private String untilEndOfMethod() {
		String res = "";
		StringReader sr = new StringReader(buffer);
		char c = 0;
		int nbOfCharRead = 0;
		try {
			c = (char) sr.read();
			nbOfCharRead++;
			while (c != '}') {
				res += c;
				c = (char) sr.read();
				nbOfCharRead++;
			}
			buffer = buffer.substring(nbOfCharRead);
			return res;

		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		return "ERROR";
	}

	private String buffer;

	private String nextToken() {
		// assert buffer != null;
		// this.display("DEB: " + buffer);
		// assert this.hasToken();

		// this.display("DEBUG " + buffer.split("[{\\s]"));
		String token = (buffer.trim().split("\\s"))[0];
		if (token.charAt(0) == '{') {
			buffer = buffer.trim().substring(1).trim();
			return "{";
		}
		buffer = buffer.trim().substring(token.length()).trim();
		return token;
	}

	private boolean hasToken() {
		return buffer.split("\\s").length > 0;
	}

	private void displayArgumentList() {
		String[] options = {
				"Usage: GossipShell [imageFile] [-h?vfi] [...]",
				"-h, -?, ?, help\t\tDisplay this help",
				"-v\t\t\tVerbose mode",
				"-f <filename>\t\tLoad and execute the provided file",
				"-i\t\t\tInteractive mode",
				"-e\t\t\tExpression to evaluate, e.g., -e \"<117 'Hello World'>\"",
				"-args\t\t\tRemaining argument are made accessible to the image",
				"-newSyntax\t\t\tNew syntax [EXPERIMENTAL]",
				"",
				"Note that the -f option is always treated before -e or -i. The -e option is treated before -i" };
		for (String s : options)
			System.out.println(s);
	}

	private void displayHelpAndQuit() {
		this.displayArgumentList();
		System.exit(0);
	}

	private void display(Object msg) {
		this.display((String) msg);
	}

	private void display(String msg) {
		if (verbose)
			System.out.println(msg);
	}

	public static void main(String[] args) {
		new GossipShell(args);
	}
}
