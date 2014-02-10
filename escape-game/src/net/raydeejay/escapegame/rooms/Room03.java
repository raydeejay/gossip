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

//		// hammer
//		new Obtainable("hammer", gameScreen)
//			.setImage("hammer.png");
//
//		// knife
//		new Obtainable("knife", gameScreen)
//			.setImage("knife.png")
//			.at(530, 5)
//			.addToRoom(this);

		// fireplace
		new Dropper("fireplace", gameScreen)
			.at(200, 82)
			.dropAt(325, 85, "unlitFire")
			.reactTo("logsItem")
			.shouldStay()
			.setImage("fireplace.png")
			.addToRoom(this);

		// fire
		new Dropper("unlitFire", gameScreen)
			.reactTo("lighterItem")
			.dropAt(325, 85, "litFire")
			.setImage("logs.png");

		new Dropper("litFire", gameScreen)
			.reactTo("boxItem")
			.dropAt(325, 85, "hammer")
			.setImage("fire.png");

	}
}
