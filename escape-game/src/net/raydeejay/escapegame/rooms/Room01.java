package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.Door;
import net.raydeejay.escapegame.Dropper;
import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Obtainable;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.screens.GameScreen;
import net.raydeejay.escapegame.screens.MainMenuScreen;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Room01 extends Room {

	public Room01(final GameScreen gameScreen) {
		super("room01", "room01.png", gameScreen);

		new Door("door01", gameScreen)
			.at(EscapeGame.WIDTH / 3, 82)
			.destination("room02")
			.imageForOpen("door2open.png")
			.imageForClosed("door2.png")
			.switchToState("closed")
			.addToRoom(this);

		// @formatter:off
//		new Obtainable("box", gameScreen);
//		new Obtainable("box", gameScreen)
//			.setImage("box.png")
//			.at(100, 20)
//			.addToRoom(this);

		new Reactor("paper", gameScreen)
			.setImage("paper.png")
			.addState(new State("state") {
				public void whenClicked() {
					EscapeGame game = gameScreen.getGame();
					game.setScreen(new MainMenuScreen(game));
					gameScreen.dispose();
				}
			})
			.switchToState("state");
		// @formatter:on

		// FIXME - note that it hardcodes final coordinates
		final Reactor vase = new Dropper("vase", gameScreen)
			.at(200, 30)
			.reactTo("hammerItem")
			.dropAt(600, 30, "paper")
			.setImage("vase.png");

		vase.addState(new State("1") {
			@Override
			public void whenClicked() {
				vase.switchToState("2");
			}
		}).addState(new State("2") {
			@Override
			public void onEnter() {
				vase.setX(400);
			}

			@Override
			public void whenClicked() {
				vase.switchToState("3");
			}
		}).addState(new State("3") {
			@Override
			public void onEnter() {
				vase.addAction(Actions.sequence(
						Actions.moveTo(600, vase.getY(), 2.0f),
						Actions.run(new Runnable() {
							public void run() {
								vase.switchToState("state");
							}
						})));
			}
		})
		.switchToState("1");

		this.addReactor(vase);

	}
}