package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room03 extends Room {

	public Room03(GameScreen gameScreen) {
		super("room03", "room03.png", gameScreen);
		this.setExitLeft("room02");

		final Reactor fireplace = new Reactor("fireplace", 200, 82, "fireplace.png");
		fireplace.addState("state", new State() {

			});
		fireplace.switchToState("state");
		this.addReactor(fireplace);

		final Reactor fire = new Reactor("fire", 325, 90, "fire.png");
		fire.addState("state", new State() {

			});
		fire.switchToState("state");
		this.addReactor(fire);

		final Reactor knife = new Reactor("knife", 530, 30, "knife.png");
		knife.addState("state", new State() {

			});
		knife.switchToState("state");
		this.addReactor(knife);

	}
	
}
