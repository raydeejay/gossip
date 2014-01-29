package net.raydeejay.escapegame;

import java.util.ArrayList;

import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room {
	ArrayList<Reactor> reactors;
	private String backgroundFilename;
	private GameScreen screen;
	private String name;

	private String exitLeft;
	private String exitRight;
	private String exitTop;
	private String exitBottom;

	public ArrayList<Reactor> getReactors() {
		return this.reactors;
	}

	public Room(String name, String aFilename, GameScreen gameScreen) {
		this.reactors = new ArrayList<Reactor>();
		this.setBackgroundFilename(aFilename);
		this.setScreen(gameScreen);
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addReactor(Reactor aReactor) {
		aReactor.setRoom(this);
		this.reactors.add(aReactor);
		this.getScreen().addReactor(aReactor);
	}

	public void addToInventory(Item anItem) {
		this.getScreen().addToInventory(anItem);
	}

	public String getBackgroundFilename() {
		return backgroundFilename;
	}

	public void setBackgroundFilename(String backgroundFilename) {
		this.backgroundFilename = backgroundFilename;
	}

	public void removeReactor(Reactor aReactor) {
		aReactor.setRoom(null);
		this.reactors.remove(aReactor);
		aReactor.remove();
	}

	public GameScreen getScreen() {
		return screen;
	}

	public void setScreen(GameScreen screen) {
		this.screen = screen;
	}

	public String getExitLeft() {
		return this.exitLeft;
	}

	public void setExitLeft(String exitLeft) {
		this.exitLeft = exitLeft;
	}

	public String getExitRight() {
		return this.exitRight;
	}

	public void setExitRight(String exitRight) {
		this.exitRight = exitRight;
	}

	public String getExitTop() {
		return this.exitTop;
	}

	public void setExitTop(String exitTop) {
		this.exitTop = exitTop;
	}

	public String getExitBottom() {
		return this.exitBottom;
	}

	public void setExitBottom(String exitBottom) {
		this.exitBottom = exitBottom;
	}

}
