package ru.sg_studio.escapegame.primitives;

import ru.sg_studio.RootObject;

public class GraphicalEntity extends RootObject {

	
	private boolean markedForEviction=false;
	
	public void remove(){
		markedForEviction=true;
	}
	
	private boolean visible;
	
	public boolean isVisible() {
		return this.visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	private String name;
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int x,y;
	private int width,height;
	
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return this.width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return this.height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
