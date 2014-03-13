package ru.sg_studio.escapegame.bindings.libgdx;

import ru.sg_studio.escapegame.GameScreen;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.RenderableDepot;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXProto;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXReactor;
import ru.sg_studio.escapegame.primitives.GraphicalEntity;
import ru.sg_studio.escapegame.rendering.RenderingWindow;
import net.raydeejay.escapegame.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

		stage = new Stage();

		
		postInit();
		

	}
	
	
	@Override
	public void render(float delta) {

		getHost().runQueue();
		
		GraphicalEntity zombie = checkForZombifiedReactors();
		HandleZombie(zombie);
		
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	
	
	private void HandleZombie(GraphicalEntity zombie) {
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
	public void addGraphical(GraphicalEntity aGraphical) {
		super.addGraphical(aGraphical);
		System.out.println("Attempting to add libGDX actor: "+aGraphical.getName());
		this.stage.addActor((LibGDXReactor)aGraphical.getCommonBindedProxy());//EXPLICIT
	}


	@Override
	protected void pushDepot(RenderableDepot depot) {
		this.stage.clear();
		for(GraphicalEntity r : depot.getGraphical()){
			this.stage.addActor((LibGDXProto)r.getCommonBindedProxy());//EXPLICIT
		}
	}


	@Override
	public void addToInventory(Item anItem) {
		super.addToInventory(anItem);
		this.stage.addActor((Actor) anItem.getCommonBindedProxy());
	}


	@Override
	protected void uploadProxyIncremental(GraphicalEntity ge) {
		IProxiedObject ipo = ge.getCommonBindedProxy();
		this.stage.addActor((Actor)ipo);
	}







}
