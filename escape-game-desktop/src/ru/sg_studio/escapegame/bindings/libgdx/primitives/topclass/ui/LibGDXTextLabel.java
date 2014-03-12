package ru.sg_studio.escapegame.bindings.libgdx.primitives.topclass.ui;


import net.raydeejay.escapegame.Reactor;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.bindings.libgdx.helpers.LibGDXFontDictionary;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXProto;
import ru.sg_studio.escapegame.primitives.topclass.ui.TextLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LibGDXTextLabel extends LibGDXProto implements IProxiedObject {

	protected TextLabel coreobject;


	@Override
	public TextLabel getBinded() {
		return coreobject;
	}

	public LibGDXTextLabel(String name){
		super();//DO NOT REMOVE! THIS IS IMPORTANT!
		coreobject = new TextLabel(name, this);
		
		
	}


	
	
	@Override
	public void trySyncGraphicalObject() {
		//TODO: Should be moved to LibGDXProto
		if(getBinded()==null){return;}//Bad syncable
		this.setX(getBinded().getX());
		this.setY(getBinded().getY());
		
		this.setWidth(getBinded().getWidth());
		this.setHeight(getBinded().getHeight());
		
		this.setVisible(getBinded().isVisible());
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(checkContextLoading()){
			checkDatapipe();
			
			font.draw(batch, seq, getX(), getY());
		}
		//System.err.println("Something wrong");
		
		//super.draw(batch, parentAlpha);
	}
	
	CharSequence seq = "initial";
	private void checkDatapipe() {
		if(coreobject.isTextChanged()){
			coreobject.setTextSynced();
			seq = coreobject.getTextToRender();
		}	
	}


	private boolean isLoaded=false;
	
	private boolean checkContextLoading(){
		if(isLoaded){return true;}else{
			return load();
		}
	}
	private boolean load(){
		if(coreobject.isReadyToBeLoadedIntoContext()){
			System.out.println("Attempting to load...");
			return fontload();
		}else{
			return false;
		}
	}

	private boolean fontload() {
		//TODO: IRRATIONAL!
		//TODO: Crash protection
		String fontpath = coreobject.getFontpath();
		font = LibGDXFontDictionary.pop(fontpath);
		if(font!=null){isLoaded=true;return true;}else{return false;}
		
	}
	
	
	//Local context data
	BitmapFont font;
	
	

}
