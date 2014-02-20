package ru.sg_studio.escapegame.rendering;

public class Texture {

	/*
	 * Shitty hack to go with current libGDX implementation 
	 * If the project will not be abandoned I will rewrite this to support stichers.
	 */
	private boolean isLoadedBySide=false;
	
	public boolean isLoadedBySide() {
		return this.isLoadedBySide;
	}
	protected void onTextureLoadedBySide(){
		isLoadedBySide=true;
	}
	
	public Texture(String filename) {
		super();
		this.filename = filename;
	}
	
	/*
	 * Nice way to clone object textures meta
	 */
	protected Texture(Texture origin){
		filename=origin.filename;
		width=origin.width;
		height=origin.height;
	}
	
	private String filename;
	private int width,height;
	
	public String getFilename() {
		return this.filename;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public Texture cloneMeta() {
		Texture rslt = new Texture(this);
		return rslt;
	}
	
}
