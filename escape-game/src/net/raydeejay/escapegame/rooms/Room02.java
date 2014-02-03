package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Door;
import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Obtainable;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room02 extends Room {

	public Room02(GameScreen gameScreen) {
		super("room02", "room02.png", gameScreen);
		this.setExitRight("room03");
		
		Reactor door02 = new Door("door02", gameScreen)
			.at(EscapeGame.WIDTH / 3, 82)
			.destination("room01")
			.lockedWith("keyItem")
			.imageForOpen("door2open.png")
			.imageForClosed("door2.png")
			.imageForLocked("door2.png")
			.switchToState("locked");

		this.addReactor(door02);

		Reactor key = new Obtainable("key", gameScreen)
			.setImage("key.png")
			.at(200, 20);
		
		this.addReactor(key);
	}
}
