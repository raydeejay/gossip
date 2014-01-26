package net.raydeejay.escapegame.reactors;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.screens.GameScreen;

import com.badlogic.gdx.Gdx;

public class Door extends Reactor {
	enum State { Open, Closed }
	private State state;
	private String destination;
	
	public Door(int x, int y, String aFilename, String aDestination) {
		super(x, y, aFilename);
		this.state = State.Closed;
		this.destination = aDestination;
	}
	
	@Override
	public void whenClicked() {
		switch(this.state) {
		case Closed:
			this.state = State.Open;
			this.setImage("door2open.png");
			break;
		case Open:
			GameScreen screen = (GameScreen) ((EscapeGame) Gdx.app.getApplicationListener()).getScreen();
			screen.switchToRoom(this.destination);
			break;
		}
	}

}
