package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room02 extends Room {

	public Room02(GameScreen gameScreen) {
		super("room02.png", gameScreen);
		
		final Reactor door02 = new Reactor(EscapeGame.WIDTH / 2, 100, "door2.png");
		door02.addState("closed", new State() {
			@Override
			public void whenClicked() {
				door02.switchToState("open");
			}
		});
		
		door02.addState("open", new State() {
			@Override
			public void onEnter() {
				door02.setImage("door2open.png");
			}
			
			@Override
			public void whenClicked() {
				getScreen().switchToRoom("room01");
			}
		});

		door02.switchToState("closed");
		this.addReactor(door02);
		
		final Reactor key = new Reactor(200, 200, "key.png");
		key.addState("state", new State() {
			@Override
			public void whenClicked() {
				final Item keyItem = new Item(50, 50, "key.png");
				addReactor(keyItem);
				removeReactor(key);
			}
			
		});
		key.switchToState("state");
		this.addReactor(key);
		
	}

}
