package net.raydeejay.escapegame;

import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Dropper extends Reactor {

	private String reactTo;
	private int dropAtX;
	private int dropAtY;
	private String dropItem;
	private boolean shouldStay = false;

	public Dropper(String aName, GameScreen aScreen) {
		super(aName, 0, 0, "door2.png", aScreen); // FIXME

		addState(new State("state") {

			@Override
			public void whenClickedWith(Item anItem) {
				if (anItem.getName().equals(reactTo)) {
					anItem.removeFromInventory();

					Reactor drop = GameRegistry.getReactor(dropItem).at(
							dropAtX, dropAtY);
					getRoom().addReactor(drop);
					if (!shouldStay) {
						removeFromRoom();
					}
				}
			}
		});

		switchToState("state");
	}

	// public Dropper(String name, float x, float y, String aFilename) {
	// super(name, x, y, aFilename);
	// }
	//
	public Dropper at(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}

	public Dropper reactTo(String anItemName) {
		reactTo = anItemName;
		return this;
	}

	public Dropper dropAt(int x, int y, String aReactorName) {
		dropAtX = x;
		dropAtY = y;
		dropItem = aReactorName;
		return this;
	}

	public Dropper shouldStay() {
		shouldStay = true;
		return this;
	}
}
