package net.raydeejay.gossip.test;

import java.lang.ref.*;

public class WeakExample {
    public static void main(String[] argv){
	System.out.println("Free Memory before allocation: " + Runtime.getRuntime().freeMemory());
	//WeakReference<String> ref = new WeakReference<String>(new String("Hello!"));
	WeakReference ref = new WeakReference(new String("Hello!"));
	System.out.println("Free Memory after allocation: " + Runtime.getRuntime().freeMemory());
	System.out.println("Before GC: "+ ref.get());
	System.gc();
	System.out.println("After GC: "+ ref.get());
	System.out.println("Free Memory after allocation: " + Runtime.getRuntime().freeMemory());
    }
}
