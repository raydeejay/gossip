package net.raydeejay.escapegame.screens;

import java.util.Hashtable;

import net.raydeejay.escapegame.Background;
import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.reactors.Door;
import net.raydeejay.escapegame.reactors.Movable;

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
	private Hashtable<String, Room> rooms;

	public GameScreen() {
		this.game = (EscapeGame) Gdx.app.getApplicationListener();
		this.rooms = new Hashtable<String, Room>();
		stage = new Stage();

		// set up the rooms
		this.createRooms();

		// and go
		this.switchToRoom("room01");
	}

	private void createRooms() {
		// room 01
		Room room01 = new Room("room01.png");

		Door door01 = new Door(EscapeGame.WIDTH / 3, 100, "door2.png", "room02");
		room01.addReactor(door01);

		Movable vase = new Movable(200, 200, "vase.png");
		vase.addCoords(400, 200);
		vase.addCoords(600, 200);
		room01.addReactor(vase);

		this.rooms.put("room01", room01);

		// room 02
		Room room02 = new Room("room02.png");

		Door door02 = new Door(EscapeGame.WIDTH / 2, 80, "door2.png", "room01");
		room02.addReactor(door02);

		this.rooms.put("room02", room02);

		// room 03
		Room room03 = new Room("room02.png");
		this.rooms.put("room03", room03);

	}

	public void switchToRoom(String destination) {
		this.stage.clear();
		this.currentRoom = this.rooms.get(destination);

		Background background = new Background(0, 0,
				this.currentRoom.getBackgroundFilename());
		this.stage.addActor(background);

		for (Reactor r : this.currentRoom.getReactors()) {
			this.stage.addActor(r);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
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

}
