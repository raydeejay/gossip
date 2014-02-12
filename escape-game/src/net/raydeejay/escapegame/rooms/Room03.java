package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.reactors.Dropper;
import net.raydeejay.escapegame.reactors.Obtainable;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room03 extends Room {

	// @formatter:off
	public Room03(final GameScreen gameScreen) {
		super("room03", "room03.png", gameScreen);
		this.setExitLeft("room02");
		this.setExitRight("room04");

	}
}
