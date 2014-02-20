package ru.sg_studio.escapegame.rendering;

import ru.sg_studio.escapegame.exceptions.RenderingWindowIsNotConfiguredException;

public abstract class RenderingWindow {

	private boolean isReady=false;
	
	public boolean isReady() {
		return this.isReady;
	}
	
	public RenderingWindow(int width, int height, String title) {
		super();
		this.width = width;
		this.height = height;
		this.title = title;
	}
	protected int width,height;
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	protected String title;

	protected final void setReady(){
		isReady=true;
	}
	protected final void setError(){
		//TODO: Error Object. exception token?
		isReady=false;
	}
	
	protected final void check() throws RenderingWindowIsNotConfiguredException{
		if(!isReady){
			throw new RenderingWindowIsNotConfiguredException();
		}
	}
	
	public abstract void run();
	
}
