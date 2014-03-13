package ru.sg_studio.escapegame.bindings.libgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import ru.sg_studio.escapegame.CommonResourceProvider;
import ru.sg_studio.escapegame.GameScreen;
import ru.sg_studio.escapegame.rendering.IGameRenderingContext;
import ru.sg_studio.escapegame.rendering.RenderingWindow;



public final class LibGDXGameContext extends Game implements IGameRenderingContext{



	public static void registerScreenInMainContext(GameScreen screen){
		mainContext.setScreen((Screen) screen);
	}
	public static LibGDXGameContext getScreenMainContext(){
		return mainContext;
	}	
	public static LibGDXGameContext mainContext;

	private RenderingWindow window;
	
	public LibGDXGameContext(LibGDXWindow window){
		this.window = window;
		mainContext = this;
		
		crp = new CommonResourceProvider();
	}
	
	
	@Override
	public void fillInitialContext() {
		// we want to load non-power-of-two textures easily
		Texture.setEnforcePotImages(false); 

	}

	@Override
	public void loadPersistentModules() {
		//Screend
		LibGDXGameScreen screen = new LibGDXGameScreen();
		crp.load(screen);	
	}


	CommonResourceProvider crp;
	
	
	@Override
	public void create() {
		System.out.println(this.getClass().getName()+" created by libGDX!");
		fillInitialContext();
		loadPersistentModules();
	}	
	
	//LibGDX


	@Override
	public void dispose() {
		super.dispose();
		crp.dispose();
	}

	@Override
	public void render() {
		super.render();
	}
	@Override
	public RenderingWindow getWindow() {
		return window;
	}	
	
	
	
}






