package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.reactors.Dropper;
import net.raydeejay.escapegame.reactors.Obtainable;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room04 extends Room {

	public Room04(GameScreen gameScreen) {
		super("room04", "room04.png", gameScreen);
		this.setExitLeft("room03");

//		new Obtainable("axe", gameScreen)
//			.setImage("axe.png")
//			.at(100, 40)
//			.addToRoom(this);
//
//		new Obtainable("lighter", gameScreen)
//			.setImage("lighter.png")
//			.at(200, 40)
//			.addToRoom(this);
//
//		new Obtainable("logs", gameScreen)
//			.setImage("logs.png");
//		
//		new Dropper("tree", gameScreen)
//			.reactTo("axeItem")
//			.dropAt(480, 20, "logs")
//			.setImage("tree.png")
//			.at(480, 20)
//			.addToRoom(this);
	}
}
