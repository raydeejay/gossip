package net.raydeejay.escapegame;

import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Door extends Reactor {

	private String destination;
	private String imageForOpen;
	private String imageForClosed;
	private String key;
	private String imageForLocked;

	public Door(String aName, GameScreen aScreen) {
		super(aName, aScreen); // FIXME

		addState(new State("locked") {
			@Override
			public void onEnter() {
				setImage(imageForLocked);
			}

			@Override
			public void whenClickedWith(Item anItem) {
				if(anItem.getName().equals(key)) {
					anItem.removeFromInventory();
					switchToState("closed");
				}
			}
		});

		addState(new State("closed") {
			@Override
			public void onEnter() {
				setImage(imageForClosed);
			}

			@Override
			public void whenClicked() {
				switchToState("open");
			}
		});

		addState(new State("open") {
			@Override
			public void onEnter() {
				setImage(imageForOpen);
			}

			@Override
			public void whenClicked() {
				gameScreen.switchToRoom(destination);
			}
		});

		// FIXME
//		switchToState("closed");
	}

	public Door at(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}

	public Door destination(String aRoomName) {
		destination = aRoomName;
		return this;
	}

	public Door imageForOpen(String aString) {
		imageForOpen = aString;
		return this;
	}

	public Door imageForClosed(String aString) {
		imageForClosed = aString;
		return this;
	}

	public Door imageForLocked(String aString) {
		imageForLocked = aString;
		return this;
	}

	public Door lockedWith(String anItemName) {
		key = anItemName;
		return this;
	}

}
