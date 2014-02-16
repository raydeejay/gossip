/*
	Athena virtual machine. 
	Version 0.1 August 31, 2007
	Alexandre Bergel (alexandre@bergel.eu  http://www.bergel.eu)
	
	Based on Tim Budd's work with Little Smalltalk
*/
package net.raydeejay.gossip.engine;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class GossipScriptFactory implements ScriptEngineFactory {
	private String imageToUse = "Gossip.image";
	private String displayString = "shell";
	private boolean verbose = false;
	private Map<String,String> _context = new HashMap<String,String>();

	public String getEngineName() {
		return "Gossip Smalltalk Engine";
	}
	
	public String getEngineVersion() {
		return "0.1";
	}
	
	public List<String> getExtensions() {
		List<String> l = new LinkedList<String>();
		l.add("st");
		return l;
	}
	
	public List<String> getMimeTypes() {
		return new LinkedList<String>();
	}
	
	public List<String> getNames() {
		List<String> l = new LinkedList<String>();
		l.add("gossip");
		l.add("smalltalk");
		return l;
	}
	
	public String getLanguageName() {
		return "Gossip";
	}
	
	public String getLanguageVersion() {
		return "0.1";
	}
	
	/**
	 * Get a global parameter
	 */
	public Object getParameter(String key) {
		return _context.get(key);
	}

	/**
	 * Set a global parameter
	 * Note that this method is not provided by ScriptEngineFactory
	 */
	public void setParameter(String key, String value) {
		_context.put(key, value);
	}

	
	public String getMethodCallSyntax(String obj, String m, String... args) {
		return null;
	}

	public String getOutputStatement(String toDisplay) {
		return "<117 (" + toDisplay+")>";
	}
 
	public String getProgram(String... statements) { 
		String answer = "[";
		for (String s : statements) {
			answer += s;
			if (!s.endsWith("."))
				answer += ".";
			answer += " ";
		}
		return answer + "] value";
	}

	public void setImageName(String newImageName) {
		this.imageToUse = newImageName;
	}

	/**
	 * Set the display. This is mainly used when error have to be handled: 
	 * whether the stack trace has to be displayed on the standard error output stream or in a windows.
	 * Refer to <code>Object>>error: msg</code> for more information
	 *
	 * @param display May be <code>"gui"</code> or <code>"shell"</code>
	 */
	public void setDisplay(String display) {
		this.displayString = display;
	}

	public void setVerbose(boolean v) {
		this.verbose = v;
	}

    public ScriptEngine getScriptEngine() {
		GossipScriptEngine engine = new GossipScriptEngine();
		engine.put("IMAGE_NAME", this.imageToUse);
		engine.put("VERBOSE", new Boolean(this.verbose).toString());
		engine.put("DISPLAY", this.displayString);

		
		Set<Map.Entry<String,String>> contextSet = _context.entrySet();
		for(Map.Entry<String,String> each: contextSet){
			//System.out.println("==>" + each);
			engine.put(each.getKey(), each.getValue());
		}
		
		engine.initializeImage();
		return engine;
	}

	public ScriptEngine getScriptEngineWithImage(InputStream is) {
		//setImageName(is);
		GossipScriptEngine engine = new GossipScriptEngine();
		engine.put("IMAGE_NAME", this.imageToUse);
		engine.put("VERBOSE", new Boolean(this.verbose).toString());
		engine.put("DISPLAY", this.displayString);

		
		Set<Map.Entry<String,String>> contextSet = _context.entrySet();
		for(Map.Entry<String,String> each: contextSet){
			//System.out.println("==>" + each);
			engine.put(each.getKey(), each.getValue());
		}
		
		engine.initializeImageFromStream(is);
		return engine;
	}
}