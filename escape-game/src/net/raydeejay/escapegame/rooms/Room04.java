package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.reactors.Dropper;
import net.raydeejay.escapegame.reactors.Obtainable;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room04 extends Room {

	public Room04(GameScreen gameScreen) {
		super("room04", "room04.png", gameScreen);
		this.setExitLeft("room03");

	}
}
