package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Door;
import net.raydeejay.escapegame.reactors.Dropper;
import net.raydeejay.escapegame.reactors.Obtainable;
import net.raydeejay.escapegame.screens.GameScreen;
import net.raydeejay.escapegame.screens.MainMenuScreen;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Room01 extends Room {

	public Room01(final GameScreen gameScreen) {
		super("room01", "room01.png", gameScreen);

	}
}