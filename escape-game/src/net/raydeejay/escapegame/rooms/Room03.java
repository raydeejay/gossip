package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room03 extends Room {

	public Room03(GameScreen gameScreen) {
		super("room03", "room02partial.png", gameScreen);
		this.setExitLeft("room02");

		final Reactor fireplace = new Reactor("fireplace", 200, 82, "fireplace.png");
		fireplace.addState("state", new State() {

			});
		fireplace.switchToState("state");
		this.addReactor(fireplace);

	}
	
}
