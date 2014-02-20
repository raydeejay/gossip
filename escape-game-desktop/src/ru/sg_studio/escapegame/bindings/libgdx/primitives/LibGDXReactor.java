package ru.sg_studio.escapegame.bindings.libgdx.primitives;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.raydeejay.escapegame.Reactor;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.bindings.libgdx.LibGDXTextureUtilities;
import ru.sg_studio.escapegame.bindings.libgdx.LibGDXTextureWrapper;
import ru.sg_studio.escapegame.primitives.GraphicalEntity;

public class LibGDXReactor extends Actor implements IProxiedObject {

	protected Reactor coreObject;

	@Override
	public GraphicalEntity getBinded() {
		return coreObject;
	}
	
	
	public LibGDXReactor(String name){
		this();//DO NOT REMOVE! THIS IS IMPORTANT!
		coreObject = new Reactor(name, this);
		
		trySyncGraphicalObject();
	}
	
	public LibGDXReactor(){
		//TODO: Should be moved to LibGDXProto
	}
	
	
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		LibGDXTextureUtilities.checkLibGDXTexture((Reactor)getBinded());
		if(((Reactor)getBinded()).getImage() != null) {
			batch.setColor(Color.WHITE);

			//batch.draw(coreObject.getImage(), coreObject.getX(), coreObject.getY());
			//TODO: Ugly large...
			batch.draw(((LibGDXTextureWrapper)((Reactor)getBinded()).getImage()).getGdxTexture(), getX(),getY());
		}
	}


	@Override
	public void trySyncGraphicalObject() {
		if(getBinded()==null){return;}//Bad syncable
		this.setX(getBinded().getX());
		this.setY(getBinded().getY());
		
		this.setWidth(getBinded().getWidth());
		this.setHeight(getBinded().getHeight());
	}


//	@Override
//	public float getX() {
//		return getBinded().getX();
//	}
//	@Override
//	public float getY() {
//		return getBinded().getY();
//	}
//	@Override
//	public float getWidth() {
//		return getBinded().getWidth();
//	}
//	@Override
//	public float getHeight() {
//		return getBinded().getHeight();
//	}
	
	
	
	
	
	
	
	
}
