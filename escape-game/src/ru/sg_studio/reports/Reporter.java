package ru.sg_studio.reports;

public final class Reporter {

	private static final String coreMessage = "Sorry. Something is totally wrong:\n\n";

	public static void out(Exception e){
		System.out.println(coreMessage+e);
	}
	
	
}
