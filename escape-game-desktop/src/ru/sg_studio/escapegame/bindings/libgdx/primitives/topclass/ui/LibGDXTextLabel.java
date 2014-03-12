package ru.sg_studio.escapegame.bindings.libgdx.primitives.topclass.ui;


import net.raydeejay.escapegame.Reactor;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.primitives.topclass.ui.TextLabel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LibGDXTextLabel extends Actor implements IProxiedObject {

	protected TextLabel coreobject;


	@Override
	public TextLabel getBinded() {
		return coreobject;
	}

	public LibGDXTextLabel(String name){
		this();//DO NOT REMOVE! THIS IS IMPORTANT!
		coreobject = new TextLabel(name, this);
		
		trySyncGraphicalObject();
	}

	public LibGDXTextLabel(){
		//TODO: Should be moved to LibGDXProto
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
		
		System.err.println("Something wrong");
		
		super.draw(batch, parentAlpha);
	}
	
	
	
	
	
	

}
