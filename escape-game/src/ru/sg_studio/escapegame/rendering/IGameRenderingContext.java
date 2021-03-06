package ru.sg_studio.escapegame.rendering;

public interface IGameRenderingContext {


	
	//Resource Management
	public void fillInitialContext();
	public void loadPersistentModules();
	
	
	public RenderingWindow getWindow();
	
	//LibGDX-alike
	public void create();
	public void dispose();
	public void render();
	
	
}
