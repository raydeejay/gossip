package ru.sg_studio.escapegame.bindings.libgdx;

import java.io.File;
import java.io.InputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import ru.sg_studio.escapegame.GameScreen;
import ru.sg_studio.escapegame.ReactorDepot;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXReactor;
import ru.sg_studio.escapegame.rendering.RenderingWindow;
import net.raydeejay.escapegame.Background;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;

public class LibGDXGameScreen extends GameScreen implements Screen {

	//final EscapeGame game;
	private Stage stage;
	//public Room currentRoom;


	
	public LibGDXGameScreen() {
		super();
		//this.game = gam;

		//GameRegistry.instance().setScreen(this);
		//GameRegistry.instance().setInventory(new Inventory("inventory.png"));
		stage = new Stage();

		
		postInit();
		
		//Removed gossip image loader	
		
		// and go
//        try {
//            System.out.println(engine.eval("GameRegistry instance switchToRoom: #room02"));
//        }
//        catch(ScriptException e) { System.err.println("ERROR :" + e); }
	}
	
	
	@Override
	public void render(float delta) {

		getHost().runQueue();
		
		Reactor zombie = checkForZombifiedReactors();
		HandleZombie(zombie);
		
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	
	
	private void HandleZombie(Reactor zombie) {
		if(zombie!=null){
			Actor actor = ((Actor)zombie.getCommonBindedProxy());
			actor.remove();
		}
	}


	//What the hell this does there? %_%
	@Override
	public void resize(int width, int height) {
		RenderingWindow window = LibGDXGameContext.getScreenMainContext().getWindow();
		Vector2 size = Scaling.fit.apply(window.getWidth(), window.getHeight(),
				width, height);
		int sizeX = (int) size.x;
		int sizeY = (int) size.y;

		int viewportX = (width - sizeX) / 2;
		int viewportY = (height - sizeY) / 2;
		int viewportWidth = sizeX;
		int viewportHeight = sizeY;

		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		stage.setViewport(window.getWidth(), window.getHeight(), true, viewportX,
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



	@Override
	public void pause() {	}

	@Override
	public void resume() {	}


	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void registerInPipeline() {
		LibGDXGameContext.registerScreenInMainContext(this);
		
	}


	@Override
	public void addReactor(Reactor aReactor) {
		super.addReactor(aReactor);
		System.out.println("Attempting to add libGDX actor: "+aReactor.getName());
		this.stage.addActor((LibGDXReactor)aReactor.getCommonBindedProxy());//EXPLICIT
	}


	@Override
	protected void pushDepot(ReactorDepot depot) {
		this.stage.clear();
		for(Reactor r : depot.getReactors()){
			this.stage.addActor((LibGDXReactor)r.getCommonBindedProxy());//EXPLICIT
		}
	}


	@Override
	public void addToInventory(Item anItem) {
		super.addToInventory(anItem);
		this.stage.addActor((Actor) anItem.getCommonBindedProxy());
	}







}
