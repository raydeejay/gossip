package net.raydeejay.escapegame.screens;

import java.io.InputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import net.raydeejay.escapegame.Background;
import net.raydeejay.escapegame.EscapeGame;
import net.raydeejay.escapegame.GameRegistry;
import net.raydeejay.escapegame.Inventory;
import net.raydeejay.escapegame.Item;
import net.raydeejay.escapegame.Reactor;
import net.raydeejay.escapegame.Room;
import net.raydeejay.escapegame.State;
import net.raydeejay.gossip.engine.GossipScriptFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;

public class GameScreen implements Screen {

	final EscapeGame game;
	private Stage stage;
	private Room currentRoom;
	final Reactor arrowLeft = new Reactor("arrowLeft", this)
		.at(10, 240)
		.setImage("arrowLeft.png");
	final Reactor arrowRight = new Reactor("arrowRight", this)
		.at(620, 240)
		.setImage("arrowRight.png");

	public GameScreen(final EscapeGame gam) {
		this.game = gam;

		GameRegistry.instance().setScreen(this);
		GameRegistry.instance().setInventory(new Inventory("inventory.png", this));
		stage = new Stage();

		// set up the navigation buttons
		this.createNavigationButtons();

		// initialize Gossip
		FileHandle imageFile = Gdx.files.internal("gossip/Gossip.image");
		InputStream is = null;
		try {
			is = imageFile.read();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ScriptEngineFactory factory = new GossipScriptFactory();
        ScriptEngine engine = ((GossipScriptFactory) factory).getScriptEngineWithImage(is);
        
    	// monkeypatch Gdx file access into the image
        try {
			System.out.println(engine.eval("File class compileMethod: 'openRead: aName ^ <142 self (aName printString)>'"));
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
                
    	// load code not shipped in the image
        try {
            System.out.println(engine.eval("File fileIn: 'gossip/Test.st'"));
        }
        catch(ScriptException e) { System.err.println("ERROR :" + e); }
        
		// and go
		this.switchToRoom("room02");
	}

	private void addRoom(Room aRoom) {
		GameRegistry.instance().registerRoom(aRoom.getName(), aRoom);
	}

	private void createNavigationButtons() {
		arrowLeft.addState(new State("state") {
			@Override
			public void whenClicked() {
				switchToRoom(currentRoom.getExitLeft());
			}

		});
		arrowLeft.switchToState("state");
		arrowLeft.setVisible(false);

		arrowRight.addState(new State("state") {
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
//		this.currentRoom = this.rooms.get(destination);
		this.currentRoom = GameRegistry.instance().getRoom(destination);

		Background background = new Background(0, 0,
				this.currentRoom.getBackgroundFilename());
		this.stage.addActor(background);

		Background invBackground = new Background(700, 0, GameRegistry.instance().getInventory()
				.getBackgroundFilename());
		this.stage.addActor(invBackground);

		// room
		for (Reactor r : this.currentRoom.getReactors()) {
			this.stage.addActor(r);
		}

		// inventory
		for (Item i : GameRegistry.instance().getInventory().getItems()) {
			this.stage.addActor(i);
		}

		// navigation
		this.currentRoom.addReactor(arrowLeft);
		this.currentRoom.addReactor(arrowRight);
		this.updateNavigation();

	}

	private void updateNavigation() {
		this.arrowLeft.setVisible(this.currentRoom.getExitLeft() != null);
		this.arrowRight.setVisible(this.currentRoom.getExitRight() != null);
	}

	public void addReactor(Reactor aReactor) {
		this.stage.addActor(aReactor);
	}

	public void addToInventory(Item anItem) {
		GameRegistry.instance().getInventory().addItem(anItem);
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
		int sizeX = (int) size.x;
		int sizeY = (int) size.y;

		int viewportX = (width - sizeX) / 2;
		int viewportY = (height - sizeY) / 2;
		int viewportWidth = sizeX;
		int viewportHeight = sizeY;

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
	public void dispose() {
		stage.dispose();
	}

/*	public static Inventory getInventory() {
		return inventory;
	}

	public static void setInventory(Inventory anInventory) {
		inventory = anInventory;
	}
*/
	public EscapeGame getGame() {
		return this.game;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
