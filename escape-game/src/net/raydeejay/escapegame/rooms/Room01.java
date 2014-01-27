package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room01 extends Room {

	public Room01(GameScreen gameScreen) {
		super("room01partial.png", gameScreen);
		
		final Reactor door01 = new Reactor(EscapeGame.WIDTH / 3, 100, "door2.png");
		
		door01.addState("closed", new State() {
			@Override
			public void whenClicked() {
				door01.switchToState("open");
			}
		});
		
		door01.addState("open", new State() {
			@Override
			public void onEnter() {
				door01.setImage("door2open.png");
			}
			
			@Override
			public void whenClicked() {
				getScreen().switchToRoom("room02");
			}
		});

		door01.switchToState("closed");
		this.addReactor(door01);

		final Reactor vase = new Reactor(200, 200, "vase.png");
		vase.addState("1", new State() {
			@Override
			public void onEnter() {
				vase.setX(200);
			}

			@Override
			public void whenClicked() {
				vase.switchToState("2");
			}
		});

		vase.addState("2", new State() {
			@Override
			public void onEnter() {
				vase.setX(400);
			}

			@Override
			public void whenClicked() {
				vase.switchToState("3");
			}
		});

		vase.addState("3", new State() {
			@Override
			public void onEnter() {
				vase.setX(600);
			}

			@Override
			public void whenClicked() {
				vase.switchToState("1");
			}
		});

		vase.switchToState("1");
		this.addReactor(vase);

	}
}
