package net.raydeejay.gossip.test;

import java.lang.reflect.*;

public class InvokeStaticMethod {
    public static void main(String[] argv) {
	try {
	    Class<System> cls = (Class<System>)Class.forName("java.lang.System");
	    Method m = cls.getDeclaredMethod("gc");
	    System.out.println(m.invoke(cls.newInstance()));
	}
	catch(Exception e) {
	    System.err.println(e);
	}
    }
}
