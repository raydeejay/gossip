package net.raydeejay.escapegame.screens;

import java.io.File;
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
	public Room currentRoom;

	public GameScreen(final EscapeGame gam) {
		this.game = gam;

		GameRegistry.instance().setScreen(this);
		GameRegistry.instance().setInventory(new Inventory("inventory.png"));
		stage = new Stage();

		// if there is an image in the local directory, load from there
		// otherwise load from internal
		
		boolean fromDisk = new File("gossip/Gossip.image").exists();
		FileHandle imageFile;
		
		if(fromDisk) {
			imageFile = Gdx.files.local("gossip/Gossip.image");
			
		} else {
			imageFile = Gdx.files.internal("gossip/Gossip.image");
		}		

		// initialize Gossip
//		FileHandle imageFile = Gdx.files.internal("gossip/Gossip.image");
		InputStream is = null;
		try {
			is = imageFile.read();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ScriptEngineFactory factory = new GossipScriptFactory();
        ScriptEngine engine = ((GossipScriptFactory) factory).getScriptEngineWithImage(is);
        
    	// monkeypatch Gdx file access into the image
		if(! fromDisk) {
	        try {
				System.out.println(engine.eval("File class compileMethod: 'openRead: aName ^ <142 self (aName printString)>'"));
			} catch (ScriptException e1) {
				e1.printStackTrace();
			}
		}
		
		// load code not shipped in the image
        try {
            System.out.println(engine.eval("File fileIn: 'gossip/Test.st'"));
        }
        catch(ScriptException e) { System.err.println("ERROR :" + e); }
        
		// and go
        try {
            System.out.println(engine.eval("GameRegistry instance switchToRoom: #room02"));
        }
        catch(ScriptException e) { System.err.println("ERROR :" + e); }
	}

	public void switchToRoom(String destination) {
		this.stage.clear();
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
		this.stage.addActor(GameRegistry.instance().getReactor("arrowLeft"));
		this.stage.addActor(GameRegistry.instance().getReactor("arrowRight"));

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

	public EscapeGame getGame() {
		return this.game;
	}

	@Override
	public void pause() {	}

	@Override
	public void resume() {	}

}
