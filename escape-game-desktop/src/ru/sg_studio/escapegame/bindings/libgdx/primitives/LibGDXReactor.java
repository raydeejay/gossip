package ru.sg_studio.escapegame.bindings.libgdx.primitives;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.raydeejay.escapegame.Reactor;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.bindings.libgdx.LibGDXTextureUtilities;
import ru.sg_studio.escapegame.bindings.libgdx.LibGDXTextureWrapper;
import ru.sg_studio.escapegame.primitives.GraphicalEntity;

public class LibGDXReactor extends LibGDXProto implements IProxiedObject {

	protected Reactor coreObject;

	@Override
	public GraphicalEntity getBinded() {
		return coreObject;
	}
	
	
	public LibGDXReactor(String name){
		super();//DO NOT REMOVE! THIS IS IMPORTANT!
		coreObject = new Reactor(name, this);
		
		
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
		//TODO: Should be moved to LibGDXProto
		if(getBinded()==null){return;}//Bad syncable
		this.setX(getBinded().getX());
		this.setY(getBinded().getY());
		
		this.setWidth(getBinded().getWidth());
		this.setHeight(getBinded().getHeight());
		
		this.setVisible(getBinded().isVisible());
	}



	
	
	@Override
	public void sendLoadSignal() {
		//Dummy
	}	
	
	
	
	
	
}
