package net.raydeejay.escapegame;

import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Obtainable extends Reactor {

//	public Obtainable(String name, float x, float y, String aFilename) {
//		super(name, x, y, aFilename);
//	}
//
//	public Obtainable(String name, float x, float y, Texture aTexture) {
//		super(name, x, y, aTexture);
//	}

	public Obtainable(String aName, GameScreen aScreen) {
		super(aName, aScreen);

		addState(new State("state") {
			@Override
			public void whenClicked() {
				Item item = asItem();
				gameScreen.addToInventory(item);
				removeFromRoom();
			}

		});
		
		switchToState("state");
	}
}
