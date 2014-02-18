package net.raydeejay.escapegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Reactor extends Actor {
	private Texture image;
	private Room room;

	public Reactor() { super(); }

	public Reactor(String name) {
		this.setName(name);

		GameRegistry.instance().registerReactor(name, this);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(getImage() != null) {
			batch.setColor(Color.WHITE);
			batch.draw(this.getImage(), this.getX(), this.getY());
		}
	}

	// IMAGE
	public Texture getImage() {
		return image;
	}

	public Reactor setImageTexture(Texture aTexture) {
		this.image = aTexture;
		this.setWidth(aTexture.getWidth());
		this.setHeight(aTexture.getHeight());
		return this;
	}

	public Reactor setImage(final String aFilename) {
		final Reactor thisReactor = this;
		new Thread(new Runnable() {
			   @Override
			   public void run() {
			      // post a Runnable to the rendering thread
			      Gdx.app.postRunnable(new Runnable() {
			         @Override
			         public void run() {
			     		Texture aTexture = new Texture(Gdx.files.internal(aFilename));
			    		thisReactor.setImageTexture(aTexture);
			         }
			      });
			   }
			}).start();
		return this;
	}

	// CONVERSION
	public Item asItem() {
		return new Item(this);
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
}
