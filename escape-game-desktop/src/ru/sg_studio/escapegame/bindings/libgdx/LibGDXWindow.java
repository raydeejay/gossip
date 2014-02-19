package ru.sg_studio.escapegame.bindings.libgdx;



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.sg_studio.escapegame.exceptions.RenderingWindowIsNotConfiguredException;
import ru.sg_studio.escapegame.rendering.IGameRenderingContext;
import ru.sg_studio.escapegame.rendering.RenderingWindow;
import ru.sg_studio.reports.Reporter;

public class LibGDXWindow extends RenderingWindow {

	public LibGDXWindow(int width, int height, String title) {
		super(width, height, title);
	}
	
	
	private boolean useGL20;
	private IGameRenderingContext context;
	
	public void configure(boolean useGL2, IGameRenderingContext context){
		this.useGL20=useGL2;
		this.context=context;
		super.setReady();
	}
	
	public void run(){
		try{
			check();
		}catch(RenderingWindowIsNotConfiguredException e){
			Reporter.out(e);return;//Unrecoverable
		}
		
		bind();
		
	}
	
	private LwjglApplicationConfiguration cfg;
	private LwjglApplication app;
	
	private void bind(){
		cfg= new LwjglApplicationConfiguration();
		cfg.width=width;
		cfg.height=height;
		cfg.title=title;
		
		cfg.useGL20=useGL20;
		
		
		app = new LwjglApplication((ApplicationListener) context, cfg);
	}

}
