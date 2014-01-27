package net.raydeejay.escapegame.reactors;

import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.State;

public class Movable extends Reactor {

	public Movable(int x, int y, String aFilename) {
		super(x, y, aFilename);

		this.addState("1", new State() {
			@Override
			public void whenClicked() {
				switchToState("2");
				setX(400);
			}
		});

		this.addState("2", new State() {
			@Override
			public void whenClicked() {
				switchToState("3");
				setX(600);
			}
		});

		this.addState("3", new State() {
			@Override
			public void whenClicked() {
				switchToState("1");
				setX(200);
			}
		});

		this.switchToState("1");
	}

}
