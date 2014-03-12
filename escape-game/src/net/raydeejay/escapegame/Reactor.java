package net.raydeejay.escapegame;

import ru.sg_studio.escapegame.ContextedFactory;
import ru.sg_studio.escapegame.ContextedObjectProvider.ObjectPrototype;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.SmallInterpreterInterfacer;
import ru.sg_studio.escapegame.primitives.GraphicalEntity;
import ru.sg_studio.escapegame.rendering.Texture;

public class Reactor extends GraphicalEntity {
	

	
	private Room room;

	//public Reactor() { super(); }

	public Reactor(String name, IProxiedObject bindedProxy) {
		
		this.bindedProxy=bindedProxy;
		
		this.setName(name);

		GameRegistry.instance().registerReactor(name, this);
	}

//	@Override
//	public void draw(Batch batch, float parentAlpha) {
//		if(getImage() != null) {
//			batch.setColor(Color.WHITE);
//			batch.draw(this.getImage(), this.getX(), this.getY());
//		}
//	}

	// IMAGE
	public Texture getImage() {
		return texture;
	}

//	public Reactor setImageTexture(Texture aTexture) {
//		this.image = aTexture;
//		this.setWidth(aTexture.getWidth());
//		this.setHeight(aTexture.getHeight());
//		return this;
//	}

	protected Texture texture;
	
	public Texture getClonedTexture(){
		return texture.cloneMeta();
	}
	
	public Reactor setImage(final String aFilename) {
		//final Reactor thisReactor = this;
		texture = new Texture(aFilename);
		SmallInterpreterInterfacer.RefreshTextureMetadataFor(this);

		
		return this;
	}

	// CONVERSION
	public Item asItem() {
		remove();
		return (Item) ContextedFactory.instance().getContextedItem(ObjectPrototype.Item, this).getBinded();
	}
	
	// ROOM
	public Room getRoom() {
		return room;
	}

	public Reactor setRoom(Room room) {
		this.room = room;
		return this;
	}
	
	public Reactor addToRoom(Room aRoom) {
		aRoom.addReactor(this);
		return this;
	}
	
	public Reactor addToRoomNamed(String aRoomName) {
		Room aRoom = GameRegistry.instance().getRoom(aRoomName.toString());
		return this.addToRoom(aRoom);
	}
	
	public Reactor removeFromRoom() {
		this.getRoom().removeReactor(this);
		return this;
	}

	// OTHER
	public Reactor at(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}
	
	// hack because you can't pass Booleans to Java (?)
	public void beVisible() {
		this.setVisible(true);
	}
	
	public void beInvisible() {
		this.setVisible(false);
	}

	
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		bindedProxy.trySyncGraphicalObject();
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

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		bindedProxy.trySyncGraphicalObject();
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		bindedProxy.trySyncGraphicalObject();
	}

	public Texture swapTexture(Texture master) {
		Texture slave = texture;
		texture = master;
		return slave;
	}

	
	
	
}
