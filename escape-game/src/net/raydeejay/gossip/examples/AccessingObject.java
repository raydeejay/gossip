package net.raydeejay.gossip.examples;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.raydeejay.gossip.engine.GossipScriptFactory;

public class AccessingObject {
	public static void main(String[] argv) {
		GossipScriptFactory factory = new GossipScriptFactory();
		factory.setParameter("MY_VARIABLE", "123");
		ScriptEngine engine = factory.getScriptEngine();
		
		try {
			System.out.println("Should return 123, if not, then work on it!: ");
			System.out.print(engine.eval("System getEnvironmentValue: 'MY_VARIABLE'"));
		}
		catch(ScriptException e) { System.err.println("ERROR :" + e); }
	}
}