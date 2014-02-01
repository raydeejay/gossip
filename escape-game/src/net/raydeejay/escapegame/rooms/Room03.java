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
		fireplace.addState("state", new State() {

		});
		fireplace.switchToState("state");
		this.addReactor(fireplace);

		final Reactor fire = new Reactor("fire", 325, 85, "fire.png");
		fire.addState("state", new State() {
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

		fire.switchToState("state");
		this.addReactor(fire);

		final Reactor knife = new Reactor("knife", 530, 5, "knife.png");
		knife.addState("state", new State() {

		});
		knife.switchToState("state");
		this.addReactor(knife);

	}

}
