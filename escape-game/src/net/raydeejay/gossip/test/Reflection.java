package net.raydeejay.gossip.test;

import java.lang.reflect.*;

public class Reflection {
	public static void main(String[] argv) {
		Method[] methods = GossipTest.class.getMethods();

		try {
			System.out.println("Method add5: " + GossipTest.class.getMethod("add5", Integer.class));
		} catch( Exception e) {System.out.println(e);}
		
		for (Method m : methods) {
			System.out.println(m.getName());
		}

	}
}