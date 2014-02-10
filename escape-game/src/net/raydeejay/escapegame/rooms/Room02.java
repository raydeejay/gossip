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
		
//		new Door("door02", gameScreen)
//			.at(EscapeGame.WIDTH / 3, 82)
//			.destination("room01")
//			.lockedWith("keyItem")
//			.imageForOpen("door2open.png")
//			.imageForClosed("door2.png")
//			.imageForLocked("door2.png")
//			.switchToState("locked")
//			.addToRoom(this);

//		new Obtainable("key", gameScreen)
//			.setImage("key.png")
//			.at(200, 20)
//			.addToRoom(this);
	}
}
