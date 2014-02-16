package net.raydeejay.gossip.examples;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.ScriptEngineFactory;

import net.raydeejay.gossip.engine.*;

public class InteractionWithJava {
	public static void main(String[] argv) {
		ScriptEngineFactory factory = new GossipScriptFactory();
		ScriptEngine engine = factory.getScriptEngine();
		
		String[] statements = {
			"Object compileMethod: 'foo ^ 2 * self bar'",
			"Object compileMethod: 'bar ^ 10'",
			"<117 (Object new foo)>"
		};
		
		try {
			String program = factory.getProgram(statements);
			//System.out.println(program);
			engine.eval(program);
		}
		catch(ScriptException e) {
			System.err.println("ERROR :" + e);
		}
	}
}