package net.raydeejay.escapegame;

import java.util.HashMap;

import net.raydeejay.escapegame.reactors.Item;

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

	public Reactor(String name, float x, float y, String aFilename) {
		this.setX(x);
		this.setY(y);
		this.setImage(aFilename);
		this.setName(name);

		this.setUpListeners();
	}

	public Reactor(String name, float x, float y, Texture aTexture) {
		this.setX(x);
		this.setY(y);
		this.setImage(aTexture);
		this.setName(name);

		this.setUpListeners();
	}

	protected void setUpListeners() {
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Item item = getRoom().getScreen().getInventory()
						.getSelectedItem();
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
		batch.setColor(Color.WHITE);
		batch.draw(this.getImage(), this.getX(), this.getY());
	}

	// IMAGE
	public Texture getImage() {
		return image;
	}

	public void setImage(Texture aTexture) {
		this.image = aTexture;
		this.setWidth(aTexture.getWidth());
		this.setHeight(aTexture.getHeight());
	}

	public void setImage(String aFilename) {
		Texture aTexture = new Texture(Gdx.files.internal(aFilename));
		this.setImage(aTexture);
	}

	// ROOM
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	// STATE
	public void addState(String aString, State aState) {
		this.states.put(aString, aState);
	}

	public State getCurrentState() {
		return this.currentState;
	}

	public void switchToState(String aString) {
		this.getCurrentState();
		if (this.currentState != null) {
			this.currentState.onExit();
		}
		this.currentState = this.states.get(aString);
		this.currentState.onEnter();
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

}
