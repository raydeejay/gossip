package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room02 extends Room {

	public Room02(GameScreen gameScreen) {
		super("room02", "room02partial.png", gameScreen);
		
		final Reactor door02 = new Reactor("door02", EscapeGame.WIDTH / 2, 100, "door2.png");
		door02.addState("locked", new State() {
			@Override
			public void whenClickedWith(Item anItem) {
				if(anItem.getName() == "keyItem") {
					anItem.getInventory().removeItem(anItem);
					door02.switchToState("closed");
				}
			}
		});
		
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

		door02.switchToState("locked");
		this.addReactor(door02);
		
		final Reactor key = new Reactor("key", 200, 200, "key.png");
		key.addState("state", new State() {
			@Override
			public void whenClicked() {
				removeReactor(key);
				final Item keyItem = new Item("keyItem", 720, 420, "key.png");
				addToInventory(keyItem);
			}
			
		});
		key.switchToState("state");
		this.addReactor(key);
		
		final Reactor hammer = new Reactor("hammer", 400, 100, "hammer.png");
		hammer.addState("state", new State() {
			@Override
			public void whenClicked() {
				removeReactor(hammer);
				final Item hammerItem = new Item("hammerItem", "hammer.png");
				addToInventory(hammerItem);
			}
			
		});
		hammer.switchToState("state");
		this.addReactor(hammer);
		
	}

}
