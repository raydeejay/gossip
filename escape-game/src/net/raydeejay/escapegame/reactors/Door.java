package net.raydeejay.escapegame.reactors;

import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Door extends Reactor {
	private String destination;

	public Door(int x, int y, String aFilename, String aDestination) {
		super(x, y, aFilename);
		this.destination = aDestination;

		this.addState("closed", new State() {
			@Override
			public void whenClicked() {
				switchToState("open");
				setImage("door2open.png");
			}
		});

		this.addState("open", new State() {
			@Override
			public void whenClicked() {
				GameScreen screen = (GameScreen) ((Game) Gdx.app
						.getApplicationListener()).getScreen();
				screen.switchToRoom(destination);
			}
		});

		this.switchToState("closed");
	}
}
