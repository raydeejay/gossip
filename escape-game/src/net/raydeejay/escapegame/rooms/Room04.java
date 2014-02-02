package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room04 extends Room {

	public Room04(GameScreen gameScreen) {
		super("room04", "room04.png", gameScreen);
		this.setExitLeft("room03");

		final Reactor axe = new Reactor("axe", 100, 40, "axe.png");
		axe.addState(new State("state") {
			@Override
			public void whenClicked() {
				final Item axeItem = new Item(axe);
				addToInventory(axeItem);
				removeReactor(axe);
			}

		});
		axe.switchToState("state");
		this.addReactor(axe);
		
		final Reactor lighter = new Reactor("lighter", 200, 40, "lighter.png");
		lighter.addState(new State("state") {
			@Override
			public void whenClicked() {
				final Item lighterItem = new Item(lighter);
				addToInventory(lighterItem);
				removeReactor(lighter);
			}

		});
		lighter.switchToState("state");
		this.addReactor(lighter);
		
		final Reactor tree = new Reactor("tree", 480, 20, "tree.png");
		tree.addState(new State("state") {
			@Override
			public void whenClickedWith(Item anItem) {
				if (anItem.getName().equals("axeItem")) {
					anItem.removeFromInventory();
					tree.removeFromRoom();

					final Reactor logs = new Reactor("logs", tree.getX(),
							tree.getY(), "logs.png");
					logs.addState(new State("state") {
						@Override
						public void whenClicked() {
							final Item logsItem = new Item(logs);
							addToInventory(logsItem);
							logs.removeFromRoom();
						}

					});

					logs.switchToState("state");
					addReactor(logs);
				}
			}

		});

		tree.switchToState("state");
		this.addReactor(tree);


	}

}
