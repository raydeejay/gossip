package ru.sg_studio.escapegame.rendering;

public class Texture {

	
	
	public Texture(String filename) {
		super();
		this.filename = filename;
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
	
}
