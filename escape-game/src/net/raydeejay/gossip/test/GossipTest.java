package net.raydeejay.gossip.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import net.raydeejay.gossip.engine.GossipScriptFactory;
import net.raydeejay.gossip.engine.GossipShell;
import net.raydeejay.gossip.engine.interpreter.SmallInt;
import net.raydeejay.gossip.engine.interpreter.SmallJavaObject;
import net.raydeejay.gossip.engine.interpreter.SmallObject;
import net.raydeejay.gossip.engine.wrapper.WeakSet;

import org.junit.Test;

public class GossipTest {
	
	public GossipTest() {}

    @Test public void testSyntax1() {
        GossipShell shell = new GossipShell(null);
        shell.evalStringWithNewSyntax("class Foo { foo: x bar: y {^ x + y }}");
        assertTrue(shell.evalExpression("Foo new foo: 5 bar: 10").toString().equals("SmallInt: 15"));

        shell.evalStringWithNewSyntax("class Foo { foo: x bar: y {^ x + y } run {^ self foo: 5 bar: 15 } }");
        assertTrue(shell.evalExpression("Foo new run").toString().equals("SmallInt: 20"));

        shell.evalStringWithNewSyntax("class Foo { foo: x bar: y {^ x + y } run { ^ self foo: 5 bar: 15}}");
        assertTrue(shell.evalExpression("Foo new run").toString().equals("SmallInt: 20"));

        shell.evalStringWithNewSyntax("class Foo{ foo: x bar: y{^ x + y } run{^self foo: 5 bar: 15}}");
        assertTrue(shell.evalExpression("Foo new run").toString().equals("SmallInt: 20"));

    }

    /** Create an object, insert it into a weak set, run the garbage collector */
	@Test public void weakSet1() {
		WeakSet set = new WeakSet();
		assertTrue(set.size() == 0);

		Object o = new Object();
		assertTrue(!set.includes(o));

		set.add(o);
		assertTrue(set.includes(o));
		assertTrue(set.size() == 1);
		o = null;
		System.gc();
		assertTrue(set.size() == 0);
	}
	
    @Test public void simpleAddition() {
		ScriptEngine engine = this.getNewEngine();
		try {
			SmallObject res = (SmallObject) engine.eval("1 + 2");
			assertTrue(res instanceof SmallInt);
			SmallInt smallInt = (SmallInt) res;
			assertEquals(smallInt.toJavaInteger(), 3);
		}
		catch(ScriptException e) {
			System.err.println("ERROR :" + e);
		}
    }

    @Test public void compileClass() {
		ScriptEngine engine = this.getNewEngine();
		try {
			engine.eval("Class addNewClass: (Object subclass: 'MyClass' variables: '' classVariables: '')");
			engine.eval("MyClass compileMethod: 'foo ^ 2 * self bar'");
			engine.eval("MyClass compileMethod: 'bar ^ 5'");
			SmallObject res = (SmallObject) engine.eval("MyClass new foo");

			assertTrue(res instanceof SmallInt);
			SmallInt smallInt = (SmallInt) res;
			assertEquals(smallInt.toJavaInteger(), 10);
		}
		catch(ScriptException e) {
			System.err.println("ERROR :" + e);
		}
    }
	
	@Test public void scriptVariables () {
		ScriptEngineFactory factory = new GossipScriptFactory();
		String[] expectedNames = {"athena","smalltalk"};
 		assertTrue(!factory.getNames().toArray().equals(expectedNames));
		
		ScriptEngine engine = this.getNewEngine();
		assertEquals(engine.get("ENGINE"), "Athena Smalltalk");
		assertTrue(new Float((String)engine.get("ENGINE_VERSION")) >= 0.1);
		assertEquals(engine.get("IMAGE_NAME"), "Athena.image");
		assertEquals(engine.get("VERBOSE"), "false");
		
		assertTrue(engine.get("BLAH") == null);
		engine.put("BLAH", 123);
		assertTrue(((Integer)engine.get("BLAH")) == 123);
	}

	@Test public void errorHandling () {
		ScriptEngine engine = this.getNewEngine();
		boolean errorRaised = false;
		try {
			engine.eval("1 +");
		}
		catch(ScriptException e) {
			errorRaised = true;
		}
		assertTrue(errorRaised);
	}
	

	@Test public void accessingJavaEnvironmentVariables () {
		ScriptEngine engine = this.getNewEngine();
		boolean errorRaised = false;
		try {
			assertEquals(engine.eval("System getEnvironmentValue: 'DISPLAY'").toString(), "shell");
			assertEquals(engine.eval("System getEnvironmentValue: 'IMAGE_NAME'").toString(), "Athena.image");
			assertEquals(engine.eval("System getEnvironmentValue: 'VERBOSE'").toString(), "false");
		}
		catch(ScriptException e) {
			errorRaised = true;
		}
		assertFalse(errorRaised);
	}

/*	@Test public void perform () {
		ScriptEngine engine = this.getNewEngine();
		boolean errorRaised = false;
		try {
			assertEquals(engine.eval("5 perform: '+' with: 10").toString(), "15");
			assertEquals(engine.eval("true perform: 'not'").toString(), "false");
		}
		catch(ScriptException e) {
			errorRaised = true;
		}
		assertFalse(errorRaised);
	}
*/

	@Test public void interactingWithJavaObject() {
		ScriptEngine engine = this.getNewEngine();
		boolean errorRaised = false;
		try {
			assertEquals(
				"JavaObject<1978>",
				engine.eval("((System instantiateJavaClass: 'net.raydeejay.gossip.test.GossipTest') invoke: 'foo') printString").toString());
		}
		catch(ScriptException e) {
			errorRaised = true;
		}
		assertFalse(errorRaised);		
	}
	
	public int foo() {return 1978;}
	public int add5(SmallJavaObject obj) {return (Integer)(obj.value) + 5;}
	public int get10() {return 10;}
	
	private ScriptEngine getNewEngine() {
        ScriptEngineFactory factory = new GossipScriptFactory();
		return factory.getScriptEngine();
	}
}