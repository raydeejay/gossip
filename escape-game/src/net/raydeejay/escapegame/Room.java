package net.raydeejay.escapegame;

import java.util.ArrayList;

import net.raydeejay.escapegame.reactors.Item;
import net.raydeejay.escapegame.screens.GameScreen;

public class Room {
	ArrayList<Reactor> reactors;
	private String backgroundFilename;
	private GameScreen screen;

	public ArrayList<Reactor> getReactors() {
		return this.reactors;
	}

	public Room(String aFilename, GameScreen gameScreen) {
		this.reactors = new ArrayList<Reactor>();
		this.setBackgroundFilename(aFilename);
		this.setScreen(gameScreen);
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

}
