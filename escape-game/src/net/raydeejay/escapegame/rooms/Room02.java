package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.reactors.Door;

public class Room02 extends Room {

	public Room02() {
		super("room02.png");
		
		Door door02 = new Door(EscapeGame.WIDTH / 2, 100, "door2.png", "room01");
		this.addReactor(door02);

		//this.rooms.put("room02", room02);		
	}

}
