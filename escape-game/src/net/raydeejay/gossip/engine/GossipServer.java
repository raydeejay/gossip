/*
	Athena virtual machine. 
	Version 0.1 August 31, 2007
	Alexandre Bergel (alexandre@bergel.eu  http://www.bergel.eu)
	
	Based on Tim Budd's work with Little Smalltalk
*/

package net.raydeejay.gossip.engine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.raydeejay.gossip.engine.interpreter.SmallObject;

public class GossipServer{
	// 0: not verbose, 1: verbose, 2: extended verbose
	private int verbose = 0;
	private boolean interaction = false;
	private String imageName = "Gossip.image";

	private GossipScriptFactory factory;
	private ScriptEngine engine;
	
	private InputStream socketInputStream;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	private int nbOfBytesSent = 0;
	private int nbOfBytesReceived = 0;

	private void enterInteractiveMode() {
		String userInput, answer;
		//this.displayIntro();
		this.initConnection();
		int nb = 0;
		for (;;) {
			this.display("#"+ (nb++) + " - Waiting for input...", 2);
			Boolean isKeyword = false;			
			
			userInput = this.readFromSocket();
			if(userInput.equals("quit")) this.quitAthena();
			
			answer = null;
			try { 
				answer = ((SmallObject)engine.eval("["+ userInput + "] value printString")).toString();	
				this.display("will be sent... : " +answer, 2);
				this.sendToSocket(answer);
				this.display("... done!", 2);
			}
			catch(ScriptException e) {System.err.println(e); this.sendToSocket(e.toString());}
		}
	}
	
	private void sendToSocket(String msg) {
		this.nbOfBytesSent += msg.length();
		socketOut.println(msg);
	}

	private long tLastCommunication = 0;

	private String readFromSocket() {
		try{
			int nbOfBytesToBeRead = 0;
			do {
				// Whether more than 200 ms has elapsed since the last communication
				if ((System.currentTimeMillis() - tLastCommunication) > 200)
					try{Thread.sleep(75);}catch(Exception e) {}
					
				nbOfBytesToBeRead = socketInputStream.available();
			} while (nbOfBytesToBeRead == 0);

			tLastCommunication = System.currentTimeMillis();
			
			this.display("nb chars to read: " + nbOfBytesToBeRead, 2);

			char[] buf = new char[nbOfBytesToBeRead];
			socketIn.read(buf, 0, nbOfBytesToBeRead);
			String smalltalkExpression = new String(buf, 0, nbOfBytesToBeRead-1);

			this.display("what has been read: " + smalltalkExpression, 2);

			this.nbOfBytesReceived += nbOfBytesToBeRead;
			return smalltalkExpression;
		}
		catch(Exception e) {return this.readFromSocket();}
	}

	private void initConnection() {
		try{
			////////////
			// Waiting for a connection
			this.display("Waiting for a connection...");
			ServerSocket s = new ServerSocket(1978);
			Socket client = s.accept();
			socketInputStream = client.getInputStream();
			socketIn  = new BufferedReader(new InputStreamReader(socketInputStream));
			socketOut = new PrintWriter(client.getOutputStream(), true);
			//System.out.println(r.readLine());
			this.display("... connected!");
		} catch(Exception e) {this.initConnection();}
	}
	
	private <T> LinkedList<T> arrayToLinkedList(T[] array) {
		LinkedList<T> t = (LinkedList<T>) new LinkedList();
		for (T each: array) {
			t.addLast(each);
		}
		return t;
	}
	
	private void printOnScreen(Object obj) {
		System.out.println(obj);
	}

	private void displayIntro() {
		this.printOnScreen("_,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:");
		this.printOnScreen("|\t\t" + engine.get("ENGINE") + " Server" + "\t\t|");
		this.printOnScreen("|\t\t" +"Version "+engine.get("ENGINE_VERSION") + "\t\t\t|");
		this.printOnScreen("|\t\t" +"Press Control-c to quit" + "\t\t|");
		this.printOnScreen("_,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:~^~:;._,.;:");
	}
	
	private void quitAthena() {
		try {
			this.socketInputStream.close();
		} catch(Exception e){}
		
		this.display("");		
		this.display("---------------------------------");
		this.display("| Statistic:");
		this.display("| \tByte received: " + this.nbOfBytesReceived);
		this.display("| \tByte sent: " + this.nbOfBytesSent);
		this.display("---------------------------------");
		this.display("bye!");
		System.exit(0);
	}
	
	private void parserArguments(String[] args) {
		for (Iterator<String> i = this.arrayToLinkedList(args).iterator(); i.hasNext(); ) {
			String s = (String)i.next();
			if (s.equals("-h") || s.equals("-?") || s.equals("?") || s.equals("help")) this.displayHelpAndQuit();
			if (s.charAt(0) != '-') imageName = s;
			if (s.equals("-v")) verbose = 1;
			if (s.equals("-V")) verbose = 2;
		}
		
		if (imageName == null) {imageName = "Gossip.image";}
		
	}
	
	/**
	 * Class constructor
	 *
	 * @param args Arguments provided when invoked from the command line
	 */
	public GossipServer(String[] args) {
		this.parserArguments(args);
		this.display("imageName: "+imageName);
		//String className = (String)(l.toArray() [l.indexOf(imageName) + 1]);

		factory = new GossipScriptFactory();
		factory.setImageName(imageName);
		if(verbose>0) factory.setVerbose(true);
		factory.setDisplay("shell");
		engine = factory.getScriptEngine();

		this.displayIntro();
		this.enterInteractiveMode();
	}

	private void displayHelpAndQuit() {
		String[] options = {
				"Usage: GossipServer [imageFile] [-h?vfi] [...]",
				"-h, -?, ?, help\t\tDisplay this help",
				"-v\t\t\tVerbose mode",
				"-V\t\t\tExtended verbose mode",
				"-f <filename>\t\tLoad and execute the provided file",
				};
		for (String s: options) System.out.println(s);
		System.exit(0);
	}


	private void display(Object msg, int verboseLevel) {
		this.display((String) msg, verboseLevel);
	}

	private void display(String msg, int verboseLevel) {
		if (verbose >= verboseLevel)
			System.out.println(msg);
	}

	private void display(Object msg) {
		this.display((String) msg, 1);
	}

	private void display(String msg) {
		this.display(msg, 1);
	}

	/**
	 * Instantiate GossipServer with the provided arguments passed in its constructor
	 */
	public static void main (String [] args) {
		new GossipServer(args);
	}
}
