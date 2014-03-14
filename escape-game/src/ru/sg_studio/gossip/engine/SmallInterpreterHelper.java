package ru.sg_studio.gossip.engine;

public class SmallInterpreterHelper {

	
	public static void report(Exception e){
		System.err.println("===========GOSSIP SI ERROR(Java-side)===========");
		System.err.println("Details: ");
		System.err.println(e.getMessage());
		System.err.println("Stacktrace: ");
		e.printStackTrace();
		System.err.println("================================================");
	}
	
}
