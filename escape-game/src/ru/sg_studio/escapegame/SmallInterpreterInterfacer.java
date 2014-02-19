package ru.sg_studio.escapegame;

import java.io.InputStream;

public class SmallInterpreterInterfacer {

	public static InputStream ReadInjarFile(String internalName) {
		internalName="/"+internalName;
		System.out.println("Marshalling file access for "+internalName+" file");
		
		InputStream stream = SmallInterpreterInterfacer.class.getResourceAsStream(internalName);
		
		return stream;
	}

}
