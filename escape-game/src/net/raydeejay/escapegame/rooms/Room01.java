package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.reactors.Door;
import net.raydeejay.escapegame.reactors.Movable;

public class Room01 extends Room {

	public Room01() {
		super("room01.png");
		
		Door door01 = new Door(EscapeGame.WIDTH / 3, 100, "door2.png", "room02");
		this.addReactor(door01);

		Movable vase = new Movable(200, 200, "vase.png");
		this.addReactor(vase);

		//this.rooms.put("room01", room01);		
	}

}
