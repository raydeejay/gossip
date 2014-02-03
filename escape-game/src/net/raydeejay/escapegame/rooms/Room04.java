package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Dropper;
import net.raydeejay.escapegame.Obtainable;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room04 extends Room {

	public Room04(GameScreen gameScreen) {
		super("room04", "room04.png", gameScreen);
		this.setExitLeft("room03");

		Reactor axe = new Obtainable("axe", gameScreen)
			.setImage("axe.png")
			.at(100, 40);
	
		this.addReactor(axe);

		Reactor lighter = new Obtainable("lighter", gameScreen)
			.setImage("lighter.png")
			.at(200, 40);

		this.addReactor(lighter);

		Reactor logs = new Obtainable("logs", gameScreen)
			.setImage("logs.png");
		
		Reactor tree = new Dropper("tree", gameScreen)
			.reactTo("axeItem")
			.dropAt(480, 20, "logs")
			.setImage("tree.png")
			.at(480, 20);
		
		this.addReactor(tree);

	}
}
