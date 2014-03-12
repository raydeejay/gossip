package ru.sg_studio.escapegame.primitives.topclass.ui;

import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.primitives.topclass.Overlay;
import ru.sg_studio.sense.StringHelper;

public class TextLabel extends Overlay {
	
	String textToRender;
	
	
	public TextLabel(String name , IProxiedObject bindedProxy){
		this(name,bindedProxy,StringHelper.EMPTY);
	}
	public TextLabel(String name , IProxiedObject bindedProxy, String text){
		
		this.bindedProxy=bindedProxy;
		
		this.setName(name);
		
		this.textToRender = text;
	}
	
	
	
	
	
	
	
}
