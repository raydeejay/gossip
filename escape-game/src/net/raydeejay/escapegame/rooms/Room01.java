package net.raydeejay.escapegame.rooms;

import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;
import net.raydeejay.escapegame.screens.MainMenuScreen;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Room01 extends Room {

	public Room01(GameScreen gameScreen) {
		super("room01", "room01.png", gameScreen);

		final Reactor door01 = new Reactor("door01", EscapeGame.WIDTH / 3, 82,
				"door2.png");

		door01.addState(new State("closed") {
			@Override
			public void whenClicked() {
				door01.switchToState("open");
			}
		});

		door01.addState(new State("open") {
			@Override
			public void onEnter() {
				door01.setImage("door2open.png");
			}

			@Override
			public void whenClicked() {
				getScreen().switchToRoom("room02");
			}
		});

		door01.switchToState("closed");
		this.addReactor(door01);

		final Reactor vase = new Reactor("vase", 200, 200, "vase.png");
		vase.addState(new State("1") {
			@Override
			public void onEnter() {
				vase.setX(200);
			}

			@Override
			public void whenClicked() {
				vase.switchToState("2");
			}
		});

		vase.addState(new State("2") {
			@Override
			public void onEnter() {
				vase.setX(400);
			}

			@Override
			public void whenClicked() {
				vase.switchToState("3");
			}
		});

		vase.addState(new State("3") {
			@Override
			public void onEnter() {
				vase.addAction(Actions.sequence(
						Actions.moveTo(600, vase.getY(), 2.0f),
						Actions.run(new Runnable() {
							public void run() {
								vase.switchToState("4");
							}
						})));
			}
		});

		vase.addState(new State("4") {
			@Override
			public void whenClickedWith(Item anItem) {
				if (anItem.getName().equals("hammerItem")) {
					anItem.getInventory().removeItem(anItem);
					removeReactor(vase);

					final Reactor paper = new Reactor("paper", vase.getX(),
							vase.getY(), "paper.png");
					paper.addState(new State("state") {
						public void whenClicked() {
							// show a message ?
							GameScreen screen = getScreen();
							EscapeGame game = screen.getGame();
							game.setScreen(new MainMenuScreen(game));
							screen.dispose();
						}
					});

					paper.switchToState("state");
					addReactor(paper);
				}
			}
		});

		vase.switchToState("1");
		this.addReactor(vase);

		final Reactor box = new Reactor("box", 100, 20, "box.png");
		box.addState(new State("state") {
			@Override
			public void whenClicked() {
				final Item boxItem = new Item(box);
				addToInventory(boxItem);
				removeReactor(box);
			}

		});
		box.switchToState("state");
		this.addReactor(box);

	}
}