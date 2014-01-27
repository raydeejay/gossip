package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Room02 extends Room {

	public Room02() {
		super("room02.png");
		
		final Reactor door02 = new Reactor(EscapeGame.WIDTH / 2, 100, "door2.png");
		door02.addState("closed", new State() {
			@Override
			public void whenClicked() {
				door02.switchToState("open");
			}
		});
		
		door02.addState("open", new State() {
			@Override
			public void onEnter() {
				door02.setImage("door2open.png");
			}
			
			@Override
			public void whenClicked() {
				GameScreen screen = (GameScreen) ((Game) Gdx.app
						.getApplicationListener()).getScreen();
				screen.switchToRoom("room01");
			}
		});

		door02.switchToState("closed");
		this.addReactor(door02);
	}

}
