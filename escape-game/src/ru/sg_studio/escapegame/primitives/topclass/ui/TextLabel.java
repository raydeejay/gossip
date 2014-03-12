package ru.sg_studio.escapegame.primitives.topclass.ui;

import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.primitives.topclass.Overlay;
import ru.sg_studio.sense.StringHelper;

public class TextLabel extends Overlay {
	
	private String textToRender;
	private boolean isTextChanged;
	
	
	public boolean isTextChanged() {
		return this.isTextChanged;
	}


	public void setTextChanged() {
		this.isTextChanged=true;
	}
	public void setTextSynced(){
		this.isTextChanged=false;
	}


	public String getTextToRender() {
		return this.textToRender;
	}


	private boolean isReadyToBeLoadedIntoContext=false;
	public boolean isReadyToBeLoadedIntoContext(){
		return isReadyToBeLoadedIntoContext;
	}
	/*
	 * To be loaded it requares:
	 * 1)generic dir to bitmap font
	 * 2)lol
	 */
	
	
	public TextLabel(String name , IProxiedObject bindedProxy){
		this(name,bindedProxy,StringHelper.EMPTY);
	}
	public TextLabel(String name , IProxiedObject bindedProxy, String text){
		
		this.bindedProxy=bindedProxy;
		
		this.setName(name);
		
		this.textToRender = text;
		setTextChanged();
	}
	
	public TextLabel setText(String text){
		this.textToRender = text;
		setTextChanged();		
		return this;//ST here
	}
	
	
	private String fontpath=StringHelper.EMPTY;
	public String getFontpath(){
		return fontpath;
	}
	
	public TextLabel setFontPath(String filepath){
		fontpath = filepath;
		checkState();
		return this;//ST style...
	}
	
	
	private void checkState(){
		if(!fontpath.equals(StringHelper.EMPTY)){
			isReadyToBeLoadedIntoContext=true;
		}
	}
	
	@Override
	public void setX(int x) {
		super.setX(x);
		bindedProxy.trySyncGraphicalObject();
	}

	@Override
	public void setY(int y) {
		super.setY(y);
		bindedProxy.trySyncGraphicalObject();
	}	
	
	
	
	
}
