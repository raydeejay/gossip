package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.reactors.Door;
import net.raydeejay.escapegame.reactors.Obtainable;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room02 extends Room {

	public Room02(GameScreen gameScreen) {
		super("room02", "room02.png", gameScreen);
		this.setExitRight("room03");
		
	}
}
