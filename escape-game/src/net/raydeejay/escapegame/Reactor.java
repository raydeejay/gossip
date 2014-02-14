package net.raydeejay.escapegame;

import java.util.HashMap;

import net.raydeejay.escapegame.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Reactor extends Actor {
	private State currentState;
	private HashMap<String, State> states = new HashMap<String, State>();
	private Texture image;
	private Room room;
	protected GameScreen gameScreen;

	public Reactor() { super(); }

	public Reactor(String name, GameScreen aScreen) {
		this.setName(name);
		gameScreen = aScreen;

		this.setUpListeners();
		GameRegistry.instance().registerReactor(name, this);
	}

	protected void setUpListeners() {
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Item item = GameRegistry.instance().getInventory().getSelectedItem();
				if (item == null) {
					whenClicked();
				} else {
					whenClickedWith(item);
				}
				return true;
			}
		});
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
		return new Item(this, gameScreen);
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

	// INVENTORY
//	public void moveToInventory() {
//		Item item = this.asItem();
//		GameRegistry.instance().addToInventory(item);
//		this.removeFromRoom();
//	}

	// STATE
	public Reactor addState(State aState) {
		this.states.put(aState.getName(), aState);
		return this;
	}

	public State getCurrentState() {
		return this.currentState;
	}

	public Reactor switchToState(String aString) {
		this.getCurrentState();
		if (this.currentState != null) {
			this.currentState.onExit();
		}
		this.currentState = this.states.get(aString);
		this.currentState.onEnter();
		return this;
	}

	// INTERACTION
	public void whenClicked() {
		State state = this.getCurrentState();
		if (state != null) {
			state.whenClicked();
		}
	}

	public void whenClickedWith(Item anItem) {
		State state = this.getCurrentState();
		if (state != null) {
			state.whenClickedWith(anItem);
		}

	}

	// OTHER
	public Reactor at(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}
}
