package net.raydeejay.gossip.examples;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.ScriptEngineFactory;

import net.raydeejay.gossip.engine.*;

public class HelloWorld {
	public static void main(String[] argv) {
		ScriptEngineFactory factory = new GossipScriptFactory();
		ScriptEngine engine = factory.getScriptEngine();
		
		try {
			engine.eval("1 to: 10 do: [:i| <117 i>]");
			engine.eval("<117 'Hello World!'>");
			
			String statement = factory.getOutputStatement("'Hello ' + 'World!'");
			engine.eval(statement);
		}
		catch(ScriptException e) {
			System.err.println("ERROR :" + e);
		}
	}
}