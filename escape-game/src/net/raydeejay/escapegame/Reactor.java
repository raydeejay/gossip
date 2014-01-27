package net.raydeejay.escapegame;

import java.util.HashMap;

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

	public Reactor(int x, int y, String aFilename) {
		Texture aTexture = new Texture(Gdx.files.internal(aFilename));

		this.setImage(aTexture);
		this.setBounds(x, y, aTexture.getWidth(), aTexture.getHeight());

		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				whenClicked();
				return true;
			}
		});
	}

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

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Color.WHITE);
		batch.draw(this.getImage(), this.getX(), this.getY());
	}

	// these are to be overriden if necessary by subclasses
	// default implementation is to do nothing

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

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

	public void whenClicked() {
		State state = this.getCurrentState();
		if (state != null) {
			state.whenClicked();
		}
	}

	public void whenClickedWith(Reactor aReactor) {

	}

	public void whenCombinedWith(Reactor aReactor) {

	}

}
