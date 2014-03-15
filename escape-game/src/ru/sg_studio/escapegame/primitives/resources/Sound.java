package ru.sg_studio.escapegame.primitives.resources;

import ru.sg_studio.sense.StringHelper;

public class Sound extends Resource {
	
	protected String filepath=StringHelper.EMPTY;
	
	String name = StringHelper.EMPTY;
	public Sound(String string) {
		this.name = string;
	}

	public Sound setPath(String aString){
		this.filepath=aString;
		return this;
	}
	
	

	public Sound tryLoad(){
		bindedProxy.sendLoadSignal();
		return this;
	}

	public String getPath() {
		return filepath;
	}
	
}
