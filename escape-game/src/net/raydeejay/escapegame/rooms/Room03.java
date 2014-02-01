package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room03 extends Room {

	public Room03(GameScreen gameScreen) {
		super("room03", "room03.png", gameScreen);
		this.setExitLeft("room02");
		this.setExitRight("room04");

		final Reactor fireplace = new Reactor("fireplace", 200, 82,
				"fireplace.png");
		final Reactor fire = new Reactor("fire", 325, 85, "fire.png");

		// fireplace
		fireplace.addState("empty", new State() {
			@Override
			public void whenClickedWith(Item anItem) {
				if (anItem.getName().equals("logsItem")) {
					anItem.getInventory().removeItem(anItem);
					fire.switchToState("with logs");
					fireplace.switchToState("filled");
				}
			}
		});

		fireplace.addState("filled", new State() {
		});

		fireplace.switchToState("empty");
		this.addReactor(fireplace);

		// fire
		fire.addState("hidden", new State() {
			@Override
			public void onEnter() {
				fire.setVisible(false);
			}
		});

		fire.addState("with logs", new State() {
			@Override
			public void onEnter() {
				fire.setImage("logs.png");
				fire.setVisible(true);
			}

			@Override
			public void whenClickedWith(Item anItem) {
				if (anItem.getName().equals("lighterItem")) {
					anItem.getInventory().removeItem(anItem);
					fire.switchToState("lit");
				}
			}
		});

		fire.addState("lit", new State() {
			@Override
			public void onEnter() {
				fire.setImage("fire.png");
			}

			@Override
			public void whenClickedWith(Item anItem) {
				if (anItem.getName().equals("boxItem")) {
					anItem.getInventory().removeItem(anItem);
					removeReactor(fire);

					final Reactor hammer = new Reactor("hammer", fire.getX(),
							fire.getY(), "hammer.png");
					hammer.addState("state", new State() {
						@Override
						public void whenClicked() {
							final Item hammerItem = new Item(hammer);
							addToInventory(hammerItem);
							removeReactor(hammer);
						}

					});

					hammer.switchToState("state");
					addReactor(hammer);
				}
			}

		});

		fire.switchToState("hidden");
		this.addReactor(fire);

		// knife
		final Reactor knife = new Reactor("knife", 530, 5, "knife.png");
		knife.addState("state", new State() {

		});
		knife.switchToState("state");
		this.addReactor(knife);

	}

}
