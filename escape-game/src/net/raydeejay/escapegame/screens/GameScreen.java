package net.raydeejay.escapegame.screens;

import java.util.Hashtable;

import net.raydeejay.escapegame.Background;
import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Inventory;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.rooms.Room01;
import net.raydeejay.escapegame.rooms.Room02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;

public class GameScreen implements Screen {

	final EscapeGame game;
	private Stage stage;
	private Room currentRoom;
	private Inventory inventory;
	private Hashtable<String, Room> rooms;
	final Reactor arrowLeft = new Reactor("arrowLeft", 10, 240, "arrowLeft.png");
	final Reactor arrowRight = new Reactor("arrowRight", 620, 240, "arrowRight.png");

	public GameScreen(final EscapeGame gam) {
		this.game = gam;
		this.rooms = new Hashtable<String, Room>();
		this.setInventory(new Inventory("inventory.png", this));
		stage = new Stage();

		// set up the rooms and stuff
		this.createRooms();
		this.createNavigationButtons();

		// and go
		this.switchToRoom("room01");
	}

	private void createRooms() {
		this.rooms.put("room01", new Room01(this));
		this.rooms.put("room02", new Room02(this));
	}

	private void createNavigationButtons() {
		arrowLeft.addState("state", new State() {
			@Override
			public void whenClicked() {
				switchToRoom(currentRoom.getExitLeft());
			}

		});
		arrowLeft.switchToState("state");
		arrowLeft.setVisible(false);

		arrowRight.addState("state", new State() {
			@Override
			public void whenClicked() {
				switchToRoom(currentRoom.getExitRight());
			}

		});
		arrowRight.switchToState("state");
		arrowRight.setVisible(false);
	}

	public void switchToRoom(String destination) {
		this.stage.clear();
		this.currentRoom = this.rooms.get(destination);

		Background background = new Background(0, 0,
				this.currentRoom.getBackgroundFilename());
		this.stage.addActor(background);

		Background invBackground = new Background(700, 0, this.getInventory()
				.getBackgroundFilename());
		this.stage.addActor(invBackground);

		// room
		for (Reactor r : this.currentRoom.getReactors()) {
			this.stage.addActor(r);
		}

		// inventory
		for (Item i : this.getInventory().getItems()) {
			this.stage.addActor(i);
		}

		// navigation
		this.currentRoom.addReactor(arrowLeft);
		this.currentRoom.addReactor(arrowRight);
		this.updateNavigation();

	}

	private void updateNavigation() {
		if (currentRoom.getExitLeft() == null) {
			this.arrowLeft.setVisible(false);
		} else {
			this.arrowLeft.setVisible(true);
		}

		if (currentRoom.getExitRight() == null) {
			this.arrowRight.setVisible(false);
		} else {
			this.arrowRight.setVisible(true);
		}
	}

	public void addReactor(Reactor aReactor) {
		this.stage.addActor(aReactor);
	}

	public void addToInventory(Item anItem) {
		this.getInventory().addItem(anItem);
		this.stage.addActor(anItem);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		Vector2 size = Scaling.fit.apply(EscapeGame.WIDTH, EscapeGame.HEIGHT,
				width, height);
		int viewportX = (int) (width - size.x) / 2;
		int viewportY = (int) (height - size.y) / 2;
		int viewportWidth = (int) size.x;
		int viewportHeight = (int) size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		stage.setViewport(EscapeGame.WIDTH, EscapeGame.HEIGHT, true, viewportX,
				viewportY, viewportWidth, viewportHeight);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.stage);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		// Raindrop.dropImage.dispose();
		stage.dispose();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public EscapeGame getGame() {
		return this.game;
	}

}
